package com.swarmmanager.docker.cli.impl;

import com.swarmmanager.docker.api.common.json.*;
import com.swarmmanager.docker.api.common.json.inner.*;
import com.swarmmanager.docker.api.common.util.DockerDateFormatter;
import com.swarmmanager.docker.api.nodes.NodesApi;
import com.swarmmanager.docker.api.services.ServicesApi;
import com.swarmmanager.docker.api.services.parameters.*;
import com.swarmmanager.docker.api.tasks.TasksApi;
import com.swarmmanager.docker.api.tasks.parameters.TasksFilters;
import com.swarmmanager.docker.api.tasks.parameters.TasksListParameters;
import com.swarmmanager.docker.cli.ServiceCli;
import com.swarmmanager.docker.cli.impl.helper.ServiceSpecJsonHelper;
import com.swarmmanager.docker.cli.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.ZonedDateTime;
import java.util.*;

import static com.swarmmanager.docker.api.common.util.DockerDateFormatter.fromDateStringToDuration;

@Component
public class ServiceCliImpl implements ServiceCli {

    private final Logger LOGGER = LoggerFactory.getLogger(ServiceCliImpl.class.getName());

    private static final String NODE_DETAIL = "com.docker.swarm.node.id";

    private static final String TASK_DETAIL = "com.docker.swarm.task.id";

    private static final int MAX_LOGS = 10000;

    @Autowired
    private ServicesApi servicesApi;

    @Autowired
    private TasksApi tasksApi;

    @Autowired
    private NodesApi nodesApi;


    @Override
    public Service inspectService(String serviceId) {
        ServiceJson serviceJson = this.servicesApi.inspectService(serviceId);
        Service service = new Service();
        service.setId(serviceJson.getId());
        ZonedDateTime createdAt = DockerDateFormatter.fromDateStringToZonedDateTime(serviceJson.getCreatedAt());
        ZonedDateTime updatedAt = DockerDateFormatter.fromDateStringToZonedDateTime(serviceJson.getUpdatedAt());
        service.setCreatedAt(createdAt.toInstant().toEpochMilli());
        service.setUpdatedAt(updatedAt.toInstant().toEpochMilli());
        service.setName(serviceJson.getSpec().getName());
        service.setGlobal(serviceJson.getSpec().getMode().getGlobal() != null);
        if (!service.isGlobal()) {
            service.setReplicas(serviceJson.getSpec().getMode().getReplicated().getReplicas());
        }
        EndpointSpecJson endpointSpecJson = serviceJson.getSpec().getEndpointSpec();
        List<Port> ports = getPorts(endpointSpecJson);
        if (!ports.isEmpty()) {
            service.setPorts(ports);
        }
        service.setImage(serviceJson.getSpec().getTaskTemplate().getContainerSpec().getImage());
        return service;
    }

    @Override
    public List<ServiceSummary> serviceLs() {
        List<ServiceJson> services = servicesApi.listServices(new ServicesListParameters());
        List<ServiceSummary> servicesSummary = new ArrayList<>();
        for (ServiceJson service : services) {
            ServiceSummary serviceSummary = new ServiceSummary();
            serviceSummary.setId(service.getId());
            serviceSummary.setName(service.getSpec().getName());
            serviceSummary.setImage(service.getSpec().getTaskTemplate().getContainerSpec().getImage());
            ReplicatedServiceJson replicated = service.getSpec().getMode().getReplicated();
            TasksListParameters tasksListParameters = new TasksListParameters();
            TasksFilters filters = new TasksFilters();
            filters.setService(serviceSummary.getId());
            filters.setDesiredState(TasksFilters.RUNNING_STATE);
            tasksListParameters.setFilters(filters);
            List<TaskJson> tasks = tasksApi.listTasks(tasksListParameters);
            if (replicated != null) {
                serviceSummary.setReplicas(replicated.getReplicas());
            } else {
                serviceSummary.setGlobal(true);
                serviceSummary.setReplicas(tasks.stream().filter(task -> !TasksFilters.RUNNING_STATE.equals(task.getStatus().getTaskState())).count());
            }
            tasks.removeIf(task -> !TasksFilters.RUNNING_STATE.equals(task.getStatus().getTaskState()));
            serviceSummary.setRunningReplicas(tasks.size());
            EndpointSpecJson endpointSpecJson = service.getSpec().getEndpointSpec();
            List<Port> ports = getPorts(endpointSpecJson);
            if (!ports.isEmpty()) {
                serviceSummary.setPorts(ports);
            }
            servicesSummary.add(serviceSummary);
        }
        return servicesSummary;
    }

    @Override
    public ServiceState servicePs(String serviceId) {
        ServiceJson service = servicesApi.inspectService(serviceId);
        ServiceState serviceState =  new ServiceState();
        if (service != null) {
            serviceState.setId(serviceId);
            serviceState.setName(service.getSpec().getName());

            TasksListParameters tasksListParameters = new TasksListParameters();
            TasksFilters filters = new TasksFilters();
            filters.setService(serviceId);
            tasksListParameters.setFilters(filters);
            Map<Integer, Set<Task>> taskByReplica = new HashMap<>();

            tasksApi.listTasks(tasksListParameters).forEach( taskJson -> {
                int replicaNumber = taskJson.getSlot();
                Set<Task> tasks = taskByReplica.get(replicaNumber);
                if (tasks == null) {
                    tasks = new TreeSet<>(Comparator.comparing(Task::getLastStateChange));
                    taskByReplica.put(replicaNumber, tasks);
                }
                Task task = new Task();
                task.setId(taskJson.getId());
                task.setDesiredState(taskJson.getDesiredState());

                TaskStatusJson status = taskJson.getStatus();
                if (status != null) {
                    String err = status.getErr();
                    if (err != null) {
                        task.setErrorMessage(status.getErr());
                    } else {
                        task.setErrorMessage("");
                    }
                    task.setState(status.getTaskState());

                    String timestamp = status.getTimestamp();
                    task.setLastStateChange(fromDateStringToDuration(timestamp));

                    PortStatusJson portStatus = status.getPortStatus();
                    if (portStatus != null) {
                        PortConfigJson[] portConfigs = portStatus.getPorts();
                        if (portConfigs != null) {
                            List<Port> ports = new ArrayList<>();
                            for (PortConfigJson portConfig : portConfigs) {
                                Port port = new Port();
                                port.setPublished(portConfig.getPublishedPort());
                                port.setProtocol(Port.Protocol.getProtocol(portConfig.getProtocol()));
                                port.setTarget(portConfig.getTargetPort());
                                ports.add(port);
                            }
                            task.setPorts(ports);
                        } else {
                            task.setPorts(new ArrayList<>());
                        }
                    } else {
                        task.setPorts(new ArrayList<>());
                    }
                }

                String nodeId = taskJson.getNodeId();
                task.setNodeId(nodeId);

                NodeJson node = nodesApi.inspectNode(nodeId);
                if (node != null) {
                    NodeDescriptionJson description = node.getDescription();
                    if (description != null) {
                        task.setNodeHostname(description.getHostname());
                    }
                }

                tasks.add(task);
            });

            List<Replica> replicas = new ArrayList<>();
            taskByReplica.forEach( (k, v) -> {
                Replica replica = new Replica();
                replica.setNumber(k);
                replica.setTasks(v);
                replicas.add(replica);
            });

            serviceState.setReplicas(replicas);
        }
        return serviceState;
    }

    @Override
    public Service serviceCreate(Service service) {
        ServiceCreateParameters createParameters = new ServiceCreateParameters();
        ServiceSpecJson serviceSpecJson = ServiceSpecJsonHelper.createNewHelper()
                .setName(service.getName())
                .setPorts(service.getPorts())
                .setImage(service.getImage())
                .setMode(service.isGlobal(), service.getReplicas())
                .getServiceSpecJson();
        createParameters.setService(serviceSpecJson);
        ServiceGeneralResponseJson responseJson = servicesApi.createService(createParameters);
        service.setId(responseJson.getId());
        return service;
    }

    @Override
    public void serviceUpdate(String serviceId, Service service) {
        ServiceUpdateParameters updateParameters = new ServiceUpdateParameters();
        ServiceJson serviceJson = servicesApi.inspectService(serviceId);
        VersionJson versionJson = serviceJson.getVersion();
        updateParameters.setVersionQueryParam(versionJson.getIndex());
        ServiceSpecJson serviceSpecJson = ServiceSpecJsonHelper.createNewHelper(serviceJson.getSpec())
                .setImage(service.getImage())
                .setPorts(service.getPorts())
                .setReplicas(service.getReplicas())
                .getServiceSpecJson();
        updateParameters.setService(serviceSpecJson);
        servicesApi.updateService(serviceId, updateParameters);
    }

    @Override
    public void serviceRm(String serviceId) {
        servicesApi.deleteService(serviceId);
    }

    @Override
    public Logs serviceLogs(String serviceId) {
        ServiceLogsParameters parameters = new ServiceLogsParameters();
        parameters.setStderrQueryParam(true);
        parameters.setStdoutQueryParam(true);
        parameters.setTimestampsQueryParam(true);
        parameters.setDetailsQueryParam(true);
        parameters.setTailQueryParam(MAX_LOGS);
        List<LogLine> logLines = new ArrayList<>();
        Set<LogFilter> logFilters = new TreeSet<>((filter1, filter2) -> {
            if (filter1.getReplica() != filter2.getReplica()) {
                return filter1.getReplica() - filter2.getReplica();
            }
            return filter1.getTaskId().compareTo(filter2.getTaskId());
        });
        Logs logs = new Logs();
        Map<String, TaskJson> tasksById = new HashMap<>();
        List<TaskJson> tasks = tasksApi.listTasks(new TasksListParameters());
        tasks.forEach(task -> tasksById.put(task.getId(), task));

        byte[] logBuf = servicesApi.getServiceLogs(serviceId, parameters);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(logBuf);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF8"));
            String entry;
            while ((entry = reader.readLine()) != null) {
                LogLine logLine = convertLogString(serviceId, tasksById, entry);
                logLines.add(logLine);
                logFilters.add(new LogFilter(logLine.getTaskId(), logLine.getReplica(), logLine.getServiceId(), logLine.getNodeId()));
            }
        } catch (IOException e) {
            LOGGER.error("Error parsing logs", e);
        }
        logLines.sort(Comparator.comparingLong(LogLine::getTimestamp));
        logs.setLogLines(logLines);
        logs.setLogFilters(logFilters);
        return logs;
    }

    private LogLine convertLogString(String serviceId, Map<String, TaskJson> tasksById, String logString) {
        //remove 8 bytes padding padding
        String log = logString.substring(8);
        String[] parts = log.split(" ");
        if (parts.length > 2) {
            String timestampStr = parts[0];
            ZonedDateTime timestamp = DockerDateFormatter.fromDateStringToZonedDateTime(timestampStr);
            String detailsStr = parts[1];
            String nodeId = "";
            String tasKId = "";
            int replica = 0;
            String[] details = detailsStr.split(",");
            for (String detail : details) {
                if (detail.startsWith(NODE_DETAIL)) {
                    nodeId = detail.substring(NODE_DETAIL.length() + 1);
                }
                if (detail.startsWith(TASK_DETAIL)) {
                    tasKId = detail.substring(TASK_DETAIL.length() + 1);
                    replica = tasksById.get(tasKId).getSlot();
                }
            }
            StringBuilder messageBuilder = new StringBuilder(parts[2]);
            for (int i = 3; i < parts.length; i++) {
                messageBuilder.append(" " + parts[i]);
            }
            String message = messageBuilder.toString();
            return new LogLine(serviceId, nodeId, tasKId, replica, message, timestamp.toInstant().toEpochMilli());
        }
        return null;
    }


    private List<Port> getPorts(EndpointSpecJson endpointSpecJson) {
        List<Port> ports = new ArrayList<>();
        if (endpointSpecJson != null) {
            PortConfigJson[] portConfigs = endpointSpecJson.getPorts();
            if (portConfigs != null) {
                for (PortConfigJson portConfig : portConfigs) {
                    Port port = new Port();
                    port.setProtocol(Port.Protocol.getProtocol(portConfig.getProtocol()));
                    port.setPublished(portConfig.getPublishedPort());
                    port.setTarget(portConfig.getTargetPort());
                    ports.add(port);
                }
            }
        }
        return ports;
    }

}


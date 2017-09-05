package com.swarmmanager.docker.cli;

import com.swarmmanager.docker.api.common.json.*;
import com.swarmmanager.docker.api.common.json.inner.*;
import com.swarmmanager.docker.api.nodes.NodesApi;
import com.swarmmanager.docker.api.services.ServicesApi;
import com.swarmmanager.docker.api.services.parameters.*;
import com.swarmmanager.docker.api.tasks.TasksApi;
import com.swarmmanager.docker.api.tasks.parameters.TasksFilters;
import com.swarmmanager.docker.api.tasks.parameters.TasksListParameters;
import com.swarmmanager.docker.cli.helper.ServiceSpecJsonHelper;
import com.swarmmanager.docker.cli.model.LogLine;
import com.swarmmanager.docker.cli.model.Port;
import com.swarmmanager.docker.cli.model.Replica;
import com.swarmmanager.docker.cli.model.Service;
import com.swarmmanager.docker.cli.model.ServiceState;
import com.swarmmanager.docker.cli.model.ServiceSummary;
import com.swarmmanager.docker.cli.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

import static com.swarmmanager.docker.api.common.util.DockerDateFormatter.fromDateStringToDuration;

@Component
public class ServiceCliImpl implements ServiceCli {
    private final Logger LOGGER = LoggerFactory.getLogger(ServiceCliImpl.class.getName());

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
        service.setName(serviceJson.getSpec().getName());
        service.setGlobal(serviceJson.getSpec().getMode().getGlobal() != null);
        if (!service.isGlobal()) {
            service.setReplicas(serviceJson.getSpec().getMode().getReplicated().getReplicas());
        }
        EndpointSpecJson endpointSpecJson = serviceJson.getSpec().getEndpointSpec();
        if (endpointSpecJson != null) {
            PortConfigJson[] portConfigs = endpointSpecJson.getPorts();
            List<Port> ports = new ArrayList<>();
            if (portConfigs != null) {
                for (PortConfigJson portConfig : portConfigs) {
                    Port port = new Port();
                    port.setProtocol(Port.Protocol.getProtocol(portConfig.getProtocol()));
                    port.setPublished(portConfig.getPublishedPort());
                    port.setTarget(portConfig.getTargetPort());
                    ports.add(port);
                }
            }
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
            if (replicated != null) {
                serviceSummary.setReplicas(replicated.getReplicas());

                TasksListParameters tasksListParameters = new TasksListParameters();
                TasksFilters filters = new TasksFilters();
                filters.setService(serviceSummary.getId());
                filters.setDesiredState(TasksFilters.RUNNING_STATE);
                tasksListParameters.setFilters(filters);
                List<TaskJson> tasks = tasksApi.listTasks(tasksListParameters);
                tasks.removeIf(task -> !TasksFilters.RUNNING_STATE.equals(task.getStatus().getTaskState()));
                serviceSummary.setRunningReplicas(tasks.size());
            } else {
                GlobalServiceJson global = service.getSpec().getMode().getGlobal();
                if (global != null) {
                    serviceSummary.setGlobal(true);
                }
            }

            EndpointSpecJson endpointSpecJson = service.getSpec().getEndpointSpec();
            if (endpointSpecJson != null) {
                PortConfigJson[] portConfigs = endpointSpecJson.getPorts();
                List<Port> ports = new ArrayList<>();
                if (portConfigs != null) {
                    for (PortConfigJson portConfig : portConfigs) {
                        Port port = new Port();
                        port.setProtocol(Port.Protocol.getProtocol(portConfig.getProtocol()));
                        port.setPublished(portConfig.getPublishedPort());
                        port.setTarget(portConfig.getTargetPort());
                        ports.add(port);
                    }
                }
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
        System.out.println(serviceSpecJson);
        servicesApi.updateService(serviceId, updateParameters);
    }

    @Override
    public void serviceRm(String serviceId) {
        servicesApi.deleteService(serviceId);
    }

    @Override
    public List<LogLine> serviceLogs(String serviceId) {
        ServiceLogsParameters parameters = new ServiceLogsParameters();
        parameters.setStderrQueryParam(true);
        parameters.setStdoutQueryParam(true);
        parameters.setTimestampQueryParam(true);
        //TODO parse task id and replica number when docker service logs fixed
        byte[] logBuf = servicesApi.getServiceLogs(serviceId, parameters);
        List<LogLine> logLines = new ArrayList<>();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(logBuf);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF8"));
            String entry;
            while ((entry = reader.readLine()) != null) {
                logLines.add(new LogLine(-1, "", entry));
            }
        } catch (IOException e) {
            LOGGER.error("Error parsing logs", e);
        }
        return logLines;
    }

}

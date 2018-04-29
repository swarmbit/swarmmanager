package co.uk.swarmbit.docker.cli.impl;

import co.uk.swarmbit.docker.api.common.json.*;
import co.uk.swarmbit.docker.api.common.json.inner.EndpointSpecJson;
import co.uk.swarmbit.docker.api.common.json.inner.ReplicatedServiceJson;
import co.uk.swarmbit.docker.api.common.json.inner.VersionJson;
import co.uk.swarmbit.docker.api.common.util.DockerDateFormatter;
import co.uk.swarmbit.docker.api.nodes.NodesApi;
import co.uk.swarmbit.docker.api.services.ServicesApi;
import co.uk.swarmbit.docker.api.services.parameters.*;
import co.uk.swarmbit.docker.api.tasks.TasksApi;
import co.uk.swarmbit.docker.api.tasks.parameters.TasksFilters;
import co.uk.swarmbit.docker.api.tasks.parameters.TasksListParameters;
import co.uk.swarmbit.docker.cli.ServiceCli;
import co.uk.swarmbit.docker.cli.impl.helper.ServiceConverter;
import co.uk.swarmbit.docker.cli.impl.helper.ServiceSpecJsonConverter;
import co.uk.swarmbit.docker.cli.impl.helper.TaskJsonHelper;
import co.uk.swarmbit.docker.cli.model.*;
import co.uk.swarmbit.exception.RegistryUserNotFound;
import co.uk.swarmbit.repository.RegistryUserRepository;
import co.uk.swarmbit.repository.model.RegistryUser;
import co.uk.swarmbit.util.EncoderDecoder;
import co.uk.swarmbit.util.UserUtil;
import org.apache.commons.lang3.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ServiceCliImpl implements ServiceCli {

    private final Logger LOGGER = LoggerFactory.getLogger(ServiceCliImpl.class.getName());

    private static final String NODE_DETAIL = "com.docker.swarm.node.id";

    private static final String TASK_DETAIL = "com.docker.swarm.task.id";

    private static final int MAX_LOGS = 10000;

    private final ServicesApi servicesApi;

    private final TasksApi tasksApi;

    private final NodesApi nodesApi;

    private final RegistryUserRepository registryUserRepository;

    @Autowired
    public ServiceCliImpl(ServicesApi servicesApi, TasksApi tasksApi, NodesApi nodesApi, RegistryUserRepository registryUserRepository) {
        this.servicesApi = servicesApi;
        this.tasksApi = tasksApi;
        this.nodesApi = nodesApi;
        this.registryUserRepository = registryUserRepository;
    }

    @Override
    public Service inspectService(String swarmId, String serviceId) {
        ServiceJson serviceJson = servicesApi.inspectService(swarmId, serviceId,
                new ServiceInspectParameters().setInsertDefaultsQueryParam(true));
        return ServiceConverter.newServiceConverter(serviceJson).getService();
    }

    @Override
    public List<ServiceSummary> serviceLs(String swarmId) {
        List<ServiceJson> services = servicesApi.listServices(swarmId, new ServicesListParameters());
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
            List<TaskJson> tasks = tasksApi.listTasks(swarmId, tasksListParameters);
            if (replicated != null) {
                serviceSummary.setReplicas(replicated.getReplicas());
            } else {
                serviceSummary.setGlobal(true);
                Map<String, Long> groupBySlot = tasks
                        .stream()
                        .collect(Collectors.groupingBy(TaskJson::getNodeId, Collectors.counting()));
                serviceSummary.setReplicas((long) groupBySlot.size());
            }
            tasks.removeIf(task -> !TasksFilters.RUNNING_STATE.equals(task.getStatus().getTaskState()));
            serviceSummary.setRunningReplicas((long) tasks.size());
            EndpointSpecJson endpointSpecJson = service.getSpec().getEndpointSpec();
            List<Port> ports = ServiceConverter.getPorts(endpointSpecJson);
            if (!ports.isEmpty()) {
                serviceSummary.setPorts(ports);
            }
            servicesSummary.add(serviceSummary);
        }
        return servicesSummary;
    }

    @Override
    public State servicePs(String swarmId, String serviceId) {
        ServiceJson service = servicesApi.inspectService(swarmId, serviceId,
                new ServiceInspectParameters().setInsertDefaultsQueryParam(true));
        State state =  new State();
        if (service != null) {
            TasksListParameters tasksListParameters = new TasksListParameters();
            TasksFilters filters = new TasksFilters();
            filters.setService(serviceId);
            tasksListParameters.setFilters(filters);
            List<Task> tasks = TaskJsonHelper.getTasks(tasksApi.listTasks(swarmId, tasksListParameters));
            Map<String, List<Task>> tasksByService = new HashMap<>();
            tasks.forEach(task -> {
                task.setServiceName(service.getSpec().getName());
                List<Task> tasksAux = tasksByService.computeIfAbsent(task.getNodeId(), k -> new ArrayList<>());
                tasksAux.add(task);
            });
            for (Map.Entry<String, List<Task>> entry : tasksByService.entrySet()) {
                NodeJson nodeJson = nodesApi.inspectNode(swarmId, entry.getKey());
                if (nodeJson != null) {
                    for (Task task : entry.getValue()) {
                        task.setNodeHostname(nodeJson.getDescription().getHostname());
                    }
                }
            }
            TaskJsonHelper.sortTasks(tasks);
            state.setTasks(tasks);
        }
        return state;
    }

    @Override
    public Service serviceCreate(String swarmId, Service service) {
        ServiceCreateParameters createParameters = new ServiceCreateParameters();
        ServiceSpecJson serviceSpecJson = getServiceSpecJson(new ServiceSpecJson(), service, true);
        createParameters.setService(serviceSpecJson);
        String xRegistryAuthHeader = getAuth(service);
        if (StringUtils.isNotEmpty(xRegistryAuthHeader)) {
            createParameters.setXRegistryAuthHeader(xRegistryAuthHeader);
        }
        ServiceGeneralResponseJson responseJson = servicesApi.createService(swarmId, createParameters);
        service.setId(responseJson.getId());
        return service;
    }

    @Override
    public void serviceUpdate(String swarmId, String serviceId, Service service) {
        ServiceUpdateParameters updateParameters = new ServiceUpdateParameters();
        ServiceJson serviceJson = servicesApi.inspectService(swarmId, serviceId,
                new ServiceInspectParameters().setInsertDefaultsQueryParam(true));
        VersionJson versionJson = serviceJson.getVersion();
        updateParameters.setVersionQueryParam(versionJson.getIndex());
        updateParameters.setRollback(service.getRollback());
        ServiceSpecJson serviceSpecJson = getServiceSpecJson(serviceJson.getSpec(), service, false);
        updateParameters.setService(serviceSpecJson);
        String xRegistryAuthHeader = getAuth(service);
        if (StringUtils.isNotEmpty(xRegistryAuthHeader)) {
            updateParameters.setXRegistryAuthHeader(xRegistryAuthHeader);
        } else {
            updateParameters.setRegistryAuthFromSpec();
        }
        servicesApi.updateService(swarmId, serviceId, updateParameters);
    }

    private String getAuth(Service service) {
        String xRegistryAuthHeader = null;
        if (service.getRegistryName() != null) {
            RegistryUser registryUser = registryUserRepository.findByNameAndUserOwner(service.getRegistryName(), UserUtil.getCurrentUsername());
            if (registryUser == null) {
                throw new RegistryUserNotFound();
            }
            xRegistryAuthHeader = getXRegistryAuthHeader(registryUser.getRegistryUsername(),
                    registryUser.getRegistryPassword(),
                    registryUser.getUrl());
        } else if (service.getRegistryUsername() != null && service.getRegistryPassword() != null) {
            xRegistryAuthHeader = getXRegistryAuthHeader(service.getRegistryUsername(),
                    service.getRegistryPassword(),
                    getUrlFromImage(service.getImage(), service.isDockerHubRegistry()));
        }
        return xRegistryAuthHeader;
    }

    @Override
    public void serviceRm(String swarmId, String serviceId) {
        servicesApi.deleteService(swarmId, serviceId);
    }

    @Override
    public Logs serviceLogs(String swarmId, String serviceId) {
        ServiceLogsParameters parameters = new ServiceLogsParameters();
        parameters.setStderrQueryParam(true);
        parameters.setStdoutQueryParam(true);
        parameters.setTimestampsQueryParam(true);
        parameters.setDetailsQueryParam(true);
        parameters.setTailQueryParam(MAX_LOGS);
        List<LogLine> logLines = new ArrayList<>();
        Set<LogFilter> logFilters = new TreeSet<>((filter1, filter2) -> {
            if (filter1.getReplica() != null && filter2.getReplica() != null) {
                Long result = filter1.getReplica() - filter2.getReplica();
                if (result != 0) {
                    return result.intValue();
                }
            }
            return filter1.getTaskId().compareTo(filter2.getTaskId());
        });
        Logs logs = new Logs();
        Map<String, TaskJson> tasksById = new HashMap<>();

        TasksListParameters tasksListParameters = new TasksListParameters();
        TasksFilters tasksFilters =  new TasksFilters();
        tasksFilters.setService(serviceId);
        tasksListParameters.setFilters(tasksFilters);
        List<TaskJson> tasks = tasksApi.listTasks(swarmId, tasksListParameters);
        tasks.forEach(task -> tasksById.put(StringUtils.substring(task.getId(), 0, 12), task));

        byte[] logBuf = servicesApi.getServiceLogs(swarmId, serviceId, parameters);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(logBuf);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF8"));
            String entry;
            while ((entry = reader.readLine()) != null) {
                LogLine logLine = convertLogString(serviceId, tasksById, entry);
                if (logLine != null && StringUtils.isNotEmpty(logLine.getTaskId())) {
                    logLines.add(logLine);
                    logFilters.add(new LogFilter(logLine.getTaskId(), logLine.getReplica(), logLine.getServiceId(), logLine.getNodeId()));
                }
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
        if (StringUtils.isNotEmpty(logString)) {
            //remove 8 bytes padding padding
            String log = logString;
            if (StringUtils.length(logString) >= 8) {
                log = logString.substring(8);
            }
            String[] parts = log.split(" ");
            if (parts.length > 2) {
                String timestampStr = parts[0];
                long timestamp = 0L;
                if (StringUtils.isNotEmpty(timestampStr)) {
                    ZonedDateTime date = DockerDateFormatter.fromDateStringToZonedDateTime(timestampStr);
                    if (date != null) {
                        timestamp = date.toInstant().toEpochMilli();
                    }
                }
                String detailsStr = parts[1];
                String nodeId = "";
                String tasKId = "";
                Long replica = 0L;
                String[] details = detailsStr.split(",");
                for (String detail : details) {
                    if (detail.startsWith(NODE_DETAIL)) {
                        nodeId = detail.substring(NODE_DETAIL.length() + 1);
                    }
                    if (detail.startsWith(TASK_DETAIL)) {
                        tasKId = StringUtils.substring(detail.substring(TASK_DETAIL.length() + 1), 0, 12);
                        Long slot = tasksById.get(tasKId).getSlot();
                        replica = slot != null ? slot : 0;
                    }
                }
                StringBuilder messageBuilder = new StringBuilder(parts[2]);
                for (int i = 3; i < parts.length; i++) {
                    messageBuilder.append(" ").append(parts[i]);
                }
                String message = messageBuilder.toString();
                return new LogLine(serviceId, nodeId, tasKId, replica, message, timestamp);
            }
        }
        return null;
    }

    private String getXRegistryAuthHeader(String username, String password, String url) {
        String xRegistryAuthHeader = "";
        try {
            JSONObject object = new JSONObject()
                    .put("username", username)
                    .put("password", EncoderDecoder.base64URLDecode(password))
                    .put("serveraddress", url);
            xRegistryAuthHeader = object.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return EncoderDecoder.base64Encode(xRegistryAuthHeader);
    }

    private String getUrlFromImage(String image, Boolean isDockerHub) {
        if (StringUtils.isNotEmpty(image) && StringUtils.contains(image, "/") && (isDockerHub == null || !isDockerHub)) {
            return image.substring(0, image.indexOf("/"));
        }
        return "";
    }

    private ServiceSpecJson getServiceSpecJson(ServiceSpecJson serviceSpecJson, Service service, boolean create) {
        ServiceSpecJsonConverter helper = ServiceSpecJsonConverter.createNewConverter(serviceSpecJson);
        if (create) {
            helper.setName(service.getName());
            helper.setMode(service.isGlobal());
        }
        helper.setReplicas(service.getReplicas())
                .setPorts(service.getPorts())
                .setEnvs(service.getEnv())
                .setImage(service.getImage())
                .setConfigs(service.getConfigs())
                .setSecrets(service.getSecrets())
                .setArgs(service.getArgs())
                .setConstraints(service.getConstraints())
                .setContainerLabels(service.getContainerLabels())
                .setLabels(service.getLabels())
                .setDnsOptions(service.getDnsOptions())
                .setDnsSearches(service.getDnsSearches())
                .setDnsServers(service.getDnsServers())
                .setEndpointMode(service.getEndpointMode())
                .setEntrypoint(service.getEntrypoint())
                .setForceUpdate(service.getForceUpdate())
                .setGroups(service.getGroups())
                .setUser(service.getUser())
                .setWorkDir(service.getWorkDir())
                .setHostname(service.getHostname())
                .setHosts(service.getHosts())
                .setStopGracePeriod(service.getStopGracePeriod())
                .setLogDriver(service.getLogDriver())
                .setLogOptions(service.getLogOptions())
                .setMounts(service.getMounts())
                .setNetworks(service.getNetworks())
                .setHealthCmd(service.getHealthCmd())
                .setHealthInterval(service.getHealthInterval())
                .setHealthRetries(service.getHealthRetries())
                .setHealthStartPeriod(service.getHealthStartPeriod())
                .setHealthTimeout(service.getHealthTimeout())
                .setNoHealthCheck(service.getNoHealthCheck())
                .setPlacementPreferences(service.getPlacementPreferences())
                .setReadOnly(service.getReadOnly())
                .setStopSignal(service.getStopSignal())
                .setResourcesCpu(service.getLimitCpu(), true)
                .setResourcesCpu(service.getReserveCpu(), false)
                .setResourcesMemory(service.getLimitMemory(), true)
                .setResourcesMemory(service.getReserveMemory(), false)
                .setRestartCondition(service.getRestartCondition())
                .setRestartDelay(service.getRestartDelay())
                .setRestartMaxAttempts(service.getRestartMaxAttempts())
                .setRestartWindow(service.getRestartWindow())
                .setConfigDelay(service.getUpdateDelay(), false)
                .setConfigFailureAction(service.getUpdateFailureAction(), false)
                .setConfigFailureRatio(service.getUpdateMaxFailureRatio(), false)
                .setConfigMonitor(service.getUpdateMonitor(), false)
                .setConfigOrder(service.getUpdateOrder(), false)
                .setConfigParallelism(service.getUpdateParallelism(), false)
                .setConfigDelay(service.getRollbackDelay(), true)
                .setConfigFailureAction(service.getRollbackFailureAction(), true)
                .setConfigFailureRatio(service.getRollbackMaxFailureRatio(), true)
                .setConfigMonitor(service.getRollbackMonitor(), true)
                .setConfigOrder(service.getRollbackOrder(), true)
                .setConfigParallelism(service.getRollbackParallelism(), true);
        return helper.getServiceSpecJson();
    }

}


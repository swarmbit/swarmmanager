package com.swarmmanager.docker.cli.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Service {

    private String id;

    private Long createdAt;

    private Long updatedAt;

    private String image;

    private Boolean global;

    private String name;

    private Long replicas;

    private List<Port> ports;

    private List<String> env;

    private List<String> configs;

    private List<String> secrets;

    private Map<String, String> labels;

    private Map<String, String> constraints;

    private Map<String, String> placementPreferences;

    /**
     * Container general
     */
    private Boolean readOnly;
    private String entrypoint;
    private Map<String, String> containerLabels;
    private List<String> args;
    private List<String> groups;
    private String user;
    private String workDir;
    private Long stopGracePeriod;
    private String stopSignal;

    /**
     * Logs
     */
    private String logDriver;
    private Map<String, String> logOptions;

    /**
     * Registry
     */
    private Boolean isDockerHubRegistry;
    private String registryName;
    private String registryUsername;
    private String registryPassword;

    private List<Mount> mounts;

    private Boolean forceUpdate;

    /**
     * Network
     */
    private String endpointMode;
    private List<String> networks;
    private String hostname;
    private List<String> hosts;

    /**
     * Dns
     */
    private List<String> dnsServers;
    private List<String> dnsOptions;
    private List<String> dnsSearches;

    /**
     * Health Check
     */
    private String healthCmd;
    private Integer healthRetries;
    private Long healthStartPeriod;
    private Long healthInterval;
    private Long healthTimeout;
    private Boolean noHealthCheck;

    /**
     * Reserve
     */
    private Long reserveCpu;
    private Long reserveMemory;

    /**
     * Limits
     */
    private Long limitCpu;
    private Long limitMemory;

    /**
     * Restart config
     */
    private String restartCondition;
    private Long restartDelay;
    private Long restartMaxAttempts;
    private Long restartWindow;

    /**
     * Update config
     */
    private Long updateDelay;
    private String updateFailureAction;
    private Double updateMaxFailureRatio;
    private Long updateMonitor;
    private String updateOrder;
    private Long updateParallelism;

    /**
     * Rollback config
     */
    private Long rollbackDelay;
    private String rollbackFailureAction;
    private Double rollbackMaxFailureRatio;
    private Long rollbackMonitor;
    private String rollbackOrder;
    private Long rollbackParallelism;

    private Boolean rollback;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean isGlobal() {
        return global;
    }

    public void setGlobal(Boolean global) {
        this.global = global;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getReplicas() {
        return replicas;
    }

    public void setReplicas(Long replicas) {
        this.replicas = replicas;
    }

    public List<Port> getPorts() {
        return ports;
    }

    public void setPorts(List<Port> ports) {
        this.ports = ports;
    }

    public List<String> getEnv() {
        return env;
    }

    public void setEnv(List<String> env) {
        this.env = env;
    }

    public String getRegistryName() {
        return registryName;
    }

    public void setRegistryName(String registryName) {
        this.registryName = registryName;
    }

    public List<String> getConfigs() {
        return configs;
    }

    public void setConfigs(List<String> configs) {
        this.configs = configs;
    }

    public List<String> getSecrets() {
        return secrets;
    }

    public void setSecrets(List<String> secrets) {
        this.secrets = secrets;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }

    public Map<String, String> getContainerLabels() {
        return containerLabels;
    }

    public void setContainerLabels(Map<String, String> containerLabels) {
        this.containerLabels = containerLabels;
    }

    public Map<String, String> getConstraints() {
        return constraints;
    }

    public void setConstraints(Map<String, String> constraints) {
        this.constraints = constraints;
    }

    public Map<String, String> getPlacementPreferences() {
        return placementPreferences;
    }

    public void setPlacementPreferences(Map<String, String> placementPreferences) {
        this.placementPreferences = placementPreferences;
    }

    public Boolean getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public String getEntrypoint() {
        return entrypoint;
    }

    public void setEntrypoint(String entrypoint) {
        this.entrypoint = entrypoint;
    }

    public List<String> getArgs() {
        return args;
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public String getLogDriver() {
        return logDriver;
    }

    public void setLogDriver(String logDriver) {
        this.logDriver = logDriver;
    }

    public Map<String, String> getLogOptions() {
        return logOptions;
    }

    public void setLogOptions(Map<String, String> logOptions) {
        this.logOptions = logOptions;
    }

    public List<Mount> getMounts() {
        return mounts;
    }

    public void setMounts(List<Mount> mounts) {
        this.mounts = mounts;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getWorkDir() {
        return workDir;
    }

    public void setWorkDir(String workDir) {
        this.workDir = workDir;
    }

    public Long getStopGracePeriod() {
        return stopGracePeriod;
    }

    public void setStopGracePeriod(Long stopGracePeriod) {
        this.stopGracePeriod = stopGracePeriod;
    }

    public String getStopSignal() {
        return stopSignal;
    }

    public void setStopSignal(String stopSignal) {
        this.stopSignal = stopSignal;
    }

    public String getEndpointMode() {
        return endpointMode;
    }

    public void setEndpointMode(String endpointMode) {
        this.endpointMode = endpointMode;
    }

    public List<String> getNetworks() {
        return networks;
    }

    public void setNetworks(List<String> networks) {
        this.networks = networks;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public List<String> getHosts() {
        return hosts;
    }

    public void setHosts(List<String> hosts) {
        this.hosts = hosts;
    }

    public List<String> getDnsServers() {
        return dnsServers;
    }

    public void setDnsServers(List<String> dnsServers) {
        this.dnsServers = dnsServers;
    }

    public List<String> getDnsOptions() {
        return dnsOptions;
    }

    public void setDnsOptions(List<String> dnsOptions) {
        this.dnsOptions = dnsOptions;
    }

    public List<String> getDnsSearches() {
        return dnsSearches;
    }

    public void setDnsSearches(List<String> dansSearches) {
        this.dnsSearches = dansSearches;
    }

    public String getHealthCmd() {
        return healthCmd;
    }

    public void setHealthCmd(String healthCmd) {
        this.healthCmd = healthCmd;
    }

    public Integer getHealthRetries() {
        return healthRetries;
    }

    public void setHealthRetries(Integer healthRetries) {
        this.healthRetries = healthRetries;
    }

    public Long getHealthStartPeriod() {
        return healthStartPeriod;
    }

    public void setHealthStartPeriod(Long healthStartPeriod) {
        this.healthStartPeriod = healthStartPeriod;
    }

    public Long getHealthInterval() {
        return healthInterval;
    }

    public void setHealthInterval(Long healthInterval) {
        this.healthInterval = healthInterval;
    }

    public Long getHealthTimeout() {
        return healthTimeout;
    }

    public void setHealthTimeout(Long healthTimeout) {
        this.healthTimeout = healthTimeout;
    }

    public Boolean getNoHealthCheck() {
        return noHealthCheck;
    }

    public void setNoHealthCheck(Boolean noHealthCheck) {
        this.noHealthCheck = noHealthCheck;
    }

    public Long getReserveCpu() {
        return reserveCpu;
    }

    public void setReserveCpu(Long reserveCpu) {
        this.reserveCpu = reserveCpu;
    }

    public Long getReserveMemory() {
        return reserveMemory;
    }

    public void setReserveMemory(Long reserveMemory) {
        this.reserveMemory = reserveMemory;
    }

    public Long getLimitCpu() {
        return limitCpu;
    }

    public void setLimitCpu(Long limitCpu) {
        this.limitCpu = limitCpu;
    }

    public Long getLimitMemory() {
        return limitMemory;
    }

    public void setLimitMemory(Long limitMemory) {
        this.limitMemory = limitMemory;
    }

    public String getRestartCondition() {
        return restartCondition;
    }

    public void setRestartCondition(String restartCondition) {
        this.restartCondition = restartCondition;
    }

    public Long getRestartDelay() {
        return restartDelay;
    }

    public void setRestartDelay(Long restartDelay) {
        this.restartDelay = restartDelay;
    }

    public Long getRestartMaxAttempts() {
        return restartMaxAttempts;
    }

    public void setRestartMaxAttempts(Long restartMaxAttempts) {
        this.restartMaxAttempts = restartMaxAttempts;
    }

    public Long getRestartWindow() {
        return restartWindow;
    }

    public void setRestartWindow(Long restartWindow) {
        this.restartWindow = restartWindow;
    }

    public Long getUpdateDelay() {
        return updateDelay;
    }

    public void setUpdateDelay(Long updateDelay) {
        this.updateDelay = updateDelay;
    }

    public String getUpdateFailureAction() {
        return updateFailureAction;
    }

    public void setUpdateFailureAction(String updateFailureAction) {
        this.updateFailureAction = updateFailureAction;
    }

    public Double getUpdateMaxFailureRatio() {
        return updateMaxFailureRatio;
    }

    public Long getUpdateMonitor() {
        return updateMonitor;
    }

    public void setUpdateMonitor(Long updateMonitor) {
        this.updateMonitor = updateMonitor;
    }

    public String getUpdateOrder() {
        return updateOrder;
    }

    public void setUpdateOrder(String updateOrder) {
        this.updateOrder = updateOrder;
    }

    public Long getUpdateParallelism() {
        return updateParallelism;
    }

    public void setUpdateParallelism(Long updateParallelism) {
        this.updateParallelism = updateParallelism;
    }

    public Long getRollbackDelay() {
        return rollbackDelay;
    }

    public void setRollbackDelay(Long rollbackDelay) {
        this.rollbackDelay = rollbackDelay;
    }

    public String getRollbackFailureAction() {
        return rollbackFailureAction;
    }

    public void setRollbackFailureAction(String rollbackFailureAction) {
        this.rollbackFailureAction = rollbackFailureAction;
    }

    public Double getRollbackMaxFailureRatio() {
        return rollbackMaxFailureRatio;
    }

    public void setRollbackMaxFailureRatio(Double rollbackMaxFailureRatio) {
        this.rollbackMaxFailureRatio = rollbackMaxFailureRatio;
    }

    public Long getRollbackMonitor() {
        return rollbackMonitor;
    }

    public void setRollbackMonitor(Long rollbackMonitor) {
        this.rollbackMonitor = rollbackMonitor;
    }

    public String getRollbackOrder() {
        return rollbackOrder;
    }

    public void setRollbackOrder(String rollbackOrder) {
        this.rollbackOrder = rollbackOrder;
    }

    public Long getRollbackParallelism() {
        return rollbackParallelism;
    }

    public void setRollbackParallelism(Long rollbackParallelism) {
        this.rollbackParallelism = rollbackParallelism;
    }

    public Boolean getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(Boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public String getRegistryUsername() {
        return registryUsername;
    }

    public void setRegistryUsername(String registryUsername) {
        this.registryUsername = registryUsername;
    }

    public String getRegistryPassword() {
        return registryPassword;
    }

    public void setRegistryPassword(String registryPassword) {
        this.registryPassword = registryPassword;
    }

    public void setUpdateMaxFailureRatio(Double updateMaxFailureRatio) {
        this.updateMaxFailureRatio = updateMaxFailureRatio;
    }

    public Boolean isDockerHubRegistry() {
        return isDockerHubRegistry;
    }

    public void setDockerHubRegistry(Boolean dockerHubRegistry) {
        isDockerHubRegistry = dockerHubRegistry;
    }

    public Boolean getRollback() {
        return rollback;
    }

    public void setRollback(Boolean rollback) {
        this.rollback = rollback;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Service{");
        sb.append("id='").append(id).append('\'');
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", image='").append(image).append('\'');
        sb.append(", global=").append(global);
        sb.append(", name='").append(name).append('\'');
        sb.append(", replicas=").append(replicas);
        sb.append(", ports=").append(ports);
        sb.append(", env=").append(env);
        sb.append(", configs=").append(configs);
        sb.append(", secrets=").append(secrets);
        sb.append(", labels=").append(labels);
        sb.append(", constraints=").append(constraints);
        sb.append(", placementPreferences=").append(placementPreferences);
        sb.append(", readOnly=").append(readOnly);
        sb.append(", entrypoint='").append(entrypoint).append('\'');
        sb.append(", containerLabels=").append(containerLabels);
        sb.append(", args=").append(args);
        sb.append(", groups=").append(groups);
        sb.append(", user='").append(user).append('\'');
        sb.append(", workDir='").append(workDir).append('\'');
        sb.append(", stopGracePeriod=").append(stopGracePeriod);
        sb.append(", stopSignal='").append(stopSignal).append('\'');
        sb.append(", logDriver='").append(logDriver).append('\'');
        sb.append(", logOptions=").append(logOptions);
        sb.append(", isDockerHubRegistry=").append(isDockerHubRegistry);
        sb.append(", registryName='").append(registryName).append('\'');
        sb.append(", registryUsername='").append(registryUsername).append('\'');
        sb.append(", registryPassword='").append(registryPassword).append('\'');
        sb.append(", mounts=").append(mounts);
        sb.append(", forceUpdate=").append(forceUpdate);
        sb.append(", endpointMode='").append(endpointMode).append('\'');
        sb.append(", networks=").append(networks);
        sb.append(", hostname='").append(hostname).append('\'');
        sb.append(", hosts=").append(hosts);
        sb.append(", dnsServers=").append(dnsServers);
        sb.append(", dnsOptions=").append(dnsOptions);
        sb.append(", dnsSearches=").append(dnsSearches);
        sb.append(", healthCmd='").append(healthCmd).append('\'');
        sb.append(", healthRetries=").append(healthRetries);
        sb.append(", healthStartPeriod=").append(healthStartPeriod);
        sb.append(", healthInterval=").append(healthInterval);
        sb.append(", healthTimeout=").append(healthTimeout);
        sb.append(", noHealthCheck=").append(noHealthCheck);
        sb.append(", reserveCpu=").append(reserveCpu);
        sb.append(", reserveMemory=").append(reserveMemory);
        sb.append(", limitCpu=").append(limitCpu);
        sb.append(", limitMemory=").append(limitMemory);
        sb.append(", restartCondition='").append(restartCondition).append('\'');
        sb.append(", restartDelay=").append(restartDelay);
        sb.append(", restartMaxAttempts=").append(restartMaxAttempts);
        sb.append(", restartWindow=").append(restartWindow);
        sb.append(", updateDelay=").append(updateDelay);
        sb.append(", updateFailureAction='").append(updateFailureAction).append('\'');
        sb.append(", updateMaxFailureRatio=").append(updateMaxFailureRatio);
        sb.append(", updateMonitor=").append(updateMonitor);
        sb.append(", updateOrder='").append(updateOrder).append('\'');
        sb.append(", updateParallelism=").append(updateParallelism);
        sb.append(", rollbackDelay=").append(rollbackDelay);
        sb.append(", rollbackFailureAction='").append(rollbackFailureAction).append('\'');
        sb.append(", rollbackMaxFailureRatio=").append(rollbackMaxFailureRatio);
        sb.append(", rollbackMonitor=").append(rollbackMonitor);
        sb.append(", rollbackOrder='").append(rollbackOrder).append('\'');
        sb.append(", rollbackParallelism=").append(rollbackParallelism);
        sb.append(", rollback=").append(rollback);
        sb.append('}');
        return sb.toString();
    }
}

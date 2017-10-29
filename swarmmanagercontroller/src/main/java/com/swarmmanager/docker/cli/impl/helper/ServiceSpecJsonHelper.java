package com.swarmmanager.docker.cli.impl.helper;

import com.swarmmanager.docker.api.common.json.ServiceSpecJson;
import com.swarmmanager.docker.api.common.json.inner.*;
import com.swarmmanager.docker.cli.model.Mount;
import com.swarmmanager.docker.cli.model.Port;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceSpecJsonHelper {

    private ServiceSpecJson serviceSpecJson;

    private ServiceSpecJsonHelper() {
    }

    public static ServiceSpecJsonHelper createNewHelper(ServiceSpecJson serviceSpecJson) {
        ServiceSpecJsonHelper builder = new ServiceSpecJsonHelper();
        TaskSpecJson taskSpecJson = serviceSpecJson.getTaskTemplate();
        if (taskSpecJson == null) {
            taskSpecJson = new TaskSpecJson();
        }
        ContainerSpecJson containerSpecJson = taskSpecJson.getContainerSpec();
        if (containerSpecJson == null) {
            containerSpecJson = new ContainerSpecJson();
            taskSpecJson.setContainerSpec(containerSpecJson);
        }
        serviceSpecJson.setTaskTemplate(taskSpecJson);
        builder.serviceSpecJson = serviceSpecJson;
        return builder;
    }

    public static ServiceSpecJsonHelper createNewHelper() {
        ServiceSpecJson serviceSpecJson = new ServiceSpecJson();
        TaskSpecJson taskSpecJson = new TaskSpecJson();
        taskSpecJson.setContainerSpec(new ContainerSpecJson());
        serviceSpecJson.setTaskTemplate(taskSpecJson);
        return createNewHelper(serviceSpecJson);
    }

    public ServiceSpecJsonHelper setName(String name) {
        serviceSpecJson.setName(name);
        return this;
    }

    public ServiceSpecJsonHelper setMode(Boolean isGlobal) {
        return setMode(isGlobal, null);
    }

    public ServiceSpecJsonHelper setMode(Boolean isGlobal, Long replicas) {
        if (serviceSpecJson.getMode() == null) {
            ServiceModeJson serviceModeJson = new ServiceModeJson();
            if (isGlobal != null && isGlobal) {
                GlobalServiceJson globalServiceJson = new GlobalServiceJson();
                serviceModeJson.setGlobal(globalServiceJson);
            } else {
                ReplicatedServiceJson replicatedServiceJson = new ReplicatedServiceJson();
                if (replicas != null) {
                    replicatedServiceJson.setReplicas(replicas);
                } else {
                    replicatedServiceJson.setReplicas(1L);
                }
                serviceModeJson.setReplicated(replicatedServiceJson);
            }
            serviceSpecJson.setMode(serviceModeJson);
        }
        return this;
    }

    public ServiceSpecJsonHelper setReplicas(Long numberOfReplicas) {
        if (serviceSpecJson.getMode().getReplicated() != null && numberOfReplicas != null) {
            ReplicatedServiceJson replicatedServiceJson = new ReplicatedServiceJson();
            replicatedServiceJson.setReplicas(numberOfReplicas);
            ServiceModeJson serviceModeJson = new ServiceModeJson();
            serviceModeJson.setReplicated(replicatedServiceJson);
            serviceSpecJson.setMode(serviceModeJson);
        }
        return this;
    }

    public ServiceSpecJsonHelper setPorts(List<Port> ports) {
        if (ports != null && !ports.isEmpty()) {
            EndpointSpecJson endpointSpecJson = serviceSpecJson.getEndpointSpec();
            if (endpointSpecJson == null) {
                endpointSpecJson  = new EndpointSpecJson();
            }
            PortConfigJson[] portConfigs = new PortConfigJson[ports.size()];
            for (int i = 0; i < portConfigs.length; i++) {
                Port port = ports.get(i);
                PortConfigJson portConfig = new PortConfigJson();
                portConfig.setProtocol(Port.Protocol.getProtocol(port.getProtocol()).toString().toLowerCase());
                portConfig.setPublishedPort(port.getPublished());
                portConfig.setTargetPort(port.getTarget());
                portConfigs[i] = portConfig;
            }
            endpointSpecJson.setPorts(portConfigs);
            serviceSpecJson.setEndpointSpec(endpointSpecJson);
        }
        return this;
    }

    public ServiceSpecJsonHelper setImage(String image) {
        if (image != null) {
            TaskSpecJson taskSpecJson = serviceSpecJson.getTaskTemplate();
            ContainerSpecJson containerSpecJson = taskSpecJson.getContainerSpec();
            containerSpecJson.setImage(image);
            taskSpecJson.setContainerSpec(containerSpecJson);
            serviceSpecJson.setTaskTemplate(taskSpecJson);
        }
        return this;
    }

    public ServiceSpecJsonHelper setConfigs(List<String> configs) {
        if (configs != null) {
            List<ConfigReferenceJson> configsJson = new ArrayList<>();
            for (String config : configs) {
                ConfigReferenceJson configReferenceJson = new ConfigReferenceJson();
                configReferenceJson.setConfigID(config);
                configsJson.add(configReferenceJson);
            }
            serviceSpecJson.getTaskTemplate().getContainerSpec().setConfigs(configsJson.toArray(new ConfigReferenceJson[0]));
        }
        return this;
    }

    public ServiceSpecJsonHelper setSecrets(List<String> secrets) {
        if (secrets != null) {
            List<SecretReferenceJson> secretsJson = new ArrayList<>();
            for (String secret : secrets) {
                SecretReferenceJson secretReferenceJson = new SecretReferenceJson();
                secretReferenceJson.setSecretID(secret);
                secretsJson.add(secretReferenceJson);
            }
            serviceSpecJson.getTaskTemplate().getContainerSpec().setSecrets(secretsJson.toArray(new SecretReferenceJson[0]));
        }
        return this;
    }

    public ServiceSpecJsonHelper setLabels(Map<String, String> labels) {
        if (labels != null) {
            serviceSpecJson.setLabels(labels);
        }
        return this;
    }

    public ServiceSpecJsonHelper setContainerLabels(Map<String, String> containerLabels) {
        if (containerLabels != null) {
            serviceSpecJson.getTaskTemplate().getContainerSpec().setLabels(containerLabels);
        }
        return this;
    }

    public ServiceSpecJsonHelper setConstraints(Map<String, String> constraints) {
        if (constraints != null) {
            List<String> constraintsString = new ArrayList<>();
            PlacementJson placementJson = serviceSpecJson.getTaskTemplate().getPlacement();
            if (placementJson == null) {
                placementJson = new PlacementJson();
            }
            for (Map.Entry<String, String> constraintEntry : constraints.entrySet()) {
                constraintsString.add(constraintEntry.getKey() + "=" + constraintEntry.getValue());
            }
            placementJson.setConstraints(constraintsString.toArray(new String[0]));
            serviceSpecJson.getTaskTemplate().setPlacement(placementJson);
        }
        return this;
    }

    public ServiceSpecJsonHelper setPlacementPreferences(Map<String, String> placementPreferences) {
        if (placementPreferences != null) {
            List<PlacementPreferenceJson> placementPreferenceJsons = new ArrayList<>();
            PlacementJson placementJson = serviceSpecJson.getTaskTemplate().getPlacement();
            if (placementJson == null) {
                placementJson = new PlacementJson();
            }
            for (Map.Entry<String, String> entry : placementPreferences.entrySet()) {
                if (StringUtils.equals("spread", entry.getKey())) {
                    PlacementPreferenceJson placementPreferenceJson = new PlacementPreferenceJson();
                    SpreadOverJson spreadOverJson = new SpreadOverJson();
                    spreadOverJson.setSpreadDescriptor(entry.getValue());
                    placementPreferenceJson.setSpread(spreadOverJson);
                    placementPreferenceJsons.add(placementPreferenceJson);
                }
            }
            placementJson.setPreferences(placementPreferenceJsons.toArray(new PlacementPreferenceJson[0]));
        }
        return this;
    }

    public ServiceSpecJsonHelper setReadOnly(Boolean readOnly) {
        if (readOnly != null) {
            serviceSpecJson.getTaskTemplate().getContainerSpec().setReadOnly(readOnly);
        }
        return this;
    }

    public ServiceSpecJsonHelper setEntrypoint(String entrypoint) {
        if (entrypoint != null) {
            serviceSpecJson.getTaskTemplate().getContainerSpec().setCommand(new String[] { entrypoint });
        }
        return this;
    }

    public ServiceSpecJsonHelper setArgs(List<String> args) {
        if (args != null) {
            serviceSpecJson.getTaskTemplate().getContainerSpec().setArgs(args.toArray(new String[0]));
        }
        return this;
    }

    public ServiceSpecJsonHelper setGroups(List<String> groups) {
        if (groups != null) {
            serviceSpecJson.getTaskTemplate().getContainerSpec().setGroups(groups.toArray(new String[0]));
        }
        return this;
    }

    public ServiceSpecJsonHelper setLogDriver(String logDriver) {
        if (logDriver != null) {
            DriverJson driverJson = serviceSpecJson.getTaskTemplate().getLogDriver();
            if (driverJson != null && StringUtils.isNotEmpty(driverJson.getName())) {
                driverJson.setOptions(null);
            } else {
                driverJson = new DriverJson();
            }
            driverJson.setName(logDriver);
            serviceSpecJson.getTaskTemplate().setLogDriver(driverJson);
        }
        return this;
    }

    public ServiceSpecJsonHelper setLogOptions(Map<String, String> logOptions) {
        if (logOptions != null) {
            DriverJson driverJson = serviceSpecJson.getTaskTemplate().getLogDriver();
            if (driverJson == null) {
                driverJson = new DriverJson();
            }
            driverJson.setOptions(logOptions);
            serviceSpecJson.getTaskTemplate().setLogDriver(driverJson);
        }
        return this;
    }

    public ServiceSpecJsonHelper setUser(String user) {
        if (user != null) {
            serviceSpecJson.getTaskTemplate().getContainerSpec().setUser(user);
        }
        return this;
    }

    public ServiceSpecJsonHelper setWorkDir(String workDir) {
        if (workDir != null) {
            serviceSpecJson.getTaskTemplate().getContainerSpec().setDir(workDir);
        }
        return this;
    }

    public ServiceSpecJsonHelper setStopGracePeriod(String stopGracePeriod) {
        if (stopGracePeriod != null) {
            serviceSpecJson.getTaskTemplate().getContainerSpec().setStopGracePeriod(stopGracePeriod);
        }
        return this;
    }

    public ServiceSpecJsonHelper setStopSignal(String stopSignal) {
        if (stopSignal != null) {
            serviceSpecJson.getTaskTemplate().getContainerSpec().setStopSignal(stopSignal);
        }
        return this;
    }

    public ServiceSpecJsonHelper setMounts(List<Mount> mounts) {
        if (mounts != null) {
            List<MountJson> mountJsons = new ArrayList<>();
            for (Mount mount : mounts) {
                MountJson mountJson = new MountJson();
                mountJson.setSource(mount.getSource());
                if (mount.getReadOnly() != null) {
                    mountJson.setReadOnly(mount.getReadOnly());
                }
                mountJson.setTarget(mount.getDestination());
                mountJson.setType(mount.getType());
                mountJson.setConsistency(mount.getConsistency());
                if (StringUtils.equals(Mount.BIND_TYPE, mount.getType()) && mount.getBindOptions() != null) {
                    BindOptionsJson bindOptionsJson = new BindOptionsJson();
                    bindOptionsJson.setPropagation(mount.getBindOptions().getPropagation());
                    mountJson.setBindOptions(bindOptionsJson);
                } else if (StringUtils.equals(Mount.VOLUME_TYPE, mount.getType()) && mount.getVolumeOptions() != null)  {
                    VolumeOptionsJson volumeOptionsJson = new VolumeOptionsJson();
                    DriverJson driverJson = new DriverJson();
                    driverJson.setName(mount.getVolumeOptions().getDriver());
                    driverJson.setOptions(mount.getVolumeOptions().getDriverOptions());
                    volumeOptionsJson.setDriver(driverJson);
                    volumeOptionsJson.setLabels(mount.getVolumeOptions().getLabels());
                    if (mount.getVolumeOptions().isNoCopy() != null) {
                        volumeOptionsJson.setNoCopy(mount.getVolumeOptions().isNoCopy());
                    }
                    mountJson.setVolumeOptions(volumeOptionsJson);
                } else if (StringUtils.equals(Mount.TMPFS_TYPE, mount.getType()) && mount.getTmpfsOptions() != null) {
                    TmpfsOptionsJson tmpfsOptionsJson = new TmpfsOptionsJson();
                    if (mount.getTmpfsOptions().getSizeBytes() != null) {
                        tmpfsOptionsJson.setSizeBytes(mount.getTmpfsOptions().getSizeBytes());
                    }
                    if (mount.getTmpfsOptions().getMode() != null) {
                        tmpfsOptionsJson.setMode(mount.getTmpfsOptions().getMode());
                    }
                    mountJson.setTmpfsOptions(tmpfsOptionsJson);
                }
                mountJsons.add(mountJson);
            }
            serviceSpecJson.getTaskTemplate().getContainerSpec().setMounts(mountJsons.toArray(new MountJson[0]));
        }
        return this;
    }

    public ServiceSpecJsonHelper setEndpointMode(String endpointMode) {
        if (endpointMode != null) {
            EndpointSpecJson endpointSpecJson = serviceSpecJson.getEndpointSpec();
            if (endpointSpecJson == null) {
                endpointSpecJson  = new EndpointSpecJson();
            }
            endpointSpecJson.setMode(endpointMode);
            serviceSpecJson.setEndpointSpec(endpointSpecJson);
        }
        return this;
    }

    public ServiceSpecJsonHelper setNetworks(List<String> networks) {
        if (networks != null) {
            NetworkAttachmentConfigJson[] networkAttachmentConfigJsons = new NetworkAttachmentConfigJson[networks.size()];
            for (int i = 0; i < networkAttachmentConfigJsons.length; i++) {
                String network = networks.get(i);
                NetworkAttachmentConfigJson networkAttachmentConfigJson = new NetworkAttachmentConfigJson();
                networkAttachmentConfigJson.setTarget(network);
                networkAttachmentConfigJsons[i] = networkAttachmentConfigJson;
            }
            serviceSpecJson.setNetworks(networkAttachmentConfigJsons);
        }
        return this;
    }

    public ServiceSpecJsonHelper setHostname(String hostname) {
        if (hostname != null) {
            serviceSpecJson.getTaskTemplate().getContainerSpec().setHostname(hostname);
        }
        return this;
    }

    public ServiceSpecJsonHelper setHosts(List<String> hosts) {
        if (hosts != null) {
            serviceSpecJson.getTaskTemplate().getContainerSpec().setHosts(hosts.toArray(new String[0]));
        }
        return this;
    }

    public ServiceSpecJsonHelper setDnsServers(List<String> dnsServers) {
        if (dnsServers != null) {
            DNSConfigJson dnsConfigJson = serviceSpecJson.getTaskTemplate().getContainerSpec().getDnsConfig();
            if (dnsConfigJson == null) {
                dnsConfigJson = new DNSConfigJson();
            }
            dnsConfigJson.setNameServers(dnsServers.toArray(new String[0]));
            serviceSpecJson.getTaskTemplate().getContainerSpec().setDnsConfig(dnsConfigJson);
        }
        return this;
    }

    public ServiceSpecJsonHelper setDnsOptions(List<String> dnsOptions) {
        if (dnsOptions != null) {
            DNSConfigJson dnsConfigJson = serviceSpecJson.getTaskTemplate().getContainerSpec().getDnsConfig();
            if (dnsConfigJson == null) {
                dnsConfigJson = new DNSConfigJson();
            }
            dnsConfigJson.setOptions(dnsOptions.toArray(new String[0]));
            serviceSpecJson.getTaskTemplate().getContainerSpec().setDnsConfig(dnsConfigJson);
        }
        return this;
    }

    public ServiceSpecJsonHelper setDnsSearches(List<String> dnsSearches) {
        if (dnsSearches != null) {
            DNSConfigJson dnsConfigJson = serviceSpecJson.getTaskTemplate().getContainerSpec().getDnsConfig();
            if (dnsConfigJson == null) {
                dnsConfigJson = new DNSConfigJson();
            }
            dnsConfigJson.setSearch(dnsSearches.toArray(new String[0]));
            serviceSpecJson.getTaskTemplate().getContainerSpec().setDnsConfig(dnsConfigJson);
        }
        return this;
    }

    public ServiceSpecJsonHelper setHealthCmd(String healthCmd) {
        if (healthCmd != null) {
            HealthConfigJson healthConfigJson = serviceSpecJson.getTaskTemplate().getContainerSpec().getHealthConfig();
            if (healthConfigJson == null) {
                healthConfigJson = new HealthConfigJson();
            }
            healthConfigJson.setTest(new String[] { "CMD-SHELL", healthCmd });
            serviceSpecJson.getTaskTemplate().getContainerSpec().setHealthConfig(healthConfigJson);
        }
        return this;
    }

    public ServiceSpecJsonHelper setNoHealthCheck(Boolean noHealthCheck) {
        if (noHealthCheck != null && noHealthCheck) {
            HealthConfigJson healthConfigJson = serviceSpecJson.getTaskTemplate().getContainerSpec().getHealthConfig();
            if (healthConfigJson == null) {
                healthConfigJson = new HealthConfigJson();
            }
            healthConfigJson.setTest(new String[] { "NONE" });
            serviceSpecJson.getTaskTemplate().getContainerSpec().setHealthConfig(healthConfigJson);
        }
        return this;
    }

    public ServiceSpecJsonHelper setHealthRetries(Integer healthRetries) {
        if (healthRetries != null) {
            HealthConfigJson healthConfigJson = serviceSpecJson.getTaskTemplate().getContainerSpec().getHealthConfig();
            if (healthConfigJson == null) {
                healthConfigJson = new HealthConfigJson();
            }
            healthConfigJson.setRetries(healthRetries);
            serviceSpecJson.getTaskTemplate().getContainerSpec().setHealthConfig(healthConfigJson);
        }
        return this;
    }

    public ServiceSpecJsonHelper setHealthStartPeriod(String healthStartPeriod) {
        if (healthStartPeriod != null) {
            HealthConfigJson healthConfigJson = serviceSpecJson.getTaskTemplate().getContainerSpec().getHealthConfig();
            if (healthConfigJson == null) {
                healthConfigJson = new HealthConfigJson();
            }
            healthConfigJson.setStartPeriod(healthStartPeriod);
            serviceSpecJson.getTaskTemplate().getContainerSpec().setHealthConfig(healthConfigJson);
        }
        return this;
    }

    public ServiceSpecJsonHelper setHealthTimeout(String healthTimeout) {
        if (healthTimeout != null) {
            HealthConfigJson healthConfigJson = serviceSpecJson.getTaskTemplate().getContainerSpec().getHealthConfig();
            if (healthConfigJson == null) {
                healthConfigJson = new HealthConfigJson();
            }
            healthConfigJson.setTimeout(healthTimeout);
            serviceSpecJson.getTaskTemplate().getContainerSpec().setHealthConfig(healthConfigJson);
        }
        return this;
    }

    public ServiceSpecJsonHelper setHealthInterval(String healthInterval) {
        if (healthInterval != null) {
            HealthConfigJson healthConfigJson = serviceSpecJson.getTaskTemplate().getContainerSpec().getHealthConfig();
            if (healthConfigJson == null) {
                healthConfigJson = new HealthConfigJson();
            }
            healthConfigJson.setInterval(healthInterval);
            serviceSpecJson.getTaskTemplate().getContainerSpec().setHealthConfig(healthConfigJson);
        }
        return this;
    }

    public ServiceSpecJsonHelper setResourcesCpu(Long cpu, boolean limit) {
        if (cpu != null) {
            ResourceRequirementsJson resourcesRequirements = serviceSpecJson.getTaskTemplate().getResources();
            if (resourcesRequirements == null) {
                resourcesRequirements = new ResourceRequirementsJson();
            }
            ResourcesJson resources = resourcesRequirements.getLimits();
            if (!limit) {
                resources = resourcesRequirements.getReservations();
            }
            if (resources == null) {
                resources = new ResourcesJson();
            }
            resources.setNanoCPUs(cpu);
            if (limit) {
                resourcesRequirements.setLimits(resources);
            } else {
                resourcesRequirements.setReservations(resources);
            }
            serviceSpecJson.getTaskTemplate().setResources(resourcesRequirements);
        }
        return this;
    }

    public ServiceSpecJsonHelper setResourcesMemory(Long memory, boolean limit) {
        if (memory != null) {
            ResourceRequirementsJson resourcesRequirements = serviceSpecJson.getTaskTemplate().getResources();
            if (resourcesRequirements == null) {
                resourcesRequirements = new ResourceRequirementsJson();
            }
            ResourcesJson resources = resourcesRequirements.getLimits();
            if (!limit) {
                resources = resourcesRequirements.getReservations();
            }
            if (resources == null) {
                resources = new ResourcesJson();
            }
            resources.setMemoryBytes(memory);
            if (limit) {
                resourcesRequirements.setLimits(resources);
            } else {
                resourcesRequirements.setReservations(resources);
            }
            serviceSpecJson.getTaskTemplate().setResources(resourcesRequirements);
        }
        return this;
    }

    public ServiceSpecJsonHelper setRestartCondition(String restartCondition) {
        if (restartCondition != null) {
            RestartPolicyJson restartPolicyJson = serviceSpecJson.getTaskTemplate().getRestartPolicy();
            if (restartPolicyJson == null) {
                restartPolicyJson = new RestartPolicyJson();
            }
            restartPolicyJson.setCondition(restartCondition);
            serviceSpecJson.getTaskTemplate().setRestartPolicy(restartPolicyJson);
        }
        return this;
    }

    public ServiceSpecJsonHelper setRestartDelay(String restartDelay) {
        if (restartDelay != null) {
            RestartPolicyJson restartPolicyJson = serviceSpecJson.getTaskTemplate().getRestartPolicy();
            if (restartPolicyJson == null) {
                restartPolicyJson = new RestartPolicyJson();
            }
            restartPolicyJson.setDelay(restartDelay);
            serviceSpecJson.getTaskTemplate().setRestartPolicy(restartPolicyJson);
        }
        return this;
    }

    public ServiceSpecJsonHelper setRestartMaxAttempts(Long maxAttempts) {
        if (maxAttempts != null) {
            RestartPolicyJson restartPolicyJson = serviceSpecJson.getTaskTemplate().getRestartPolicy();
            if (restartPolicyJson == null) {
                restartPolicyJson = new RestartPolicyJson();
            }
            restartPolicyJson.setMaxAttempts(maxAttempts);
            serviceSpecJson.getTaskTemplate().setRestartPolicy(restartPolicyJson);
        }
        return this;
    }

    public ServiceSpecJsonHelper setRestartWindow(String restartWindow) {
        if (restartWindow != null) {
            RestartPolicyJson restartPolicyJson = serviceSpecJson.getTaskTemplate().getRestartPolicy();
            if (restartPolicyJson == null) {
                restartPolicyJson = new RestartPolicyJson();
            }
            restartPolicyJson.setWindow(restartWindow);
            serviceSpecJson.getTaskTemplate().setRestartPolicy(restartPolicyJson);
        }
        return this;
    }

    public ServiceSpecJsonHelper setForceUpdate(Boolean forceUpdate) {
        if (forceUpdate != null && forceUpdate) {
            serviceSpecJson.getTaskTemplate().setForceUpdate(1L);
        }
        return this;
    }

    public ServiceSpecJsonHelper setConfigDelay(String updateDelay, boolean rollback) {
        if (updateDelay != null) {
            UpdateConfigJson configJson = serviceSpecJson.getUpdateConfig();
            if (rollback) {
                configJson = serviceSpecJson.getRollbackConfig();
            }
            if (configJson == null) {
                configJson = new UpdateConfigJson();
            }
            configJson.setDelay(updateDelay);
            if (rollback) {
                serviceSpecJson.setRollbackConfig(configJson);
            } else {
                serviceSpecJson.setUpdateConfig(configJson);
            }
        }
        return this;
    }

    public ServiceSpecJsonHelper setConfigFailureAction(String updateFailureAction, boolean rollback) {
        if (updateFailureAction != null) {
            UpdateConfigJson configJson = serviceSpecJson.getUpdateConfig();
            if (rollback) {
                configJson = serviceSpecJson.getRollbackConfig();
            }
            if (configJson == null) {
                configJson = new UpdateConfigJson();
            }
            configJson.setFailureAction(updateFailureAction);
            if (rollback) {
                serviceSpecJson.setRollbackConfig(configJson);
            } else {
                serviceSpecJson.setUpdateConfig(configJson);
            }
        }
        return this;
    }

    public ServiceSpecJsonHelper setConfigFailureRatio(Double updateFailureRatio, boolean rollback) {
        if (updateFailureRatio != null) {
            UpdateConfigJson configJson = serviceSpecJson.getUpdateConfig();
            if (rollback) {
                configJson = serviceSpecJson.getRollbackConfig();
            }
            if (configJson == null) {
                configJson = new UpdateConfigJson();
            }
            configJson.setMaxFailureRatio(updateFailureRatio);
            serviceSpecJson.setUpdateConfig(configJson);
        }
        return this;
    }

    public ServiceSpecJsonHelper setConfigMonitor(String updateMonitor, boolean rollback) {
        if (updateMonitor != null) {
            UpdateConfigJson configJson = serviceSpecJson.getUpdateConfig();
            if (rollback) {
                configJson = serviceSpecJson.getRollbackConfig();
            }
            if (configJson == null) {
                configJson = new UpdateConfigJson();
            }
            configJson.setMonitor(updateMonitor);
            if (rollback) {
                serviceSpecJson.setRollbackConfig(configJson);
            } else {
                serviceSpecJson.setUpdateConfig(configJson);
            }
        }
        return this;
    }

    public ServiceSpecJsonHelper setConfigOrder(String updateOrder, boolean rollback) {
        if (updateOrder != null) {
            UpdateConfigJson configJson = serviceSpecJson.getUpdateConfig();
            if (rollback) {
                configJson = serviceSpecJson.getRollbackConfig();
            }
            if (configJson == null) {
                configJson = new UpdateConfigJson();
            }
            configJson.setOrder(updateOrder);
            if (rollback) {
                serviceSpecJson.setRollbackConfig(configJson);
            } else {
                serviceSpecJson.setUpdateConfig(configJson);
            }
        }
        return this;
    }

    public ServiceSpecJsonHelper setConfigParallelism(Long updateParallelism, boolean rollback) {
        if (updateParallelism != null) {
            UpdateConfigJson configJson = serviceSpecJson.getUpdateConfig();
            if (rollback) {
                configJson = serviceSpecJson.getRollbackConfig();
            }
            if (configJson == null) {
                configJson = new UpdateConfigJson();
            }
            configJson.setParallelism(updateParallelism);
            if (rollback) {
                serviceSpecJson.setRollbackConfig(configJson);
            } else {
                serviceSpecJson.setUpdateConfig(configJson);
            }
        }
        return this;
    }

    public ServiceSpecJson getServiceSpecJson() {
        return serviceSpecJson;
    }

}

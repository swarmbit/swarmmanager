package co.uk.swarmbit.docker.cli.impl.helper;

import co.uk.swarmbit.docker.api.common.json.ServiceSpecJson;
import co.uk.swarmbit.docker.api.common.json.inner.*;
import co.uk.swarmbit.docker.cli.model.*;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceSpecJsonConverter {

    private ServiceSpecJson serviceSpecJson;

    public ServiceSpecJsonConverter(ServiceSpecJson serviceSpecJson, Service service, boolean create) {
        this.serviceSpecJson = serviceSpecJson;
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
        if (create) {
            setName(service.getName());
            setMode(service.isGlobal());
        }
        setReplicas(service.getReplicas())
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
    }

    public ServiceSpecJson getServiceSpecJson() {
        return serviceSpecJson;
    }

    private ServiceSpecJsonConverter setName(String name) {
        serviceSpecJson.setName(name);
        return this;
    }

    private ServiceSpecJsonConverter setMode(Boolean isGlobal) {
        if (serviceSpecJson.getMode() == null) {
            ServiceModeJson serviceModeJson = new ServiceModeJson();
            if (isGlobal != null && isGlobal) {
                GlobalServiceJson globalServiceJson = new GlobalServiceJson();
                serviceModeJson.setGlobal(globalServiceJson);
            } else {
                ReplicatedServiceJson replicatedServiceJson = new ReplicatedServiceJson();
                replicatedServiceJson.setReplicas(1L);
                serviceModeJson.setReplicated(replicatedServiceJson);
            }
            serviceSpecJson.setMode(serviceModeJson);
        }
        return this;
    }

    private ServiceSpecJsonConverter setReplicas(Long numberOfReplicas) {
        if (serviceSpecJson.getMode().getReplicated() != null && numberOfReplicas != null) {
            ReplicatedServiceJson replicatedServiceJson = new ReplicatedServiceJson();
            replicatedServiceJson.setReplicas(numberOfReplicas);
            ServiceModeJson serviceModeJson = new ServiceModeJson();
            serviceModeJson.setReplicated(replicatedServiceJson);
            serviceSpecJson.setMode(serviceModeJson);
        }
        return this;
    }

    private ServiceSpecJsonConverter setPorts(List<Port> ports) {
        if (ports != null) {
            EndpointSpecJson endpointSpecJson = serviceSpecJson.getEndpointSpec();
            if (endpointSpecJson == null) {
                endpointSpecJson  = new EndpointSpecJson();
            }
            PortConfigJson[] portConfigs = new PortConfigJson[ports.size()];
            for (int i = 0; i < portConfigs.length; i++) {
                Port port = ports.get(i);
                if (port != null) {
                    PortConfigJson portConfig = new PortConfigJson();
                    Port.Protocol protocol = Port.Protocol.getProtocol(port.getProtocol());
                    if (protocol != null) {
                        portConfig.setProtocol(protocol.toString().toLowerCase());
                    }
                    portConfig.setPublishedPort(port.getPublished());
                    portConfig.setTargetPort(port.getTarget());
                    portConfigs[i] = portConfig;
                }
            }
            endpointSpecJson.setPorts(portConfigs);
            serviceSpecJson.setEndpointSpec(endpointSpecJson);
        }
        return this;
    }

    private ServiceSpecJsonConverter setEnvs(List<String> env) {
        if (env != null) {
            TaskSpecJson taskSpecJson = serviceSpecJson.getTaskTemplate();
            if (taskSpecJson == null) {
                taskSpecJson = new TaskSpecJson();
            }

            ContainerSpecJson containerSpecJson = taskSpecJson.getContainerSpec();
            if (containerSpecJson == null) {
                containerSpecJson = new ContainerSpecJson();
            }
            containerSpecJson.setEnv(env.toArray(new String[]{}));
        }
        return this;
    }

    private ServiceSpecJsonConverter setImage(String image) {
        if (image != null) {
            TaskSpecJson taskSpecJson = serviceSpecJson.getTaskTemplate();
            ContainerSpecJson containerSpecJson = taskSpecJson.getContainerSpec();
            containerSpecJson.setImage(image);
            taskSpecJson.setContainerSpec(containerSpecJson);
            serviceSpecJson.setTaskTemplate(taskSpecJson);
        }
        return this;
    }

    private ServiceSpecJsonConverter setConfigs(List<ServiceSecretAndConfig> configs) {
        if (configs != null) {
            List<ConfigReferenceJson> configsJson = new ArrayList<>();
            for (ServiceSecretAndConfig config : configs) {
                ConfigReferenceJson configReferenceJson = new ConfigReferenceJson();
                configReferenceJson.setConfigID(config.getId());
                configReferenceJson.setConfigName(config.getName());
                SecretAndConfigReferenceFileTargetJson file = new SecretAndConfigReferenceFileTargetJson();
                if (StringUtils.isNotEmpty(config.getFileName())) {
                    file.setName(config.getFileName());
                } else {
                    file.setName(config.getName());
                }

                // UID:  "0",
                // GID:  "0",
                // Mode: 0444,
                file.setGid(StringUtils.isNotEmpty(config.getFileGID()) ? config.getFileGID() :  "0");
                file.setUid(StringUtils.isNotEmpty(config.getFileUID()) ? config.getFileUID() :  "0");
                file.setMode((config.getFileMode() != null && config.getFileMode() > 0) ? config.getFileMode() : new Integer(0444));
                configReferenceJson.setFile(file);
                configsJson.add(configReferenceJson);
            }
            serviceSpecJson.getTaskTemplate().getContainerSpec().setConfigs(configsJson.toArray(new ConfigReferenceJson[0]));
        }
        return this;
    }

    private ServiceSpecJsonConverter setSecrets(List<ServiceSecretAndConfig> secrets) {
        if (secrets != null) {
            List<SecretReferenceJson> secretsJson = new ArrayList<>();
            for (ServiceSecretAndConfig secret : secrets) {
                SecretReferenceJson secretReferenceJson = new SecretReferenceJson();
                secretReferenceJson.setSecretID(secret.getId());
                secretReferenceJson.setSecretName(secret.getName());
                SecretAndConfigReferenceFileTargetJson file = new SecretAndConfigReferenceFileTargetJson();
                if (StringUtils.isNotEmpty(secret.getFileName())) {
                    file.setName(secret.getFileName());
                } else {
                    file.setName(secret.getName());
                }

                // UID:  "0",
                // GID:  "0",
                // Mode: 0444,
                file.setGid(StringUtils.isNotEmpty(secret.getFileGID()) ? secret.getFileGID() :  "0");
                file.setUid(StringUtils.isNotEmpty(secret.getFileUID()) ? secret.getFileUID() :  "0");
                file.setMode((secret.getFileMode() != null && secret.getFileMode() > 0) ? secret.getFileMode() : new Integer(0444));
                secretReferenceJson.setFile(file);
                secretsJson.add(secretReferenceJson);
            }
            serviceSpecJson.getTaskTemplate().getContainerSpec().setSecrets(secretsJson.toArray(new SecretReferenceJson[0]));
        }
        return this;
    }

    private ServiceSpecJsonConverter setLabels(Map<String, String> labels) {
        if (labels != null) {
            serviceSpecJson.setLabels(labels);
        }
        return this;
    }

    private ServiceSpecJsonConverter setContainerLabels(Map<String, String> containerLabels) {
        if (containerLabels != null) {
            serviceSpecJson.getTaskTemplate().getContainerSpec().setLabels(containerLabels);
        }
        return this;
    }

    private ServiceSpecJsonConverter setConstraints(List<Constraint> constraints) {
        if (constraints != null) {
            List<String> constraintsString = new ArrayList<>();
            PlacementJson placementJson = serviceSpecJson.getTaskTemplate().getPlacement();
            if (placementJson == null) {
                placementJson = new PlacementJson();
            }
            for (Constraint constraint : constraints) {
                constraintsString.add(constraint.getName() + (constraint.isDifferent() ? " != " : " == ") + constraint.getValue());
            }
            placementJson.setConstraints(constraintsString.toArray(new String[0]));
            serviceSpecJson.getTaskTemplate().setPlacement(placementJson);
        }
        return this;
    }

    private ServiceSpecJsonConverter setPlacementPreferences(Map<String, String> placementPreferences) {
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

    private ServiceSpecJsonConverter setReadOnly(Boolean readOnly) {
        if (readOnly != null) {
            serviceSpecJson.getTaskTemplate().getContainerSpec().setReadOnly(readOnly);
        }
        return this;
    }

    private ServiceSpecJsonConverter setEntrypoint(String entrypoint) {
        if (entrypoint != null) {
            serviceSpecJson.getTaskTemplate().getContainerSpec().setCommand(new String[] { entrypoint });
        }
        return this;
    }

    private ServiceSpecJsonConverter setArgs(List<String> args) {
        if (args != null) {
            serviceSpecJson.getTaskTemplate().getContainerSpec().setArgs(args.toArray(new String[0]));
        }
        return this;
    }

    private ServiceSpecJsonConverter setGroups(List<String> groups) {
        if (groups != null) {
            serviceSpecJson.getTaskTemplate().getContainerSpec().setGroups(groups.toArray(new String[0]));
        }
        return this;
    }

    private ServiceSpecJsonConverter setLogDriver(String logDriver) {
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

    private ServiceSpecJsonConverter setLogOptions(Map<String, String> logOptions) {
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

    private ServiceSpecJsonConverter setUser(String user) {
        if (user != null) {
            serviceSpecJson.getTaskTemplate().getContainerSpec().setUser(user);
        }
        return this;
    }

    private ServiceSpecJsonConverter setWorkDir(String workDir) {
        if (workDir != null) {
            serviceSpecJson.getTaskTemplate().getContainerSpec().setDir(workDir);
        }
        return this;
    }

    private ServiceSpecJsonConverter setStopGracePeriod(Long stopGracePeriod) {
        if (stopGracePeriod != null) {
            serviceSpecJson.getTaskTemplate().getContainerSpec().setStopGracePeriod(stopGracePeriod);
        }
        return this;
    }

    private ServiceSpecJsonConverter setStopSignal(String stopSignal) {
        if (stopSignal != null) {
            serviceSpecJson.getTaskTemplate().getContainerSpec().setStopSignal(stopSignal);
        }
        return this;
    }

    private ServiceSpecJsonConverter setMounts(List<Mount> mounts) {
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
                    if (mount.getTmpfsOptions().getSize() != null) {
                        tmpfsOptionsJson.setSizeBytes(mount.getTmpfsOptions().getSize());
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

    private ServiceSpecJsonConverter setEndpointMode(String endpointMode) {
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

    private ServiceSpecJsonConverter setNetworks(List<String> networks) {
        if (networks != null) {
            NetworkAttachmentConfigJson[] networkAttachmentConfigJsons = new NetworkAttachmentConfigJson[networks.size()];
            for (int i = 0; i < networkAttachmentConfigJsons.length; i++) {
                String network = networks.get(i);
                NetworkAttachmentConfigJson networkAttachmentConfigJson = new NetworkAttachmentConfigJson();
                networkAttachmentConfigJson.setTarget(network);
                networkAttachmentConfigJsons[i] = networkAttachmentConfigJson;
            }
            TaskSpecJson taskSpecJson = serviceSpecJson.getTaskTemplate();
            if (taskSpecJson == null) {
                taskSpecJson  = new TaskSpecJson();
            }
            taskSpecJson.setNetworks(networkAttachmentConfigJsons);
        }
        return this;
    }

    private ServiceSpecJsonConverter setHostname(String hostname) {
        if (hostname != null) {
            serviceSpecJson.getTaskTemplate().getContainerSpec().setHostname(hostname);
        }
        return this;
    }

    private ServiceSpecJsonConverter setHosts(List<String> hosts) {
        if (hosts != null) {
            serviceSpecJson.getTaskTemplate().getContainerSpec().setHosts(hosts.toArray(new String[0]));
        }
        return this;
    }

    private ServiceSpecJsonConverter setDnsServers(List<String> dnsServers) {
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

    private ServiceSpecJsonConverter setDnsOptions(List<String> dnsOptions) {
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

    private ServiceSpecJsonConverter setDnsSearches(List<String> dnsSearches) {
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

    private ServiceSpecJsonConverter setHealthCmd(String healthCmd) {
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

    private ServiceSpecJsonConverter setNoHealthCheck(Boolean noHealthCheck) {
        if (noHealthCheck != null) {
            HealthConfigJson healthConfigJson = serviceSpecJson.getTaskTemplate().getContainerSpec().getHealthConfig();
            if (healthConfigJson == null) {
                healthConfigJson = new HealthConfigJson();
            }
            if (noHealthCheck) {
                healthConfigJson.setTest(new String[] { "NONE" });
            } else {
                String[] test = healthConfigJson.getTest();
                if (test != null && test.length == 1 && "NONE".equals(test[0])) {
                    healthConfigJson.setTest(new String[]{});
                }
            }
            serviceSpecJson.getTaskTemplate().getContainerSpec().setHealthConfig(healthConfigJson);
        }
        return this;
    }

    private ServiceSpecJsonConverter setHealthRetries(Integer healthRetries) {
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

    private ServiceSpecJsonConverter setHealthStartPeriod(Long healthStartPeriod) {
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

    private ServiceSpecJsonConverter setHealthTimeout(Long healthTimeout) {
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

    private ServiceSpecJsonConverter setHealthInterval(Long healthInterval) {
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

    private ServiceSpecJsonConverter setResourcesCpu(Long cpu, boolean limit) {
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

    private ServiceSpecJsonConverter setResourcesMemory(Long memory, boolean limit) {
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

    private ServiceSpecJsonConverter setRestartCondition(String restartCondition) {
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

    private ServiceSpecJsonConverter setRestartDelay(Long restartDelay) {
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

    private ServiceSpecJsonConverter setRestartMaxAttempts(Long maxAttempts) {
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

    private ServiceSpecJsonConverter setRestartWindow(Long restartWindow) {
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

    private ServiceSpecJsonConverter setForceUpdate(Boolean forceUpdate) {
        if (forceUpdate != null && forceUpdate) {
            serviceSpecJson.getTaskTemplate().setForceUpdate(System.currentTimeMillis());
        }
        return this;
    }

    private ServiceSpecJsonConverter setConfigDelay(Long updateDelay, boolean rollback) {
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

    private ServiceSpecJsonConverter setConfigFailureAction(String updateFailureAction, boolean rollback) {
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

    private ServiceSpecJsonConverter setConfigFailureRatio(Double updateFailureRatio, boolean rollback) {
        if (updateFailureRatio != null) {
            UpdateConfigJson configJson = serviceSpecJson.getUpdateConfig();
            if (rollback) {
                configJson = serviceSpecJson.getRollbackConfig();
            }
            if (configJson == null) {
                configJson = new UpdateConfigJson();
            }
            configJson.setMaxFailureRatio(updateFailureRatio);
            if (rollback) {
                serviceSpecJson.setRollbackConfig(configJson);
            } else {
                serviceSpecJson.setUpdateConfig(configJson);
            }
        }
        return this;
    }

    private ServiceSpecJsonConverter setConfigMonitor(Long updateMonitor, boolean rollback) {
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

    private ServiceSpecJsonConverter setConfigOrder(String updateOrder, boolean rollback) {
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

    private ServiceSpecJsonConverter setConfigParallelism(Long updateParallelism, boolean rollback) {
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

}

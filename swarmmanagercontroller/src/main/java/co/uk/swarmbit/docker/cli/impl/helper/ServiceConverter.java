package co.uk.swarmbit.docker.cli.impl.helper;

import co.uk.swarmbit.docker.api.common.json.ServiceJson;
import co.uk.swarmbit.docker.api.common.json.inner.*;
import co.uk.swarmbit.docker.cli.model.*;
import co.uk.swarmbit.docker.api.common.util.DockerDateFormatter;
import org.apache.commons.lang3.StringUtils;

import java.time.ZonedDateTime;
import java.util.*;

public class ServiceConverter {

    private Service service;

    public ServiceConverter(ServiceJson serviceJson) {
        service = new Service();
        service.setId(serviceJson.getId());
        service.setName(serviceJson.getSpec().getName());
        service.setLabels(serviceJson.getSpec().getLabels());
        setCreatedAt(serviceJson.getCreatedAt());
        setUpdatedAt(serviceJson.getUpdatedAt());
        setTask(serviceJson.getSpec().getTaskTemplate());

        // if there are no networks in task template set service spec networks
        if (service.getNetworks() == null) {
            setNetworks(serviceJson.getSpec().getNetworks());
        }

        setMode(serviceJson.getSpec().getMode());
        setEndpoint(serviceJson.getSpec().getEndpointSpec());
        setUpdateConfig(serviceJson.getSpec().getUpdateConfig());
        setRollbackConfig(serviceJson.getSpec().getRollbackConfig());
    }

    public Service getService() {
        return service;
    }

    private void setCreatedAt(String dateTime) {
        ZonedDateTime createdAt = DockerDateFormatter.fromDateStringToZonedDateTime(dateTime);
        if (createdAt != null) {
            service.setCreatedAt(createdAt.toInstant().toEpochMilli());
        }
    }

    private void setUpdatedAt(String dateTime) {
        ZonedDateTime updatedAt = DockerDateFormatter.fromDateStringToZonedDateTime(dateTime);
        if (updatedAt != null) {
            service.setUpdatedAt(updatedAt.toInstant().toEpochMilli());
        }
    }

    private void setTask(TaskSpecJson taskSpecJson) {
        if (taskSpecJson != null) {
            setContainer(taskSpecJson.getContainerSpec());
            setPlacement(taskSpecJson.getPlacement());
            setDriver(taskSpecJson.getLogDriver());
            setNetworks(taskSpecJson.getNetworks());
            setRestartPolicy(taskSpecJson.getRestartPolicy());
            setResourceRequirements(taskSpecJson.getResources());
        }
    }

    private void setMode(ServiceModeJson modeJson) {
        if (modeJson != null) {
            service.setGlobal(modeJson.getGlobal() != null);
            if (!service.isGlobal()) {
                ReplicatedServiceJson replicatedServiceJson = modeJson.getReplicated();
                if (replicatedServiceJson != null) {
                    service.setReplicas(replicatedServiceJson.getReplicas());

                }
            }
        }
    }

    private void setEndpoint(EndpointSpecJson endpointSpecJson) {
        if (endpointSpecJson != null) {
            List<Port> ports = getPorts(endpointSpecJson);
            if (!ports.isEmpty()) {
                service.setPorts(ports);
            }
            String mode = endpointSpecJson.getMode();
            if (mode != null) {
                service.setEndpointMode(mode);
            }
        }
    }

    private void setUpdateConfig(UpdateConfigJson updateConfigJson) {
        if (updateConfigJson != null) {
            service.setUpdateDelay(updateConfigJson.getDelay());
            service.setUpdateFailureAction(updateConfigJson.getFailureAction());
            service.setUpdateMaxFailureRatio(updateConfigJson.getMaxFailureRatio());
            service.setUpdateMonitor(updateConfigJson.getMonitor());
            service.setUpdateOrder(updateConfigJson.getOrder());
            service.setUpdateParallelism(updateConfigJson.getParallelism());
        }
    }

    private void setRollbackConfig(UpdateConfigJson rollbackConfigJson) {
        if (rollbackConfigJson != null) {
            service.setRollbackDelay(rollbackConfigJson.getDelay());
            service.setRollbackFailureAction(rollbackConfigJson.getFailureAction());
            service.setRollbackMaxFailureRatio(rollbackConfigJson.getMaxFailureRatio());
            service.setRollbackMonitor(rollbackConfigJson.getMonitor());
            service.setRollbackOrder(rollbackConfigJson.getOrder());
            service.setRollbackParallelism(rollbackConfigJson.getParallelism());
        }
    }

    private void setContainer(ContainerSpecJson containerSpecJson) {
        if (containerSpecJson != null) {
            service.setImage(containerSpecJson.getImage());

            Boolean readOnly = containerSpecJson.getReadOnly();
            if (readOnly != null) {
                service.setReadOnly(readOnly);
            }

            String[] env = containerSpecJson.getEnv();
            if (env != null) {
                service.setEnv(Arrays.asList(env));
            }

            String[] args = containerSpecJson.getArgs();
            if (args != null) {
                service.setArgs(Arrays.asList(args));
            }

            Map<String, String> containerLabels = containerSpecJson.getLabels();
            if (containerLabels != null) {
                service.setContainerLabels(containerLabels);
            }

            String[] groups = containerSpecJson.getGroups();
            if (groups != null) {
                service.setGroups(Arrays.asList(groups));
            }

            String user = containerSpecJson.getUser();
            if (user != null) {
                service.setUser(user);
            }

            String workDir = containerSpecJson.getDir();
            if (workDir != null) {
                service.setWorkDir(workDir);
            }

            String hostname = containerSpecJson.getHostname();
            if (hostname != null) {
                service.setHostname(hostname);
            }

            String[] hosts = containerSpecJson.getHosts();
            if (hosts != null) {
                service.setHosts(Arrays.asList(hosts));
            }

            Long stopGracePeriod = containerSpecJson.getStopGracePeriod();
            if (stopGracePeriod != null) {
                service.setStopGracePeriod(stopGracePeriod);
            }

            String stopSignal = containerSpecJson.getStopSignal();
            if (stopSignal != null) {
                service.setStopSignal(stopSignal);
            }
            setCmd(containerSpecJson.getCommand());
            setConfigs(containerSpecJson.getConfigs());
            setSecrets(containerSpecJson.getSecrets());
            setHealthCheck(containerSpecJson.getHealthConfig());
            setDnsConfig(containerSpecJson.getDnsConfig());
            setMounts(readOnly, containerSpecJson.getMounts());
        }
    }

    private void setCmd(String[] cmd) {
        if (cmd != null) {
            if (cmd.length > 0) {
                service.setEntrypoint(cmd[0]);
            }
        }
    }

    private void setConfigs(ConfigReferenceJson[] configReferenceJsons) {
        if (configReferenceJsons != null) {
            List<ServiceSecretAndConfig> configs = new ArrayList<>();
            for (ConfigReferenceJson configReferenceJson : configReferenceJsons) {
                ServiceSecretAndConfig config = new ServiceSecretAndConfig();
                config.setName(configReferenceJson.getConfigName());
                config.setId(configReferenceJson.getConfigID());
                SecretAndConfigReferenceFileTargetJson file = configReferenceJson.getFile();
                setFileProperties(config, file);
                configs.add(config);
            }
            service.setConfigs(configs);
        }
    }

    private void setSecrets(SecretReferenceJson[] secretReferenceJsons) {
        if (secretReferenceJsons != null) {
            List<ServiceSecretAndConfig> secrets = new ArrayList<>();
            for (SecretReferenceJson secretReferenceJson : secretReferenceJsons) {
                ServiceSecretAndConfig secret = new ServiceSecretAndConfig();
                secret.setName(secretReferenceJson.getSecretName());
                secret.setId(secretReferenceJson.getSecretID());
                SecretAndConfigReferenceFileTargetJson file = secretReferenceJson.getFile();
                setFileProperties(secret, file);
                secrets.add(secret);
            }
            service.setSecrets(secrets);
        }
    }

    private void setHealthCheck(HealthConfigJson healthConfigJson) {
        if (healthConfigJson != null) {
            Long startPeriod = healthConfigJson.getStartPeriod();
            if (startPeriod != null) {
                service.setHealthStartPeriod(startPeriod);
            }

            String[] test = healthConfigJson.getTest();
            if (test != null) {
                if (test.length > 0) {
                    if (StringUtils.equals(test[0], "NONE")) {
                        service.setNoHealthCheck(true);
                    } else if (test.length > 1) {
                        service.setHealthCmd(test[1]);
                    }
                }
            }

            Long healthInterval = healthConfigJson.getInterval();
            if (healthInterval != null) {
                service.setHealthInterval(healthInterval);
            }

            Long healthTimeout = healthConfigJson.getTimeout();
            if (healthTimeout != null) {
                service.setHealthTimeout(healthTimeout);
            }

            Integer healthRetries = healthConfigJson.getRetries();
            if (healthRetries != null) {
                service.setHealthRetries(healthRetries);
            }
        }
    }

    private void setDnsConfig(DNSConfigJson dnsConfigJson) {
        if (dnsConfigJson != null) {
            String[] nameServers = dnsConfigJson.getNameServers();
            if (nameServers != null) {
                service.setDnsServers(Arrays.asList(nameServers));
            }

            String[] options = dnsConfigJson.getOptions();
            if (options != null) {
                service.setDnsOptions(Arrays.asList(options));
            }

            String[] dnsSearches = dnsConfigJson.getSearch();
            if (dnsSearches != null) {
                service.setDnsSearches(Arrays.asList(dnsSearches));
            }

        }
    }

    private void setMounts(Boolean readOnly, MountJson[] mountJsons) {
        if (mountJsons != null) {
            List<Mount> mounts = new ArrayList<>();
            for (MountJson mountJson : mountJsons) {
                Mount mount = new Mount();
                Boolean readOnlyMount = mountJson.isReadOnly();
                if (readOnlyMount != null && readOnlyMount) {
                    mount.setReadOnly(readOnly);
                }
                mount.setType(mountJson.getType());
                mount.setConsistency(mountJson.getConsistency());
                mount.setSource(mountJson.getSource());
                mount.setDestination(mountJson.getTarget());

                if (StringUtils.equals(Mount.BIND_TYPE, mount.getType())) {
                    BindOptionsJson bindOptionsJson = mountJson.getBindOptions();
                    if (bindOptionsJson != null) {
                        BindMountOptions bindMountOptions = new BindMountOptions();
                        bindMountOptions.setPropagation(bindOptionsJson.getPropagation());
                        mount.setBindOptions(bindMountOptions);
                    }
                } else if (StringUtils.equals(Mount.VOLUME_TYPE, mount.getType())) {
                    VolumeOptionsJson volumeOptionsJson = mountJson.getVolumeOptions();
                    if (volumeOptionsJson != null) {
                        VolumeMountOptions volumeMountOptions = new VolumeMountOptions();
                        DriverJson driverJson = volumeOptionsJson.getDriver();
                        if (driverJson != null) {
                            volumeMountOptions.setDriver(driverJson.getName());
                            volumeMountOptions.setDriverOptions(driverJson.getOptions());
                        }
                        volumeMountOptions.setNoCopy(volumeOptionsJson.isNoCopy());
                        volumeMountOptions.setLabels(volumeOptionsJson.getLabels());
                        mount.setVolumeOptions(volumeMountOptions);
                    }
                } else if (StringUtils.equals(Mount.TMPFS_TYPE, mount.getType())) {
                    TmpfsOptionsJson tmpfsOptionsJson = mountJson.getTmpfsOptions();
                    if (tmpfsOptionsJson != null) {
                        TmpfsMountOptions tmpfsMountOptions = new TmpfsMountOptions();
                        tmpfsMountOptions.setMode(tmpfsOptionsJson.getMode());
                        tmpfsMountOptions.setSize(tmpfsOptionsJson.getSizeBytes());
                        mount.setTmpfsMountOptions(tmpfsMountOptions);
                    }
                }
                mounts.add(mount);
            }
            service.setMounts(mounts);
        }
    }

    private void setPlacement(PlacementJson placementJson) {
        if (placementJson != null) {
            String[] constraints = placementJson.getConstraints();
            if (constraints != null) {
                List<Constraint> constraintsList = new ArrayList<>();
                for (String constraint : constraints) {
                    if (constraint != null) {
                        boolean isDifferent;
                        String[] values;
                        String different = "!=";
                        String equals = "==";
                        if (StringUtils.contains(constraint, different)) {
                            isDifferent = true;
                            values = constraint.split(different);
                        } else {
                            isDifferent = false;
                            values = constraint.split(equals);
                        }
                        if (values.length == 2) {
                            constraintsList.add(new Constraint(StringUtils.trim(values[0]), StringUtils.trim(values[0]), isDifferent));
                        }
                    }
                }
                service.setConstraints(constraintsList);
            }
            PlacementPreferenceJson[] placementPreferences = placementJson.getPreferences();
            if (placementPreferences != null) {
                Map<String, String> placementPreferencesMap = new HashMap<>();
                for (PlacementPreferenceJson placementPreference : placementPreferences) {
                    placementPreferencesMap.put("spread", placementPreference.getSpread().getSpreadDescriptor());
                }
                service.setPlacementPreferences(placementPreferencesMap);
            }
        }
    }

    private void setDriver(DriverJson driverJson) {
        if (driverJson != null) {
            String name = driverJson.getName();
            if (name != null) {
                service.setLogDriver(name);
            }
            Map<String, String> options = driverJson.getOptions();
            if (options != null) {
                service.setLogOptions(options);
            }
        }
    }

    private void setNetworks(NetworkAttachmentConfigJson[] networkAttachmentConfigJsons) {
        if (networkAttachmentConfigJsons != null) {
            List<String> networks = getNetworks(networkAttachmentConfigJsons);
            service.setNetworks(networks);
        }
    }

    private void setRestartPolicy(RestartPolicyJson restartPolicyJson) {
        if (restartPolicyJson != null) {
            service.setRestartCondition(restartPolicyJson.getCondition());
            service.setRestartDelay(restartPolicyJson.getDelay());
            service.setRestartMaxAttempts(restartPolicyJson.getMaxAttempts());
            service.setRestartWindow(restartPolicyJson.getWindow());
        }
    }

    private void setResourceRequirements(ResourceRequirementsJson resourceRequirementsJson) {
        if (resourceRequirementsJson != null) {
            ResourcesJson resourcesLimitsJson = resourceRequirementsJson.getLimits();
            if (resourcesLimitsJson != null) {
                service.setLimitCpu(resourcesLimitsJson.getNanoCPUs());
                service.setLimitMemory(resourcesLimitsJson.getMemoryBytes());
            }
            ResourcesJson resourcesReservationsJson = resourceRequirementsJson.getReservations();
            if (resourcesReservationsJson != null) {
                service.setReserveCpu(resourcesReservationsJson.getNanoCPUs());
                service.setReserveMemory(resourcesReservationsJson.getMemoryBytes());
            }
        }
    }

    private void setFileProperties(ServiceSecretAndConfig config, SecretAndConfigReferenceFileTargetJson file) {
        if (file != null) {
            config.setFileGID(file.getGid());
            config.setFileMode(file.getMode());
            config.setFileUID(file.getUid());
            config.setFileName(file.getName());
        }
    }

    private List<String> getNetworks(NetworkAttachmentConfigJson[] networkAttachmentConfigJsons) {
        List<String> networks = new ArrayList<>();
        for (NetworkAttachmentConfigJson networkAttachmentConfigJson : networkAttachmentConfigJsons) {
            networks.add(networkAttachmentConfigJson.getTarget());
        }
        return networks;
    }

    public static List<Port> getPorts(EndpointSpecJson endpointSpecJson) {
        List<Port> ports = new ArrayList<>();
        if (endpointSpecJson != null) {
            PortConfigJson[] portConfigs = endpointSpecJson.getPorts();
            if (portConfigs != null) {
                for (PortConfigJson portConfig : portConfigs) {
                    if (portConfig != null) {
                        Port port = new Port();
                        port.setProtocol(Port.Protocol.getProtocol(portConfig.getProtocol()));
                        port.setPublished(portConfig.getPublishedPort());
                        port.setTarget(portConfig.getTargetPort());
                        ports.add(port);
                    }
                }
            }
        }
        return ports;
    }

}

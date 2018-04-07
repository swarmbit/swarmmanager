package co.uk.swarmbit.docker.cli.impl.helper;

import co.uk.swarmbit.docker.api.common.json.ServiceJson;
import co.uk.swarmbit.docker.api.common.json.inner.*;
import co.uk.swarmbit.docker.cli.model.*;
import co.uk.swarmbit.docker.api.common.util.DockerDateFormatter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;
import java.util.*;

public class ServiceConverter {

    private final Logger LOGGER = LoggerFactory.getLogger(ServiceConverter.class.getName());

    private Service service;

    private ServiceJson serviceJson;

    private ServiceConverter() {
    }

    public static ServiceConverter newServiceConverter(ServiceJson serviceJson) {
        ServiceConverter builder = new ServiceConverter();
        builder.serviceJson = serviceJson;
        builder.service = new Service();
        return builder;
    }

    public Service getService() {
        service.setId(serviceJson.getId());
        ZonedDateTime createdAt = DockerDateFormatter.fromDateStringToZonedDateTime(serviceJson.getCreatedAt());
        ZonedDateTime updatedAt = DockerDateFormatter.fromDateStringToZonedDateTime(serviceJson.getUpdatedAt());
        service.setCreatedAt(createdAt.toInstant().toEpochMilli());
        service.setUpdatedAt(updatedAt.toInstant().toEpochMilli());
        service.setName(serviceJson.getSpec().getName());

        if (serviceJson.getSpec().getLabels() != null) {
            service.setLabels(serviceJson.getSpec().getLabels());
        }

        ServiceModeJson modeJson = serviceJson.getSpec().getMode();
        if (modeJson != null) {
            service.setGlobal(serviceJson.getSpec().getMode().getGlobal() != null);
            if (!service.isGlobal()) {
                ReplicatedServiceJson replicatedServiceJson = serviceJson.getSpec().getMode().getReplicated();
                if (replicatedServiceJson != null) {
                    service.setReplicas(replicatedServiceJson.getReplicas());

                }
            }
        }

        EndpointSpecJson endpointSpecJson = serviceJson.getSpec().getEndpointSpec();
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

        TaskSpecJson taskSpecJson = serviceJson.getSpec().getTaskTemplate();
        if (taskSpecJson != null) {
            service.setImage(taskSpecJson.getContainerSpec().getImage());
            ContainerSpecJson containerSpecJson = taskSpecJson.getContainerSpec();
            if (containerSpecJson != null) {
                Boolean readOnly = containerSpecJson.getReadOnly();
                if (readOnly != null) {
                    service.setReadOnly(readOnly);
                }
                ConfigReferenceJson[] configReferenceJsons = containerSpecJson.getConfigs();
                if (configReferenceJsons != null) {
                    List<String> configs = new ArrayList<>();
                    for (ConfigReferenceJson configReferenceJson : configReferenceJsons) {
                        configs.add(configReferenceJson.getConfigName());
                    }
                    service.setConfigs(configs);
                }
                SecretReferenceJson[] secretReferenceJsons = containerSpecJson.getSecrets();
                if (secretReferenceJsons != null) {
                    List<String> secrets = new ArrayList<>();
                    for (SecretReferenceJson secretReferenceJson : secretReferenceJsons) {
                        secrets.add(secretReferenceJson.getSecretName());
                    }
                    service.setSecrets(secrets);
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

                HealthConfigJson healthConfigJson = containerSpecJson.getHealthConfig();
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

                DNSConfigJson dnsConfigJson = containerSpecJson.getDnsConfig();
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

                    Long stopGracePeriod = containerSpecJson.getStopGracePeriod();
                    if (stopGracePeriod != null) {
                        service.setStopGracePeriod(stopGracePeriod);
                    }

                    String stopSignal = containerSpecJson.getStopSignal();
                    if (stopSignal != null) {
                        service.setStopSignal(stopSignal);
                    }

                    String[] cmd = containerSpecJson.getCommand();
                    if (cmd != null) {
                        if (cmd.length > 0) {
                            service.setEntrypoint(cmd[0]);
                        }
                    }

                    MountJson[] mountJsons = containerSpecJson.getMounts();
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
            }

            PlacementJson placementJson = taskSpecJson.getPlacement();
            if (placementJson != null) {
                String[] constraints = placementJson.getConstraints();
                if (constraints != null) {
                    Map<String, String> constraintsMap = new HashMap<>();
                    for (String constraint : constraints) {
                        if (constraint != null) {
                            String[] constraintSplit = constraint.split("=");
                            if (constraintSplit.length == 2) {
                                constraintsMap.put(constraintSplit[0], constraintSplit[1]);
                            }
                        }
                    }
                    service.setConstraints(constraintsMap);
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

            DriverJson driverJson = taskSpecJson.getLogDriver();
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

            NetworkAttachmentConfigJson[] networkAttachmentConfigJsons = taskSpecJson.getNetworks();
            if (networkAttachmentConfigJsons != null) {
                List<String> networks = getNetworks(networkAttachmentConfigJsons);
                service.setNetworks(networks);
            } else {
                networkAttachmentConfigJsons = serviceJson.getSpec().getNetworks();
                if (networkAttachmentConfigJsons != null) {
                    List<String> networks = getNetworks(networkAttachmentConfigJsons);
                    service.setNetworks(networks);
                }
            }

            RestartPolicyJson restartPolicyJson = taskSpecJson.getRestartPolicy();
            if (restartPolicyJson != null) {
                service.setRestartCondition(restartPolicyJson.getCondition());
                service.setRestartDelay(restartPolicyJson.getDelay());
                service.setRestartMaxAttempts(restartPolicyJson.getMaxAttempts());
                service.setRestartWindow(restartPolicyJson.getWindow());
            }

            ResourceRequirementsJson resourceRequirementsJson = taskSpecJson.getResources();
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

            UpdateConfigJson updateConfigJson = serviceJson.getSpec().getUpdateConfig();
            if (updateConfigJson != null) {
                service.setUpdateDelay(updateConfigJson.getDelay());
                service.setUpdateFailureAction(updateConfigJson.getFailureAction());
                service.setUpdateMaxFailureRatio(updateConfigJson.getMaxFailureRatio());
                service.setUpdateMonitor(updateConfigJson.getMonitor());
                service.setUpdateOrder(updateConfigJson.getOrder());
                service.setUpdateParallelism(updateConfigJson.getParallelism());
            }

            UpdateConfigJson rollbackConfigJson = serviceJson.getSpec().getRollbackConfig();
            if (rollbackConfigJson != null) {
                service.setRollbackDelay(rollbackConfigJson.getDelay());
                service.setRollbackFailureAction(rollbackConfigJson.getFailureAction());
                service.setRollbackMaxFailureRatio(rollbackConfigJson.getMaxFailureRatio());
                service.setRollbackMonitor(rollbackConfigJson.getMonitor());
                service.setRollbackOrder(rollbackConfigJson.getOrder());
                service.setRollbackParallelism(rollbackConfigJson.getParallelism());
            }
        }

        return service;
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
                        System.out.println(portConfig.getProtocol());
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

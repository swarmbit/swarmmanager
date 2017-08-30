package com.swarmmanager.docker.cli.helper;

import com.swarmmanager.docker.api.common.json.ServiceSpecJson;
import com.swarmmanager.docker.api.common.json.inner.*;
import com.swarmmanager.docker.cli.model.Port;

import java.util.List;

public class ServiceSpecJsonHelper {

    private ServiceSpecJson serviceSpecJson;

    private ServiceSpecJsonHelper() {
    }

    public static ServiceSpecJsonHelper createNewHelper(ServiceSpecJson serviceSpecJson) {
        ServiceSpecJsonHelper builder = new ServiceSpecJsonHelper();
        builder.serviceSpecJson = serviceSpecJson;
        return builder;
    }

    public static ServiceSpecJsonHelper createNewHelper() {
        return createNewHelper(new ServiceSpecJson());
    }

    public ServiceSpecJsonHelper setName(String name) {
        serviceSpecJson.setName(name);
        return this;
    }

    public ServiceSpecJsonHelper setMode(boolean isGlobal) {
        return setMode(isGlobal, null);
    }

    public ServiceSpecJsonHelper setMode(boolean isGlobal, Long replicas) {
        if (serviceSpecJson.getMode() == null) {
            ServiceModeJson serviceModeJson = new ServiceModeJson();
            if (isGlobal) {
                GlobalServiceJson globalServiceJson = new GlobalServiceJson();
                serviceModeJson.setGlobal(globalServiceJson);
            } else {
                ReplicatedServiceJson replicatedServiceJson = new ReplicatedServiceJson();
                if (replicas != null) {
                    replicatedServiceJson.setReplicas(replicas);
                } else {
                    replicatedServiceJson.setReplicas(1);
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
            if (taskSpecJson == null) {
                taskSpecJson = new TaskSpecJson();
            }
            ContainerSpecJson containerSpecJson = taskSpecJson.getContainerSpec();
            if (containerSpecJson == null) {
                containerSpecJson = new ContainerSpecJson();
            }
            containerSpecJson.setImage(image);
            taskSpecJson.setContainerSpec(containerSpecJson);
            serviceSpecJson.setTaskTemplate(taskSpecJson);
        }
        return this;
    }

    public ServiceSpecJson getServiceSpecJson() {
        return serviceSpecJson;
    }

}

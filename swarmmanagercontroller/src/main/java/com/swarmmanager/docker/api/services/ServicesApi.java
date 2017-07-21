package com.swarmmanager.docker.api.services;

import com.swarmmanager.docker.api.common.annotation.DockerRemoteApiMinVersion;
import com.swarmmanager.docker.api.common.json.ServiceGeneralResponseJson;
import com.swarmmanager.docker.api.common.json.ServiceJson;
import com.swarmmanager.docker.api.services.parameters.ServiceCreateParameters;
import com.swarmmanager.docker.api.services.parameters.ServiceLogsParameters;
import com.swarmmanager.docker.api.services.parameters.ServiceUpdateParameters;
import com.swarmmanager.docker.api.services.parameters.ServicesListParameters;

import java.util.List;

public interface ServicesApi {

    List<ServiceJson> listServices(ServicesListParameters parameters);

    ServiceJson inspectService(String id);

    ServiceGeneralResponseJson createService(ServiceCreateParameters parameters);

    void updateService(String id, ServiceUpdateParameters parameters);

    void deleteService(String id);

    @DockerRemoteApiMinVersion("v1.29")
    byte[] getServiceLogs(String id, ServiceLogsParameters parameters);

}

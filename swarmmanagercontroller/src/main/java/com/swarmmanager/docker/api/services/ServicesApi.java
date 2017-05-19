package com.swarmmanager.docker.api.services;

import com.swarmmanager.docker.api.common.json.ServiceGeneralResponseJson;
import com.swarmmanager.docker.api.common.json.ServiceJson;
import com.swarmmanager.docker.api.services.parameters.ServiceCreateParameters;
import com.swarmmanager.docker.api.services.parameters.ServiceDeleteParameters;
import com.swarmmanager.docker.api.services.parameters.ServiceInspectParameters;
import com.swarmmanager.docker.api.services.parameters.ServiceLogsParameters;
import com.swarmmanager.docker.api.services.parameters.ServiceUpdateParameters;
import com.swarmmanager.docker.api.services.parameters.ServicesListParameters;

import java.util.List;

public interface ServicesApi {

    List<ServiceJson> listServices(ServicesListParameters parameters);

    ServiceGeneralResponseJson createService(ServiceCreateParameters parameters);

    ServiceJson inspectService(ServiceInspectParameters parameters);

    void updateService(ServiceUpdateParameters parameters);

    void deleteService(ServiceDeleteParameters parameters);

    byte[] getServiceLogs(ServiceLogsParameters parameters);

}

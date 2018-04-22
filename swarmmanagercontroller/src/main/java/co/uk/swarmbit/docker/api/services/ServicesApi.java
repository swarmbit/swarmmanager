package co.uk.swarmbit.docker.api.services;

import co.uk.swarmbit.docker.api.common.json.ServiceJson;
import co.uk.swarmbit.docker.api.services.parameters.*;
import co.uk.swarmbit.docker.api.common.annotation.DockerRemoteApiMinVersion;
import co.uk.swarmbit.docker.api.common.json.ServiceGeneralResponseJson;

import java.util.List;

public interface ServicesApi {

    List<ServiceJson> listServices(String swarmId, ServicesListParameters parameters);

    ServiceJson inspectService(String swarmId, String id, ServiceInspectParameters parameters);

    ServiceGeneralResponseJson createService(String swarmId, ServiceCreateParameters parameters);

    void updateService(String swarmId, String id, ServiceUpdateParameters parameters);

    void deleteService(String swarmId, String id);

    @DockerRemoteApiMinVersion("v1.29")
    byte[] getServiceLogs(String swarmId, String id, ServiceLogsParameters parameters);

}

package co.uk.swarmbit.docker.api.services;

import co.uk.swarmbit.docker.api.common.AbstractApiImpl;
import co.uk.swarmbit.docker.api.common.json.ServiceJson;
import co.uk.swarmbit.docker.api.services.parameters.*;
import co.uk.swarmbit.docker.api.common.json.ServiceGeneralResponseJson;
import co.uk.swarmbit.rest.RestResponseType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServicesApiImpl extends AbstractApiImpl implements ServicesApi {

    private static final String SERVICES_PATH = "/services";

    private static final String CREATE_PATH = "/create";

    private static final String UPDATE_PATH = "/update";

    private static final String LOGS_PATH = "/logs";

    @Override
    public List<ServiceJson> listServices(String swarmId, ServicesListParameters parameters) {
        return listObjects(SERVICES_PATH, swarmId, new RestResponseType<List<ServiceJson>>() {}, parameters);
    }

    @Override
    public ServiceJson inspectService(String swarmId, String id, ServiceInspectParameters parameters) {
        return inspectObject(SERVICES_PATH, swarmId, new RestResponseType<ServiceJson>() {}, id, parameters);
    }

    @Override
    public ServiceGeneralResponseJson createService(String swarmId, ServiceCreateParameters parameters) {
        return createObject(SERVICES_PATH + CREATE_PATH, swarmId, new RestResponseType<ServiceGeneralResponseJson>() {},
            parameters, parameters);
    }

    @Override
    public void updateService(String swarmId, String id, ServiceUpdateParameters parameters) {
        updateObject(SERVICES_PATH + "/" + id + UPDATE_PATH, swarmId, new RestResponseType<ServiceGeneralResponseJson>() {},
                parameters, parameters, parameters);
    }

    @Override
    public void deleteService(String swarmId, String id) {
          deleteObject(SERVICES_PATH + "/" + id, swarmId, new RestResponseType<Void>() {});
    }

    @Override
    public byte[] getServiceLogs(String swarmId, String id, ServiceLogsParameters parameters) {
        return getObjectLogs(SERVICES_PATH + "/" + id + LOGS_PATH, swarmId, parameters);
    }
}

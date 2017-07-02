package com.swarmmanager.docker.api.services;

import com.swarmmanager.docker.api.common.AbstractApiImpl;
import com.swarmmanager.docker.api.services.parameters.ServiceUpdateParameters;
import com.swarmmanager.docker.api.common.json.ServiceGeneralResponseJson;
import com.swarmmanager.docker.api.common.json.ServiceJson;
import com.swarmmanager.docker.api.services.parameters.ServiceLogsParameters;
import com.swarmmanager.docker.api.services.parameters.ServicesListParameters;
import com.swarmmanager.rest.RestExecutorFactory;
import com.swarmmanager.rest.RestMethod;
import com.swarmmanager.rest.RestParameters;
import com.swarmmanager.rest.RestResponseType;
import com.swarmmanager.docker.api.services.parameters.ServiceCreateParameters;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ServicesApiImpl extends AbstractApiImpl implements ServicesApi {

    private static final String SERVICES_PATH = "/services";

    private static final String CREATE_PATH = "/create";

    private static final String UPDATE_PATH = "/update";

    private static final String LOGS_PATH = "/logs";

    @Override
    public List<ServiceJson> listServices(ServicesListParameters parameters) {
        return listObjects(SERVICES_PATH, new RestResponseType<List<ServiceJson>>() {}, parameters);
    }

    @Override
    public ServiceJson inspectService(String id) {
        return inspectObject(SERVICES_PATH, new RestResponseType<ServiceJson>() {}, id);
    }

    @Override
    public ServiceGeneralResponseJson createService(ServiceCreateParameters parameters) {
        return createObject(SERVICES_PATH + CREATE_PATH, new RestResponseType<ServiceGeneralResponseJson>() {},
            parameters, parameters);
    }

    @Override
    public void updateService(String id, ServiceUpdateParameters parameters) {
        updateObject(SERVICES_PATH + "/" + id + UPDATE_PATH, new RestResponseType<ServiceGeneralResponseJson>() {},
                parameters, parameters, parameters);

    }

    @Override
    public void deleteService(String id) {
          deleteObject(SERVICES_PATH + "/" + id, new RestResponseType<Void>() {});
    }

    @Override
    public byte[] getServiceLogs(ServiceLogsParameters parameters) {
        if (parameters != null) {
            RestParameters restParameters = new RestParameters(dockerWebClient.getBaseResource())
                    .setPath(SERVICES_PATH + "/" + parameters.getId() + LOGS_PATH)
                    .addQueryParam(parameters.getStdoutQueryParam())
                    .addQueryParam(parameters.getStderrQueryParam())
                    .addQueryParam(parameters.getSinceQueryParam())
                    .addQueryParam(parameters.getTimestampQueryParam());
            if (parameters.getTailQueryParam().isPresent()) {
                restParameters.addQueryParam(parameters.getTailQueryParam().get());
            }
            return RestExecutorFactory.createRestExecutor(RestMethod.GET).execute(restParameters, new RestResponseType<byte[]>(){});
        }
        return new byte[]{};
    }
}

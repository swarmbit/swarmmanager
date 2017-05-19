package com.swarmmanager.docker.api.services;

import com.swarmmanager.docker.api.services.parameters.ServiceDeleteParameters;
import com.swarmmanager.docker.api.services.parameters.ServiceInspectParameters;
import com.swarmmanager.docker.api.services.parameters.ServiceUpdateParameters;
import com.swarmmanager.docker.api.common.client.DockerWebClient;
import com.swarmmanager.docker.api.common.json.ServiceGeneralResponseJson;
import com.swarmmanager.docker.api.common.json.ServiceJson;
import com.swarmmanager.docker.api.services.parameters.ServiceLogsParameters;
import com.swarmmanager.docker.api.services.parameters.ServicesListParameters;
import com.swarmmanager.rest.QueryParam;
import com.swarmmanager.rest.RestExecutorFactory;
import com.swarmmanager.rest.RestMethod;
import com.swarmmanager.rest.RestParameters;
import com.swarmmanager.rest.RestResponseType;
import com.swarmmanager.docker.api.services.parameters.ServiceCreateParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ServicesApiImpl implements ServicesApi {

    private static final String SERVICES_PATH = "/services";

    private static final String CREATE_PATH = "/create";

    private static final String UPDATE_PATH = "/update";

    private static final String LOGS_PATH = "/logs";

    @Autowired
    private DockerWebClient dockerWebClient;

    @Override
    public List<ServiceJson> listServices(ServicesListParameters parameters) {
        if (parameters != null) {
            RestParameters restParameters = new RestParameters(dockerWebClient.getBaseResource())
                    .setPath(SERVICES_PATH);
            Optional<QueryParam> filters = parameters.getFilters();
            if (filters.isPresent()) {
                restParameters.addQueryParam(filters.get());
            }
            return RestExecutorFactory.createRestExecutor(RestMethod.GET).execute(restParameters, new RestResponseType<List<ServiceJson>>(){});
        }
        return new ArrayList<>();
    }

    @Override
    public ServiceGeneralResponseJson createService(ServiceCreateParameters parameters) {
        if (parameters != null) {
            RestParameters restParameters = new RestParameters(dockerWebClient.getBaseResource())
                    .setPath(SERVICES_PATH + CREATE_PATH)
                    .setRequestBody(parameters.getService());
            if (parameters.getXRegistryAuthHeader().isPresent()) {
                restParameters.addHeaderParam( parameters.getXRegistryAuthHeader().get());
            }
            return RestExecutorFactory.createRestExecutor(RestMethod.POST).execute(restParameters, new RestResponseType<ServiceGeneralResponseJson>(){});
        }
        ServiceGeneralResponseJson response = new ServiceGeneralResponseJson();
        response.setWarning("Service not provided");
        return response;
    }

    @Override
    public ServiceJson inspectService(ServiceInspectParameters parameters) {
        if (parameters != null) {
            RestParameters restParameters = new RestParameters(dockerWebClient.getBaseResource())
                    .setPath(SERVICES_PATH + "/" + parameters.getId());
            return RestExecutorFactory.createRestExecutor(RestMethod.GET).execute(restParameters, new RestResponseType<ServiceJson>() {
            });
        }
        return new ServiceJson();
    }

    @Override
    public void updateService(ServiceUpdateParameters parameters) {
        if (parameters != null) {
            RestParameters restParameters = new RestParameters(dockerWebClient.getBaseResource())
                    .setPath(SERVICES_PATH + "/" + parameters.getId() + UPDATE_PATH)
                    .setRequestBody(parameters.getService())
                    .addQueryParam(parameters.getVersionQueryParam());
            if (!parameters.getXRegistryAuthHeader().isPresent()) {
                restParameters.addQueryParam(parameters.getRegistryAuthFromQueryParam());
            }
            if (parameters.getXRegistryAuthHeader().isPresent()) {
                restParameters.addHeaderParam(parameters.getXRegistryAuthHeader().get());
            }
            RestExecutorFactory.createRestExecutor(RestMethod.POST).execute(restParameters, new RestResponseType<ServiceGeneralResponseJson>(){});
        }
    }

    @Override
    public void deleteService(ServiceDeleteParameters parameters) {
        if (parameters != null) {
            RestParameters restParameters = new RestParameters(dockerWebClient.getBaseResource())
                    .setPath(SERVICES_PATH + "/" + parameters.getId());
            RestExecutorFactory.createRestExecutor(RestMethod.DELETE).execute(restParameters, new RestResponseType<Void>() {
            });
        }
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

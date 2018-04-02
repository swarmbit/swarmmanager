package com.swarmmanager.docker.api.common;

import com.swarmmanager.docker.api.common.client.DockerWebClient;
import com.swarmmanager.docker.api.common.parameters.FiltersParameters;
import com.swarmmanager.docker.api.common.parameters.HeaderParameters;
import com.swarmmanager.docker.api.common.parameters.QueryParameters;
import com.swarmmanager.docker.api.common.parameters.RequestBodyParameter;
import com.swarmmanager.rest.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractApiImpl {

    @Autowired
    protected DockerWebClient dockerWebClient;

    protected  <E> List<E> listObjects(String apiPath, String swarmId, RestResponseType<List<E>> restResponseType, FiltersParameters filtersParameters) {
        if (filtersParameters != null) {
            RestParameters restParameters = new RestParameters(dockerWebClient.getBaseResource(swarmId))
                    .setPath(apiPath);
            if (filtersParameters.getFilters().isPresent()) {
                restParameters.addQueryParam(filtersParameters.getFilters().get());
            }
            return RestExecutorFactory.createRestExecutor(RestMethod.GET).execute(restParameters, restResponseType);
        }
        return new ArrayList<>();
    }

    protected <E> E inspectObject(String apiPath, String swarmId, RestResponseType<E> restResponseType, String id) {
        String path = id != null ? apiPath + "/" + id : apiPath;
        RestParameters restParameters = new RestParameters(dockerWebClient.getBaseResource(swarmId))
                .setPath(path)
                        .addQueryParam(new QueryParam("insertDefaults", true));
        return RestExecutorFactory.createRestExecutor(RestMethod.GET).execute(restParameters, restResponseType);
    }

    protected <E> E inspectObject(String apiPath, String swarmId, RestResponseType<E> restResponseType) {
        return inspectObject(apiPath, swarmId, restResponseType, null);
    }

    protected <E> E createObject(String apiPath, String swarmId, RestResponseType<E> restResponseType, RequestBodyParameter requestBodyParameter,
                                 HeaderParameters headerParameters) {
        RestParameters restParameters = createRestParameters(apiPath, swarmId, requestBodyParameter, headerParameters, null);
        return RestExecutorFactory.createRestExecutor(RestMethod.POST).execute(restParameters, restResponseType);
    }

    protected <E> E createObject(String apiPath, String swarmId, RestResponseType<E> restResponseType, RequestBodyParameter requestBodyParameter) {
        return createObject(apiPath, swarmId, restResponseType, requestBodyParameter, null);
    }

    protected <E> E updateObject(String apiPath, String swarmId, RestResponseType<E> restResponseType, RequestBodyParameter requestBodyParameter,
                                 QueryParameters queryParameters, HeaderParameters headerParameters) {
        RestParameters restParameters = createRestParameters(apiPath, swarmId, requestBodyParameter, headerParameters, queryParameters);
        return RestExecutorFactory.createRestExecutor(RestMethod.POST).execute(restParameters, restResponseType);
    }

    protected <E> E updateObject(String apiPath, String swarmId, RestResponseType<E> restResponseType, RequestBodyParameter requestBodyParameter,
                               QueryParameters queryParameters) {
       return updateObject(apiPath, swarmId, restResponseType, requestBodyParameter, queryParameters, null);
    }

    protected <E> E deleteObject(String apiPath, String swarmId, RestResponseType<E> restResponseType, QueryParameters queryParameters) {
        RestParameters restParameters = createRestParameters(apiPath, swarmId, null, null, queryParameters);
        return RestExecutorFactory.createRestExecutor(RestMethod.DELETE).execute(restParameters, restResponseType);
    }

    protected <E> E deleteObject(String apiPath, String swarmId, RestResponseType<E> restResponseType) {
        return deleteObject(apiPath, swarmId, restResponseType, null);
    }

    private RestParameters createRestParameters(String apiPath, String swarmId, RequestBodyParameter requestBodyParameter,
                                                HeaderParameters headerParameters, QueryParameters queryParameters) {
        RestParameters restParameters = new RestParameters(dockerWebClient.getBaseResource(swarmId))
                .setPath(apiPath);
        if (requestBodyParameter != null && requestBodyParameter.getRequestBody() != null) {
            restParameters.setRequestBody(requestBodyParameter.getRequestBody());
        }
        if (headerParameters != null && headerParameters.getHeaders() != null) {
            restParameters.addHeaderParams(headerParameters.getHeaders());
        }
        if (queryParameters != null && queryParameters.getQueryParams() != null) {
            restParameters.addQueryParams(queryParameters.getQueryParams());
        }
        return restParameters;
    }

    public byte[] getObjectLogs(String apiPath, String swarmId, QueryParameters queryParameters) {
        RestParameters restParameters = createRestParameters(apiPath, swarmId, null, null, queryParameters);
        return RestExecutorFactory.createRestExecutor(RestMethod.GET).execute(restParameters, new RestResponseType<byte[]>(){});
    }

}

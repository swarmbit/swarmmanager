package com.swarmmanager.docker.api.common;

import com.swarmmanager.docker.api.common.client.DockerWebClient;
import com.swarmmanager.rest.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class AbstractApiImpl {

    @Autowired
    protected DockerWebClient dockerWebClient;

    protected  <E> List<E> listObjects(String apiPath, RestResponseType<List<E>> restResponseType, FiltersParameters filtersParameters) {
        if (filtersParameters != null) {
            RestParameters restParameters = new RestParameters(dockerWebClient.getBaseResource())
                    .setPath(apiPath);
            if (filtersParameters.getFilters().isPresent()) {
                restParameters.addQueryParam(filtersParameters.getFilters().get());
            }
            return RestExecutorFactory.createRestExecutor(RestMethod.GET).execute(restParameters, restResponseType);
        }
        return new ArrayList<>();
    }

    protected <E> E inspectObject(String apiPath, RestResponseType<E> restResponseType, String id) {
        String path = id != null ? apiPath + "/" + id : apiPath;
        RestParameters restParameters = new RestParameters(dockerWebClient.getBaseResource())
                .setPath(path);
        return RestExecutorFactory.createRestExecutor(RestMethod.GET).execute(restParameters, restResponseType);
    }

    protected <E> E inspectObject(String apiPath, RestResponseType<E> restResponseType) {
        return inspectObject(apiPath, restResponseType, null);
    }

    protected <E> E createObject(String apiPath, RestResponseType<E> restResponseType, RequestBodyParameter requestBodyParameter,
                                 HeaderParameters headerParameters) {
        RestParameters restParameters = createRestParameters(apiPath, requestBodyParameter, headerParameters, null);
        return RestExecutorFactory.createRestExecutor(RestMethod.POST).execute(restParameters, restResponseType);
    }

    protected <E> E createObject(String apiPath, RestResponseType<E> restResponseType, RequestBodyParameter requestBodyParameter) {
        return createObject(apiPath, restResponseType, requestBodyParameter, null);
    }

    protected <E> E updateObject(String apiPath, RestResponseType<E> restResponseType, RequestBodyParameter requestBodyParameter,
                                 QueryParameters queryParameters, HeaderParameters headerParameters) {
        RestParameters restParameters = createRestParameters(apiPath, requestBodyParameter, headerParameters, queryParameters);
        return RestExecutorFactory.createRestExecutor(RestMethod.POST).execute(restParameters, restResponseType);
    }

    protected <E> E updateObject(String apiPath, RestResponseType<E> restResponseType, RequestBodyParameter requestBodyParameter,
                               QueryParameters queryParameters) {
       return updateObject(apiPath, restResponseType, requestBodyParameter, queryParameters, null);
    }

    protected <E> E deleteObject(String apiPath, RestResponseType<E> restResponseType, QueryParameters queryParameters) {
        RestParameters restParameters = createRestParameters(apiPath, null, null, queryParameters);
        return RestExecutorFactory.createRestExecutor(RestMethod.DELETE).execute(restParameters, restResponseType);
    }

    protected <E> E deleteObject(String apiPath, RestResponseType<E> restResponseType) {
        return deleteObject(apiPath, restResponseType, null);
    }

    private RestParameters createRestParameters(String apiPath, RequestBodyParameter requestBodyParameter,
                                                HeaderParameters headerParameters, QueryParameters queryParameters) {
        RestParameters restParameters = new RestParameters(dockerWebClient.getBaseResource())
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

}

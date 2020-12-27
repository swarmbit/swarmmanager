package com.swarmbit.docker.api.common

import com.swarmbit.docker.api.common.client.DockerWebClient
import com.swarmbit.docker.api.common.parameters.FiltersParameters
import com.swarmbit.docker.api.common.parameters.HeaderParameters
import com.swarmbit.docker.api.common.parameters.QueryParameters
import com.swarmbit.docker.api.common.parameters.RequestBodyParameter
import com.swarmbit.docker.api.common.rest.executor.RestExecutorFactory
import com.swarmbit.docker.api.common.rest.model.Method
import com.swarmbit.docker.api.common.rest.model.Parameters
import com.swarmbit.docker.api.common.rest.model.QueryParam
import com.swarmbit.docker.api.common.rest.model.ResponseType

abstract class AbstractApiImpl(private val dockerWebClient: DockerWebClient) {

    protected open fun <E> listObjects(
            apiPath: String,
            swarmId: String,
            responseType: ResponseType<List<E>>,
            filtersParameters: FiltersParameters? = null
    ): List<E> {
        val restParameters: Parameters = Parameters(dockerWebClient.getBaseResource(swarmId)).setPath(apiPath)
        if (filtersParameters != null) {
            restParameters.addQueryParam(filtersParameters.filters)
        }
        return RestExecutorFactory.createRestExecutor(Method.GET).execute(restParameters, responseType) ?: emptyList()
    }

    protected open fun <E> inspectObject(
            apiPath: String,
            swarmId: String,
            responseType: ResponseType<E>,
            id: String? = null,
            queryParameters: QueryParameters? = null
    ): E? {
        val path = if (id != null) "$apiPath/$id" else apiPath
        var restParameters: Parameters = Parameters(dockerWebClient.getBaseResource(swarmId))
                .setPath(path)
        if (queryParameters != null) {
            val queryParams: List<QueryParam> = queryParameters.queryParams
            for (queryParam in queryParams) {
                restParameters = restParameters.addQueryParam(queryParam)
            }
        }
        return RestExecutorFactory.createRestExecutor(Method.GET).execute(restParameters, responseType)
    }

    protected open fun <E> createObject(
            apiPath: String,
            swarmId: String,
            responseType: ResponseType<E>,
            requestBodyParameter: RequestBodyParameter,
            headerParameters: HeaderParameters?
    ): E? {
        val parameter: Parameters = createRestParameters(apiPath, swarmId, requestBodyParameter, headerParameters, null)
        return RestExecutorFactory.createRestExecutor(Method.POST).execute(parameter, responseType)
    }

    protected open fun <E> updateObject(
            apiPath: String,
            swarmId: String,
            responseType: ResponseType<E>,
            requestBodyParameter: RequestBodyParameter,
            queryParameters: QueryParameters? = null,
            headerParameters: HeaderParameters? = null
    ): E? {
        val parameters: Parameters = createRestParameters(apiPath, swarmId, requestBodyParameter, headerParameters, queryParameters)
        return RestExecutorFactory.createRestExecutor(Method.POST).execute(parameters, responseType)
    }

    protected open fun <E> deleteObject(
            apiPath: String,
            swarmId: String,
            responseType: ResponseType<E>,
            queryParameters: QueryParameters? = null
    ): E? {
        val restParameters: Parameters = createRestParameters(apiPath, swarmId, null, null, queryParameters)
        return RestExecutorFactory.createRestExecutor(Method.DELETE).execute(restParameters, responseType)
    }

    protected open fun getObjectLogs(
            apiPath: String,
            swarmId: String,
            queryParameters: QueryParameters?
    ): ByteArray? {
        val parameters: Parameters = createRestParameters(apiPath, swarmId, null, null, queryParameters)
        return RestExecutorFactory.createRestExecutor(Method.GET).execute(parameters, object : ResponseType<ByteArray>() {})
    }

    private fun createRestParameters(
            apiPath: String,
            swarmId: String,
            requestBodyParameter: RequestBodyParameter?,
            headerParameters: HeaderParameters?,
            queryParameters: QueryParameters?
    ): Parameters {
        val parameters: Parameters = Parameters(dockerWebClient.getBaseResource(swarmId)).setPath(apiPath)
        if (requestBodyParameter?.requestBody != null) {
            parameters.setRequestBody(requestBodyParameter.requestBody)
        }
        if (headerParameters?.headers != null) {
            parameters.addHeaderParams(headerParameters.headers)
        }
        if (queryParameters?.queryParams != null) {
            parameters.addQueryParams(queryParameters.queryParams)
        }
        return parameters
    }

}
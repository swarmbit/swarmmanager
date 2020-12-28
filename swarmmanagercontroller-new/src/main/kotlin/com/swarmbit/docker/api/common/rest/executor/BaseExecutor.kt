package com.swarmbit.docker.api.common.rest.executor

import com.swarmbit.docker.api.common.rest.model.Parameters
import com.swarmbit.docker.api.common.rest.model.ResponseType
import javax.ws.rs.client.Invocation
import javax.ws.rs.client.WebTarget

abstract class BaseExecutor : RestExecutor {

    override fun <E> execute(parameters: Parameters, responseType: ResponseType<E>): E? {
        var webTarget: WebTarget = parameters.baseRequest
        val path: String? = parameters.path
        if (path != null) {
            webTarget = webTarget.path(parameters.path)
        }
        for (queryParam in parameters.getQueryParams()) {
            webTarget = webTarget.queryParam(queryParam.name, queryParam.value)
        }
        val requestBuilder = webTarget.request()
        for (headerParam in parameters.getHeaderParams()) {
            requestBuilder.header(headerParam.name, headerParam.value)
        }
        return execute(parameters.requestBody, requestBuilder, responseType)
    }

    abstract fun <E> execute(requestBody: Any?, requestBuilder: Invocation.Builder, responseType: ResponseType<E>): E?
}

package com.swarmbit.docker.api.common.rest.executor

import com.swarmbit.docker.api.common.rest.model.ResponseType
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.MediaType

class GetExecutor : BaseExecutor() {
    override fun <E> execute(requestBody: Any?, requestBuilder: Invocation.Builder, responseType: ResponseType<E>): E? {
        return requestBuilder.accept(MediaType.APPLICATION_JSON).get(responseType)
    }
}
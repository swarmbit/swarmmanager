package com.swarmbit.docker.api.common.rest.executor

import com.swarmbit.docker.api.common.rest.model.ResponseType
import javax.ws.rs.client.Invocation

class DeleteExecutor : BaseExecutor() {
    override fun <E> execute(requestBody: Any?, requestBuilder: Invocation.Builder, responseType: ResponseType<E>): E? {
        requestBuilder.buildDelete().invoke()
        return null
    }
}

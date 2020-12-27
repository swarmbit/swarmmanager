package com.swarmbit.docker.api.common.rest.executor

import com.swarmbit.docker.api.common.rest.model.ResponseType
import javax.ws.rs.client.Entity
import javax.ws.rs.client.Invocation
import javax.ws.rs.core.MediaType


class PostExecutor: BaseExecutor() {
    override fun <E> execute(requestBody: Any?, requestBuilder: Invocation.Builder, responseType: ResponseType<E>): E? {
        val response = requestBuilder
                .accept(MediaType.APPLICATION_JSON)
                .buildPost(Entity.entity<Any>(requestBody, MediaType.APPLICATION_JSON))
                .invoke()
        var entity: E? = null
        if (response.hasEntity()) {
            entity = response.readEntity(responseType)
        }
        return entity
    }
}

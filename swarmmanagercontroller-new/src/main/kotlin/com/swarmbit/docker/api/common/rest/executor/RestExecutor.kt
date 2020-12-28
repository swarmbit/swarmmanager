package com.swarmbit.docker.api.common.rest.executor

import com.swarmbit.docker.api.common.rest.model.Parameters
import com.swarmbit.docker.api.common.rest.model.ResponseType

interface RestExecutor {
    fun <E> execute(parameters: Parameters, responseType: ResponseType<E>): E?
}

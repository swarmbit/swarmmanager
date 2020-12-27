package com.swarmbit.docker.api.common.rest.executor

import com.swarmbit.docker.api.common.rest.model.Method
import com.swarmbit.docker.api.common.rest.model.Method.DELETE
import com.swarmbit.docker.api.common.rest.model.Method.POST

object RestExecutorFactory {
    fun createRestExecutor(method: Method): RestExecutor {
        return when (method) {
            POST -> PostExecutor()
            DELETE -> DeleteExecutor()
            else -> GetExecutor()
        }
    }
}
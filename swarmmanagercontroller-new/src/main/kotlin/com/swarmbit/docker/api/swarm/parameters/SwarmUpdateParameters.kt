package com.swarmbit.docker.api.swarm.parameters

import com.swarmbit.docker.api.common.parameters.QueryParameters
import com.swarmbit.docker.api.common.parameters.RequestBodyParameter
import com.swarmbit.docker.api.common.rest.model.QueryParam
import com.swarmbit.docker.api.swarm.model.SwarmSpec

class SwarmUpdateParameters : RequestBodyParameter, QueryParameters {

    companion object {
        private const val VERSION_NAME = "version"
        private const val ROTATE_WORKER_TOKEN_NAME = "rotateWorkerToken"
        private const val ROTATE_MANAGER_TOKEN_NAME = "rotateManagerToken"
        private const val ROTATE_MANAGER_UNLOCK_KEY_NAME = "rotateManagerUnlockKey"
    }

    private var spec = SwarmSpec()
    private var versionQueryParam = QueryParam(VERSION_NAME, 0L)
    private var rotateWorkerTokenQueryParam = QueryParam(ROTATE_WORKER_TOKEN_NAME, false)
    private var rotateManagerTokenQueryParam = QueryParam(ROTATE_MANAGER_TOKEN_NAME, false)
    private var rotateManagerUnlockKeyQueryParam = QueryParam(ROTATE_MANAGER_UNLOCK_KEY_NAME, false)

    fun getVersionQueryParam(): QueryParam {
        return versionQueryParam
    }

    fun setVersionQueryParam(versionValue: Long): SwarmUpdateParameters {
        versionQueryParam = QueryParam(VERSION_NAME, versionValue)
        return this
    }

    fun setSpec(spec: SwarmSpec): SwarmUpdateParameters {
        this.spec = spec
        return this
    }

    fun setRotateWorkerTokenQueryParam(rotateWorkerTokenValue: Boolean): SwarmUpdateParameters {
        rotateWorkerTokenQueryParam = QueryParam(ROTATE_WORKER_TOKEN_NAME, rotateWorkerTokenValue)
        return this
    }

    fun setRotateManagerTokenQueryParam(rotateManagerTokenValue: Boolean): SwarmUpdateParameters {
        rotateManagerTokenQueryParam = QueryParam(ROTATE_MANAGER_TOKEN_NAME, rotateManagerTokenValue)
        return this
    }

    fun setRotateManagerUnlockKeyQueryParam(rotateManagerUnlockKeyValue: Boolean): SwarmUpdateParameters {
        rotateManagerUnlockKeyQueryParam = QueryParam(ROTATE_MANAGER_UNLOCK_KEY_NAME, rotateManagerUnlockKeyValue)
        return this
    }

    override val requestBody: Any
        get() = spec

    override val queryParams: List<QueryParam>
        get() {
            return listOf(
                versionQueryParam,
                rotateWorkerTokenQueryParam,
                rotateManagerTokenQueryParam,
                rotateManagerUnlockKeyQueryParam
            )
        }

}

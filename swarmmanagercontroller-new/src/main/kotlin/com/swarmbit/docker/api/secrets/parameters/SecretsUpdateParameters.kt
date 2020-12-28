package com.swarmbit.docker.api.secrets.parameters

import com.swarmbit.docker.api.common.parameters.QueryParameters
import com.swarmbit.docker.api.common.parameters.RequestBodyParameter
import com.swarmbit.docker.api.common.rest.model.QueryParam
import com.swarmbit.docker.api.secrets.model.SecretSpec

class SecretsUpdateParameters : RequestBodyParameter, QueryParameters {
    companion object {
        private const val VERSION_NAME = "version"
    }

    private var secret: SecretSpec = SecretSpec()
    private var versionQueryParam: QueryParam = QueryParam(VERSION_NAME, 0L)

    fun setSecret(secret: SecretSpec): SecretsUpdateParameters {
        this.secret = secret
        return this
    }

    fun setVersionQueryParam(versionValue: Long): SecretsUpdateParameters {
        versionQueryParam = QueryParam(VERSION_NAME, versionValue)
        return this
    }

    override val queryParams: List<QueryParam>
        get() {
            return listOf(versionQueryParam)
        }

    override val requestBody: Any
        get() = secret
}

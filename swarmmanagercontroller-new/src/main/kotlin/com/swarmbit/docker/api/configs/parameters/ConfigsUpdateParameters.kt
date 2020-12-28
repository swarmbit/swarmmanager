package com.swarmbit.docker.api.configs.parameters

import com.swarmbit.docker.api.common.parameters.QueryParameters
import com.swarmbit.docker.api.common.parameters.RequestBodyParameter
import com.swarmbit.docker.api.common.rest.model.QueryParam
import com.swarmbit.docker.api.configs.model.ConfigSpec

class ConfigsUpdateParameters : RequestBodyParameter, QueryParameters {
    companion object {
        private const val VERSION_NAME = "version"
    }

    private var config: ConfigSpec = ConfigSpec()
    private var versionQueryParam: QueryParam = QueryParam(VERSION_NAME, 0L)

    fun setConfig(config: ConfigSpec): ConfigsUpdateParameters {
        this.config = config
        return this
    }

    fun setVersionQueryParam(versionValue: Long): ConfigsUpdateParameters {
        versionQueryParam = QueryParam(VERSION_NAME, versionValue)
        return this
    }

    override val queryParams: List<QueryParam>
        get() {
            return listOf(versionQueryParam)
        }

    override val requestBody: Any
        get() = config
}

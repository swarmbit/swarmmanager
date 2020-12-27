package com.swarmbit.docker.api.services.parameters

import com.swarmbit.docker.api.common.parameters.HeaderParameters
import com.swarmbit.docker.api.common.parameters.QueryParameters
import com.swarmbit.docker.api.common.parameters.RequestBodyParameter
import com.swarmbit.docker.api.common.rest.model.HeaderParam
import com.swarmbit.docker.api.common.rest.model.QueryParam
import com.swarmbit.docker.api.services.model.ServiceSpec

class ServicesUpdateParameters : RequestBodyParameter, HeaderParameters, QueryParameters {

    companion object {
        private const val X_REGISTRY_AUTH_HEADER = "X-Registry-Auth"
        private const val VERSION_NAME = "version"
        private const val REGISTRY_AUTH_FORM_NAME = "registryAuthFrom"
        private const val ROLLBACK_NAME = "rollback"
    }

    private var service = ServiceSpec()
    private var xRegistryAuthHeader: HeaderParam? = null
    private var versionQueryParam = QueryParam(VERSION_NAME, 0L)
    private var rollbackQueryParam: QueryParam? = null
    private var registryAuthFromQueryParam: QueryParam? = null


    fun setService(service: ServiceSpec): ServicesUpdateParameters {
        this.service = service
        return this
    }

    fun setVersionQueryParam(versionValue: Long): ServicesUpdateParameters {
        versionQueryParam = QueryParam(VERSION_NAME, versionValue)
        return this
    }

    fun setRollback(rollback: Boolean): ServicesUpdateParameters {
        if (rollback) {
            rollbackQueryParam = QueryParam(ROLLBACK_NAME, "previous")
        }
        return this
    }

    fun setXRegistryAuthHeader(xRegistryAuthValue: String): ServicesUpdateParameters {
        xRegistryAuthHeader = HeaderParam(X_REGISTRY_AUTH_HEADER, xRegistryAuthValue)
        return this
    }

    fun setRegistryAuthFromSpec(): ServicesUpdateParameters {
        registryAuthFromQueryParam = QueryParam(REGISTRY_AUTH_FORM_NAME, "spec")
        return this
    }

    override val requestBody: Any
        get() = service

    override val queryParams: List<QueryParam>
        get() {
            val queryParams = mutableListOf<QueryParam>()
            queryParams.add(versionQueryParam)
            registryAuthFromQueryParam?.let {
                queryParams.add(it)
            }
            rollbackQueryParam?.let {
                queryParams.add(it)
            }
            return queryParams
        }

    override val headers: List<HeaderParam>
        get() {
            val headerParams = mutableListOf<HeaderParam>()
            xRegistryAuthHeader?.let {
                headerParams.add(it)
            }
            return headerParams
        }

}
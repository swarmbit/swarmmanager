package com.swarmbit.docker.api.services.parameters

import com.swarmbit.docker.api.common.parameters.HeaderParameters
import com.swarmbit.docker.api.common.parameters.RequestBodyParameter
import com.swarmbit.docker.api.common.rest.model.HeaderParam
import com.swarmbit.docker.api.services.model.ServiceSpec

class ServicesCreateParameters : RequestBodyParameter, HeaderParameters {
    companion object {
        private const val X_REGISTRY_AUTH_HEADER_NAME = "X-Registry-Auth"
    }

    private var xRegistryAuthHeader: HeaderParam? = null
    private var service: ServiceSpec = ServiceSpec()

    fun setXRegistryAuthHeader(XRegistryAuthValue: String): ServicesCreateParameters {
        xRegistryAuthHeader = HeaderParam(X_REGISTRY_AUTH_HEADER_NAME, XRegistryAuthValue)
        return this
    }

    fun setService(service: ServiceSpec): ServicesCreateParameters {
        this.service = service
        return this
    }

    override val requestBody: ServiceSpec
        get() = service

    override val headers: List<HeaderParam>
        get() {
            val headers = mutableListOf<HeaderParam>()
            xRegistryAuthHeader?.let {
                headers.add(it)
            }
            return headers
        }
}

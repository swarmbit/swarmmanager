package com.swarmbit.docker.api.networks.parameters

import com.swarmbit.docker.api.common.parameters.QueryParameters
import com.swarmbit.docker.api.common.rest.model.QueryParam

class NetworksInspectParameters: QueryParameters {
    companion object {
        private const val VERBOSE = "insertDefaults"
        private const val SCOPE = "scopeQueryParam"
    }

    private var verboseQueryParam: QueryParam = QueryParam(VERBOSE, false)
    private var scopeQueryParam: QueryParam? = null

    fun setVerboseQueryParam(insertDefaults: Boolean): NetworksInspectParameters {
        verboseQueryParam = QueryParam(VERBOSE, insertDefaults)
        return this
    }

    fun setScopeQueryParam(scope: Scope): NetworksInspectParameters {
        scopeQueryParam = QueryParam(SCOPE, scope.toString().toLowerCase())
        return this
    }

    override val queryParams: List<QueryParam>
        get() {
            val params = mutableListOf<QueryParam>(verboseQueryParam)
            scopeQueryParam?.let {
                params.add(it)
            }
            return params
        }

}
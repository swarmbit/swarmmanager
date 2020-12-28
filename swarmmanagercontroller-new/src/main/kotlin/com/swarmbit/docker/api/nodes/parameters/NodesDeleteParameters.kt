package com.swarmbit.docker.api.nodes.parameters

import com.swarmbit.docker.api.common.parameters.QueryParameters
import com.swarmbit.docker.api.common.rest.model.QueryParam

class NodesDeleteParameters : QueryParameters {
    companion object {
        private const val FORCE_NAME = "force"
    }

    private var forceQueryParam = QueryParam(FORCE_NAME, false)

    fun setForceQueryParam(forceValue: Boolean): NodesDeleteParameters {
        forceQueryParam = QueryParam(FORCE_NAME, forceValue)
        return this
    }

    override val queryParams: List<QueryParam>
        get() {
            return listOf(forceQueryParam)
        }
}

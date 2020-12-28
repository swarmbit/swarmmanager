package com.swarmbit.docker.api.nodes.parameters

import com.swarmbit.docker.api.common.parameters.QueryParameters
import com.swarmbit.docker.api.common.parameters.RequestBodyParameter
import com.swarmbit.docker.api.common.rest.model.QueryParam
import com.swarmbit.docker.api.nodes.model.NodeSpec

class NodesUpdateParameters : RequestBodyParameter, QueryParameters {
    companion object {
        private const val VERSION_NAME = "version"
    }

    private var versionQueryParam = QueryParam(VERSION_NAME, 0L)
    private var node = NodeSpec()

    fun setVersionQueryParam(versionValue: Long): NodesUpdateParameters {
        versionQueryParam = QueryParam(VERSION_NAME, versionValue)
        return this
    }

    fun setNode(node: NodeSpec): NodesUpdateParameters {
        this.node = node
        return this
    }

    override val requestBody: Any
        get() = node

    override val queryParams: List<QueryParam>
        get() {
            return listOf(versionQueryParam)
        }
}

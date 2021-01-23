package com.swarmbit.docker.api.nodes

import com.swarmbit.docker.api.nodes.model.Node
import com.swarmbit.docker.api.nodes.parameters.NodesDeleteParameters
import com.swarmbit.docker.api.nodes.parameters.NodesListParameters
import com.swarmbit.docker.api.nodes.parameters.NodesUpdateParameters

interface NodesApi {
    fun listNodes(swarmId: String, parameters: NodesListParameters = NodesListParameters()): List<Node>

    fun inspectNode(swarmId: String, id: String): Node

    fun updateNode(swarmId: String, id: String, parameters: NodesUpdateParameters)

    fun deleteNode(swarmId: String, id: String, parameters: NodesDeleteParameters)
}

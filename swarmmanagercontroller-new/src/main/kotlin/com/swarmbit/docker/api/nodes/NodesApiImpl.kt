package com.swarmbit.docker.api.nodes

import com.swarmbit.docker.api.common.AbstractApiImpl
import com.swarmbit.docker.api.common.client.DockerWebClient
import com.swarmbit.docker.api.common.rest.model.ResponseType
import com.swarmbit.docker.api.nodes.model.Node
import com.swarmbit.docker.api.nodes.parameters.NodesDeleteParameters
import com.swarmbit.docker.api.nodes.parameters.NodesListParameters
import com.swarmbit.docker.api.nodes.parameters.NodesUpdateParameters
import io.micronaut.context.annotation.Context

@Context
class NodesApiImpl(dockerWebClient: DockerWebClient) : AbstractApiImpl(dockerWebClient), NodesApi {
    companion object {
        private const val NODES_PATH = "/nodes"
    }

    override fun listNodes(swarmId: String, parameters: NodesListParameters): List<Node> {
        return listObjects(NODES_PATH, swarmId, object : ResponseType<List<Node>>() {}, parameters)
    }

    override fun inspectNode(swarmId: String, id: String): Node {
        return inspectObject(NODES_PATH, swarmId, object : ResponseType<Node>() {}, id)
                ?: throw IllegalArgumentException("No node found for swarmId ($swarmId) and nodeId (${id})")
    }

    override fun updateNode(swarmId: String, id: String, parameters: NodesUpdateParameters) {
        updateObject("$NODES_PATH/$id", swarmId, object : ResponseType<Void>() {}, parameters)
                ?: throw IllegalArgumentException("No node found for swarmId ($swarmId) and nodeId (${id})")
    }

    override fun deleteNode(swarmId: String, id: String, parameters: NodesDeleteParameters) {
        deleteObject("$NODES_PATH/$id", swarmId, object : ResponseType<Void>() {}, parameters)
    }

}
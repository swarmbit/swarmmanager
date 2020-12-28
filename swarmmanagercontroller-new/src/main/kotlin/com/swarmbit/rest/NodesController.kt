package com.swarmbit.rest

import com.swarmbit.docker.api.nodes.NodesApi
import com.swarmbit.docker.api.nodes.model.Node
import com.swarmbit.docker.api.nodes.parameters.NodesListParameters
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("nodes")
class NodesController(private val nodesApi: NodesApi) {

    @Get
    fun getNodes(): List<Node> {
        return nodesApi.listNodes("swarm", NodesListParameters())
    }
}

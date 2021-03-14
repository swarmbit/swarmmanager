package com.swarmbit.services.docker.node

import com.swarmbit.services.docker.node.model.DockerNode
import com.swarmbit.services.docker.node.model.DockerNodeSummary
import com.swarmbit.services.docker.node.model.DockerNodeUpdate

interface DockerNodeService {
    fun ls(swarmId: String): List<DockerNodeSummary>
    fun inspect(swarmId: String, nodeId: String): DockerNode
    fun update(swarmId: String, nodeId: String, dockerNodeUpdate: DockerNodeUpdate)
    fun rm(swarmId: String, nodeId: String, force: Boolean)
}

package com.swarmbit.docker.api.nodes.parameters

import com.swarmbit.docker.api.common.AbstractFilters
import com.swarmbit.docker.api.common.annotation.DockerRemoteApiMinVersion

class NodesFilters : AbstractFilters() {

    fun setId(id: String): NodesFilters {
        addFilter("id", id)
        return this
    }

    fun setLabel(label: String): NodesFilters {
        addFilter("label", label)
        return this
    }

    fun setMembership(membership: NodeMembership): NodesFilters {
        addFilter("membership", membership.toString().toLowerCase())
        return this
    }

    fun setName(name: String): NodesFilters {
        addFilter("name", name)
        return this
    }

    fun setRole(role: NodeRole): NodesFilters {
        addFilter("role", role.toString().toLowerCase())
        return this
    }

    @DockerRemoteApiMinVersion("v1.40")
    fun setNodeLabel(nodeLabel: String, equals: Boolean = true): NodesFilters {
        addFilter("node.label", nodeLabel, equals)
        return this
    }
}

package com.swarmbit.docker.api.networks.parameters

import com.swarmbit.docker.api.common.AbstractFilters
import com.swarmbit.docker.api.common.annotation.DockerRemoteApiMinVersion

class NetworksFilters : AbstractFilters() {
    fun setId(id: String): NetworksFilters {
        addFilter("id", id)
        return this
    }

    fun setLabel(label: String): NetworksFilters {
        addFilter("label", label)
        return this
    }

    fun setDriver(driver: String): NetworksFilters {
        addFilter("driver", driver)
        return this
    }

    fun setName(name: String): NetworksFilters {
        addFilter("name", name)
        return this
    }

    fun setType(type: Type): NetworksFilters {
        addFilter("type", type.toString().toLowerCase())
        return this
    }

    @DockerRemoteApiMinVersion("v1.29")
    fun setScope(scope: Scope): NetworksFilters {
        addFilter("scope", scope.toString())
        return this
    }

    @DockerRemoteApiMinVersion("v1.40")
    fun setDangling(dangling: Boolean): NetworksFilters {
        addFilter("dangling", dangling.toString())
        return this
    }
}

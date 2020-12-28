package com.swarmbit.docker.api.services.parameters

import com.swarmbit.docker.api.common.AbstractFilters
import com.swarmbit.docker.api.common.annotation.DockerRemoteApiMinVersion

class ServicesFilters : AbstractFilters() {
    enum class Mode {
        REPLICATED, GLOBAL;
    }

    fun setId(id: String): ServicesFilters {
        addFilter("id", id)
        return this
    }

    fun setLabel(label: String): ServicesFilters {
        addFilter("label", label)
        return this
    }

    fun setName(name: String): ServicesFilters {
        addFilter("name", name)
        return this
    }

    @DockerRemoteApiMinVersion("v1.28")
    fun setMode(mode: Mode): ServicesFilters {
        addFilter("mode", mode.toString().toLowerCase())
        return this
    }
}

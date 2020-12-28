package com.swarmbit.docker.api.configs.parameters

import com.swarmbit.docker.api.common.AbstractFilters

class ConfigsFilters : AbstractFilters() {

    fun setId(id: String): ConfigsFilters {
        addFilter("id", id)
        return this
    }

    fun setName(name: String): ConfigsFilters {
        addFilter("name", name)
        return this
    }

    fun setLabel(label: String): ConfigsFilters {
        addFilter("label", label)
        return this
    }

}
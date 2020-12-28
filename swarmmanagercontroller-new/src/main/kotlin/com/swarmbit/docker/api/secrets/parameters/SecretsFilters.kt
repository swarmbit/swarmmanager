package com.swarmbit.docker.api.secrets.parameters

import com.swarmbit.docker.api.common.AbstractFilters

class SecretsFilters : AbstractFilters() {

    fun setId(id: String): SecretsFilters {
        addFilter("id", id)
        return this
    }

    fun setName(name: String): SecretsFilters {
        addFilter("name", name)
        return this
    }

    fun setLabel(label: String): SecretsFilters {
        addFilter("label", label)
        return this
    }

}
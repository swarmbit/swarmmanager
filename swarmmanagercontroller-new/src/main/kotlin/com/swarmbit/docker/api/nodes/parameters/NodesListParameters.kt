package com.swarmbit.docker.api.nodes.parameters

import com.swarmbit.docker.api.common.parameters.FiltersParameters
import com.swarmbit.docker.api.common.rest.model.QueryParam

class NodesListParameters : FiltersParameters {
    companion object {
        private const val FILTERS_NAME = "filters"
    }

    private var innerFilters: QueryParam? = null

    fun setFilters(filters: NodesFilters): NodesListParameters {
        this.innerFilters = QueryParam(FILTERS_NAME, filters.asUrlJson())
        return this
    }

    override val filters: QueryParam?
        get() = innerFilters

}
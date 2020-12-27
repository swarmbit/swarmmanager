package com.swarmbit.docker.api.services.parameters

import com.swarmbit.docker.api.common.parameters.FiltersParameters
import com.swarmbit.docker.api.common.rest.model.QueryParam

class ServicesListParameters : FiltersParameters {

    companion object {
        private const val FILTERS_NAME = "filters"
    }

    private var innerFilters: QueryParam? = null

    fun setFilters(filters: ServicesFilters): ServicesListParameters {
        this.innerFilters = QueryParam(FILTERS_NAME, filters.asUrlJson())
        return this
    }

    override val filters: QueryParam?
        get() = innerFilters

}
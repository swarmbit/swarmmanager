package com.swarmbit.docker.api.services.parameters

import com.swarmbit.docker.api.common.annotation.DockerRemoteApiMinVersion
import com.swarmbit.docker.api.common.parameters.FiltersParameters
import com.swarmbit.docker.api.common.rest.model.QueryParam

class ServicesListParameters : FiltersParameters {

    companion object {
        private const val FILTERS_NAME = "filters"
        private const val STATUS_NAME = "status"
    }

    private var innerFilters: QueryParam? = null

    fun setFilters(filters: ServicesFilters): ServicesListParameters {
        this.innerFilters = QueryParam(FILTERS_NAME, filters.asUrlJson())
        return this
    }

    @DockerRemoteApiMinVersion("v1.41")
    fun setStatus(status: Boolean): ServicesListParameters {
        this.innerFilters = QueryParam(STATUS_NAME, status.toString())
        return this
    }

    override val filters: QueryParam?
        get() = innerFilters

}
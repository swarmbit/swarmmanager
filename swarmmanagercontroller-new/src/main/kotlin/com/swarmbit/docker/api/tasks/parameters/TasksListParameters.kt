package com.swarmbit.docker.api.tasks.parameters

import com.swarmbit.docker.api.common.parameters.FiltersParameters
import com.swarmbit.docker.api.common.rest.model.QueryParam

class TasksListParameters : FiltersParameters {

    companion object {
        private const val FILTERS_NAME = "filters"
    }

    private var innerFilters: QueryParam? = null

    fun setFilters(filters: TasksFilters): TasksListParameters {
        this.innerFilters = QueryParam(FILTERS_NAME, filters.asUrlJson())
        return this
    }

    override val filters: QueryParam?
        get() = innerFilters
}

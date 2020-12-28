package com.swarmbit.docker.api.services.parameters

import com.swarmbit.docker.api.common.parameters.QueryParameters
import com.swarmbit.docker.api.common.rest.model.QueryParam

class ServicesInspectParameters : QueryParameters {
    companion object {
        private const val INSERT_DEFAULTS = "insertDefaults"
    }

    private var insertDefaultsQueryParam = QueryParam(INSERT_DEFAULTS, false)

    fun setInsertDefaultsQueryParam(insertDefaults: Boolean): ServicesInspectParameters {
        insertDefaultsQueryParam = QueryParam(INSERT_DEFAULTS, insertDefaults)
        return this
    }

    override val queryParams: List<QueryParam>
        get() {
            return mutableListOf(insertDefaultsQueryParam)
        }
}

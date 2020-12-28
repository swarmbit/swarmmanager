package com.swarmbit.docker.api.common.parameters

import com.swarmbit.docker.api.common.rest.model.QueryParam

interface FiltersParameters {
    val filters: QueryParam?
}

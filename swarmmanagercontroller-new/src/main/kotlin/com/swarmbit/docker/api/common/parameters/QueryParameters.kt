package com.swarmbit.docker.api.common.parameters

import com.swarmbit.docker.api.common.rest.model.QueryParam

interface QueryParameters {
    val queryParams: List<QueryParam>
}
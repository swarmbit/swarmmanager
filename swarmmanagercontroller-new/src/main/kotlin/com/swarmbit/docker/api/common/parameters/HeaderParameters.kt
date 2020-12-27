package com.swarmbit.docker.api.common.parameters

import com.swarmbit.docker.api.common.rest.model.HeaderParam

interface HeaderParameters {
    val headers: List<HeaderParam>
}
package com.swarmbit.services.docker.common.model

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class DockerState(
    val tasks: List<DockerTask>
)

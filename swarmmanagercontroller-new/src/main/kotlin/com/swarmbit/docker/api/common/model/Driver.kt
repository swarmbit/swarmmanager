package com.swarmbit.docker.api.common.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Driver(
    @JsonProperty("Name")
    val name: String? = null,
    @JsonProperty("Options")
    val options: Map<String, String>? = null
)

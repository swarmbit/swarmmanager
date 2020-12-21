package com.swarmbit.docker.api.model.common

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class RestartPolicy(
        @JsonProperty("Condition")
        val condition: String? = null,
        @JsonProperty("Delay")
        val delay: Long? = null,
        @JsonProperty("MaxAttempts")
        val maxAttempts: Long? = null,
        @JsonProperty("Window")
        val window: Long? = null
)
package com.swarmbit.docker.api.common.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class SecretAndConfigReferenceFileTarget(
        @JsonProperty("Name")
        val name: String = "",
        @JsonProperty("UID")
        val uid: String = "",
        @JsonProperty("GID")
        val gid: String = "",
        @JsonProperty("Mode")
        val mode: Int = 0
)
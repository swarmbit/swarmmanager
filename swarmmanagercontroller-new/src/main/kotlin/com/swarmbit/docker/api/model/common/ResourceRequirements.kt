package com.swarmbit.docker.api.model.common

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.swarmbit.docker.api.annotation.DockerRemoteApiMinVersion

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ResourceRequirements(
        @JsonProperty("Limits")
        val limits: Limit? = null,
        @JsonProperty("Reservations")
        val reservations: Resources? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Limit(
        @JsonProperty("NanoCPUs")
        val nanoCPUs: Long? = null,
        @JsonProperty("MemoryBytes")
        val memoryBytes: Long? = null,
        @DockerRemoteApiMinVersion("v1.41")
        @JsonProperty("Pids")
        val pids: Long? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Resources(
        @JsonProperty("NanoCPUs")
        val nanoCPUs: Long? = null,
        @JsonProperty("MemoryBytes")
        val memoryBytes: Long? = null,
        @JsonProperty("GenericResources")
        val genericResources: List<GenericResources>? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class GenericResources(
        @JsonProperty("NamedGenericResource")
        val namedGenericResource: NamedGenericResource? = null,
        @JsonProperty("DiscreteGenericResource")
        val discreteGenericResource: DiscreteGenericResource? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class NamedGenericResource(
        @JsonProperty("Kind")
        val kind: String? = null,
        @JsonProperty("Value")
        val value: String? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class DiscreteGenericResource(
        @JsonProperty("Kind")
        val kind: String? = null,
        @JsonProperty("Value")
        val value: String? = null
)
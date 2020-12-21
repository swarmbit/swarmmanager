package com.swarmbit.docker.api.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.swarmbit.docker.api.model.common.Driver
import com.swarmbit.docker.api.model.common.SecretAndConfigReferenceFileTarget
import com.swarmbit.docker.api.model.common.Version

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Secret(
        @JsonProperty("ID")
        val id: String? = null,
        @JsonProperty("Version")
        val version: Version? = null,
        @JsonProperty("CreatedAt")
        val createdAt: String? = null,
        @JsonProperty("UpdatedAt")
        val updatedAt: String? = null,
        @JsonProperty("Spec")
        val spec: SecretSpec? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class SecretSpec(
        @JsonProperty("Name")
        val name: String? = null,
        @JsonProperty("Labels")
        val labels: Map<String, String>? = null,
        @JsonProperty("Data")
        val data: String? = null,
        @JsonProperty("Driver")
        val driver: Driver? = null,
        @JsonProperty("Templating")
        val templating: Driver? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class SecretReference(
        @JsonProperty("File")
        val file: SecretAndConfigReferenceFileTarget? = null,
        @JsonProperty("SecretID")
        val secretID: String? = null,
        @JsonProperty("SecretName")
        val secretName: String? = null

)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class SecretCreateResponse(
        @JsonProperty("ID")
        val id: String? = null
)
package com.swarmbit.docker.api.configs.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.swarmbit.docker.api.common.annotation.DockerRemoteApiMinVersion
import com.swarmbit.docker.api.common.model.Driver
import com.swarmbit.docker.api.common.model.SecretAndConfigReferenceFileTarget
import com.swarmbit.docker.api.common.model.Version

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@DockerRemoteApiMinVersion("v1.30")
data class Config(
    @JsonProperty("ID")
    val id: String? = null,
    @JsonProperty("Version")
    val version: Version? = null,
    @JsonProperty("CreatedAt")
    val createdAt: String? = null,
    @JsonProperty("UpdatedAt")
    val updatedAt: String? = null,
    @JsonProperty("Spec")
    val spec: ConfigSpec? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@DockerRemoteApiMinVersion("v1.30")
data class ConfigSpec(
    @JsonProperty("Name")
    val name: String? = null,
    @JsonProperty("Labels")
    val labels: Map<String, String>? = null,
    @JsonProperty("Data")
    val data: String? = null,
    @DockerRemoteApiMinVersion("v1.37")
    @JsonProperty("Templating")
    val templating: Driver? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ConfigReference(
    @JsonProperty("File")
    val file: SecretAndConfigReferenceFileTarget? = null,
    @DockerRemoteApiMinVersion("v1.40")
    @JsonProperty("Runtime")
    val runtime: ConfigReferenceRuntimeTarget? = null,
    @JsonProperty("ConfigID")
    val configID: String? = null,
    @JsonProperty("ConfigName")
    val configName: String? = null
)

@DockerRemoteApiMinVersion("v1.40")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class ConfigReferenceRuntimeTarget

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ConfigCreateResponse(
    @JsonProperty("ID")
    val id: String? = null
)

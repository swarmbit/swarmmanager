package com.swarmbit.docker.api.nodes.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.swarmbit.docker.api.common.annotation.DockerRemoteApiMinVersion
import com.swarmbit.docker.api.common.model.Resources
import com.swarmbit.docker.api.common.model.TLSInfo
import com.swarmbit.docker.api.common.model.Version

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Node(
    @JsonProperty("ID")
    val id: String? = null,
    @JsonProperty("Version")
    val version: Version? = null,
    @JsonProperty("CreatedAt")
    val createdAt: String? = null,
    @JsonProperty("UpdatedAt")
    val updatedAt: String? = null,
    @JsonProperty("Spec")
    val spec: NodeSpec? = null,
    @JsonProperty("Description")
    val description: NodeDescription? = null,
    @JsonProperty("Status")
    val status: NodeStatus? = null,
    @JsonProperty("ManagerStatus")
    var managerStatus: ManagerStatus? = null,
    @DockerRemoteApiMinVersion("v1.30")
    @JsonProperty("TLSInfo")
    val tlsInfo: TLSInfo? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class NodeSpec(
    @JsonProperty("Name")
    val name: String? = null,
    @JsonProperty("Labels")
    val labels: Map<String, String>? = null,
    @JsonProperty("RoleAuthorities")
    val role: String? = null,
    @JsonProperty("Availability")
    val availability: String? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class NodeDescription(
    @JsonProperty("Hostname")
    val hostname: String? = null,
    @JsonProperty("Platform")
    val platform: Platform? = null,
    @JsonProperty("Resources")
    val resources: Resources? = null,
    @JsonProperty("Engine")
    val engine: EngineDescription? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class NodeStatus(
    @JsonProperty("State")
    val state: String? = null,
    @JsonProperty("Message")
    val message: String? = null,
    @JsonProperty("Addr")
    val addr: String? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ManagerStatus(
    @JsonProperty("Leader")
    val isLeader: Boolean? = null,
    @JsonProperty("Reachability")
    val reachability: String? = null,
    @JsonProperty("Addr")
    val addr: String? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Platform(
    @JsonProperty("Architecture")
    val architecture: String? = null,
    @JsonProperty("OS")
    val os: String? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class EngineDescription(
    @JsonProperty("EngineVersion")
    val engineVersion: String? = null,
    @JsonProperty("Labels")
    val labels: Map<String, String>? = null,
    @JsonProperty("Plugins")
    val plugins: List<PluginDescription>? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class PluginDescription(
    @JsonProperty("Type")
    val type: String? = null,
    @JsonProperty("Name")
    val name: String? = null
)

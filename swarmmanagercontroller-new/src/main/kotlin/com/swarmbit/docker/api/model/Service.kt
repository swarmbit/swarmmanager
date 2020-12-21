package com.swarmbit.docker.api.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.swarmbit.docker.api.model.common.Endpoint
import com.swarmbit.docker.api.model.common.EndpointSpec
import com.swarmbit.docker.api.model.common.Version

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Service(
        @JsonProperty("ID")
        val id: String? = null,
        @JsonProperty("Version")
        val version: Version? = null,
        @JsonProperty("CreatedAt")
        val createdAt: String? = null,
        @JsonProperty("UpdatedAt")
        val updatedAt: String? = null,
        @JsonProperty("Spec")
        val spec: ServiceSpec? = null,
        @JsonProperty("PreviousSpec")
        val previousSpec: ServiceSpec? = null,
        @JsonProperty("Endpoint")
        val endpoint: Endpoint? = null,
        @JsonProperty("UpdateStatus")
        val updateStatus: UpdateStatus? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class UpdateStatus(
        @JsonProperty("State")
        val state: String? = null,
        @JsonProperty("StartedAt")
        val startedAt: String? = null,
        @JsonProperty("CompletedAt")
        val completedAt: String? = null,
        @JsonProperty("Message")
        val message: String? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ServiceSpec(
        @JsonProperty("Name")
        val name: String? = null,
        @JsonProperty("Labels")
        val labels: Map<String, String>? = null,
        @JsonProperty("TaskTemplate")
        val taskTemplate: TaskSpec? = null,
        @JsonProperty("Mode")
        val mode: ServiceMode? = null,
        @JsonProperty("UpdateConfig")
        val updateConfig: UpdateConfig? = null,
        @JsonProperty("RollbackConfig")
        val rollbackConfig: UpdateConfig? = null,
        @JsonProperty("Networks")
        val networks: List<NetworkAttachmentConfig>? = null,
        @JsonProperty("EndpointSpec")
        val endpointSpec: EndpointSpec? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class UpdateConfig(
        @JsonProperty("Parallelism")
        val parallelism: Long? = null,
        @JsonProperty("Delay")
        val delay: Long? = null,
        @JsonProperty("FailureAction")
        val failureAction: String? = null,
        @JsonProperty("Monitor")
        val monitor: Long? = null,
        @JsonProperty("Order")
        val order: String? = null,
        @JsonProperty("MaxFailureRatio")
        val maxFailureRatio: Double? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ServiceMode(
        @JsonProperty("Replicated")
        val replicated: ReplicatedService? = null,
        @JsonProperty("Global")
        val global: GlobalService? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class GlobalService

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ReplicatedService(
        @JsonProperty("Replicas")
        val replicas: Long? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ServiceGeneralResponse(
        @JsonProperty("ID")
        val id: String? = null,
        @JsonProperty("Warning")
        val warning: String? = null
)
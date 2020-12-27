package com.swarmbit.docker.api.services.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.swarmbit.docker.api.common.annotation.DockerRemoteApiMinVersion
import com.swarmbit.docker.api.common.model.Endpoint
import com.swarmbit.docker.api.common.model.EndpointSpec
import com.swarmbit.docker.api.common.model.Version
import com.swarmbit.docker.api.tasks.model.TaskSpec
import com.swarmbit.docker.api.networks.model.NetworkAttachmentConfig

/*
 * v1.41
 * GET /services now accepts query parameter status. When set true, services returned will include ServiceStatus, which provides Desired, Running, and Completed task counts for the service.
 */
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
        val updateStatus: UpdateStatus? = null,
        @DockerRemoteApiMinVersion("v1.41")
        @JsonProperty("JobStatus")
        val jobStatus: JobStatus? = null,
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
        val global: GlobalService? = null,
        @DockerRemoteApiMinVersion("v1.41")
        @JsonProperty("ReplicatedJob")
        val replicatedJob: ReplicatedJob? = null,
        @DockerRemoteApiMinVersion("v1.41")
        @JsonProperty("GlobalJob")
        val globalJob: GlobalJob? = null
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

@DockerRemoteApiMinVersion("v1.41")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class GlobalJob

@DockerRemoteApiMinVersion("v1.41")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ReplicatedJob(
        @JsonProperty("MaxConcurrent")
        val maxConcurrent: Long? = null,
        @JsonProperty("TotalCompletions")
        val totalCompletions: Long? = null
)

@DockerRemoteApiMinVersion("v1.41")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class JobStatus(
        @JsonProperty("JobIteration")
        val jobIteration: Version? = null,
        @JsonProperty("LastExecution")
        val LastExecution: String? = null
)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ServiceGeneralResponse(
        @JsonProperty("ID")
        val id: String? = null,
        @JsonProperty("Warning")
        val warning: String? = null
)
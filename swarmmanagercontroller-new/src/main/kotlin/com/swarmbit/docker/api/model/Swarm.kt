package com.swarmbit.docker.api.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.swarmbit.docker.api.annotation.DockerRemoteApiMinVersion
import com.swarmbit.docker.api.model.common.Driver
import com.swarmbit.docker.api.model.common.TLSInfo
import com.swarmbit.docker.api.model.common.Version

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Swarm(
        @JsonProperty("ClusterInfo")
        val clusterInfo: ClusterInfo? = null,
        @JsonProperty("JoinTokens")
        val joinTokens: JoinTokens? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ClusterInfo(
        @JsonProperty("ID")
        val id: String? = null,
        @JsonProperty("Version")
        val version: Version? = null,
        @JsonProperty("CreatedAt")
        val createdAt: String? = null,
        @JsonProperty("UpdatedAt")
        val updatedAt: String? = null,
        @JsonProperty("Spec")
        val spec: SwarmSpec? = null,
        @JsonProperty("RootRotationInProgress")
        val isRootRotationInProgress: Boolean? = null,
        @JsonProperty("TLSInfo")
        val tlsInfo: TLSInfo? = null,
        @DockerRemoteApiMinVersion("v1.40")
        @JsonProperty("DataPathPort")
        val dataPathPort: Int? = null,
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class SwarmSpec(
        @JsonProperty("Name")
        val name: String? = null,
        @JsonProperty("Labels")
        val labels: Map<String, String>? = null,
        @JsonProperty("Orchestration")
        val orchestration: OrchestrationConfig? = null,
        @JsonProperty("Raft")
        val raft: RaftConfig? = null,
        @JsonProperty("Dispatcher")
        val dispatcher: DispatcherConfig? = null,
        @JsonProperty("CAConfig")
        val caConfig: CAConfig? = null,
        @JsonProperty("TasksDefaults")
        val tasksDefaults: TasksDefaults? = null,
        @JsonProperty("EncryptionConfig")
        val encryptionConfig: EncryptionConfig? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class JoinTokens(
        @JsonProperty("Worker")
        val worker: String? = null,
        @JsonProperty("Manager")
        val manager: String? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class OrchestrationConfig(
        @JsonProperty("TaskHistoryRetentionLimit")
        val taskHistoryRetentionLimit: Long? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class RaftConfig(
        @JsonProperty("SnapshotInterval")
        val snapshotInterval: Long? = null,
        @JsonProperty("KeepOldSnapshots")
        val keepOldSnapshots: Long? = null,
        @JsonProperty("LogEntriesForSlowFollowers")
        val logEntriesForSlowFollowers: Long? = null,
        @JsonProperty("ElectionTick")
        val electionTick: Int? = null,
        @JsonProperty("HeartbeatTick")
        val heartbeatTick: Int? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class DispatcherConfig(
        @JsonProperty("HeartbeatPeriod")
        val heartbeatPeriod: Long? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class TasksDefaults(
        @JsonProperty("LogDriver")
        val logDriver: Driver? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class EncryptionConfig(
        @JsonProperty("AutoLockManagers")
        val isAutoLockManagers: Boolean? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class CAConfig(
        @JsonProperty("NodeCertExpiry")
        val nodeCertExpiry: Long? = null,
        @JsonProperty("ExternalCAs")
        val externalCAs: List<ExternalCA>? = null,
        @JsonProperty("SigningCACert")
        val signingCACert: String? = null,
        @JsonProperty("SigningCAKey")
        val signingCAKey: String? = null,
        @JsonProperty("ForceRotate")
        val forceRotate: Int? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ExternalCA(
        @JsonProperty("Protocol")
        val protocol: String? = null,
        @JsonProperty("URL")
        val url: String? = null,
        @JsonProperty("Options")
        val options: Map<String, String>? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class UnlockKey(
        @JsonProperty("UnlockKey")
        val unlockKey: String? = null
)


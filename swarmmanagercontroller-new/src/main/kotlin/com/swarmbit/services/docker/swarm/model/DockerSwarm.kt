package com.swarmbit.services.docker.swarm.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.swarmbit.docker.api.common.formatter.dockerToEpochMillis
import com.swarmbit.docker.api.swarm.model.CAConfig
import com.swarmbit.docker.api.swarm.model.DispatcherConfig
import com.swarmbit.docker.api.swarm.model.EncryptionConfig
import com.swarmbit.docker.api.swarm.model.ExternalCA
import com.swarmbit.docker.api.swarm.model.OrchestrationConfig
import com.swarmbit.docker.api.swarm.model.RaftConfig
import com.swarmbit.docker.api.swarm.model.Swarm
import com.swarmbit.docker.api.swarm.model.SwarmSpec

@JsonInclude(JsonInclude.Include.NON_NULL)
data class DockerSwarm(
    val createdAt: Long,
    val updatedAt: Long,
    val autoLock : Boolean,
    val certExpiry : Long,
    val dispatcherHeartBeat : Long,
    val maxSnapshots : Long,
    val snapshotInterval : Long,
    val taskHistoryLimit : Long,
    val workerToken : String,
    val managerToken : String,
    val rotateInProgress : Boolean,
    val externalCAs: List<DockerExternalCA>
)

data class DockerSwarmUpdate(
    val autoLock : Boolean?,
    val certExpiry : Long?,
    val dispatcherHeartBeat : Long?,
    val maxSnapshots : Long?,
    val snapshotInterval : Long?,
    val taskHistoryLimit : Long?,
    val externalCAs: List<DockerExternalCA>?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class DockerExternalCA(
    val protocol: String = "cfssl",
    val url: String,
    val options: Map<String, String>
)

fun Swarm.toDockerSwarm(): DockerSwarm =
    DockerSwarm(
        createdAt = createdAt?.dockerToEpochMillis() ?: 0L,
        updatedAt = updatedAt?.dockerToEpochMillis() ?: 0L,
        autoLock = spec?.encryptionConfig?.isAutoLockManagers ?: false,
        certExpiry = spec?.caConfig?.nodeCertExpiry ?: 0L,
        dispatcherHeartBeat = spec?.dispatcher?.heartbeatPeriod ?: 0L,
        taskHistoryLimit = spec?.orchestration?.taskHistoryRetentionLimit ?: 0L,
        snapshotInterval = spec?.raft?.snapshotInterval ?: 0L,
        maxSnapshots = spec?.raft?.keepOldSnapshots ?: 0L,
        externalCAs = spec?.caConfig?.externalCAs?.map { it.toDockerExternalCA() }.orEmpty(),
        managerToken = joinTokens?.manager.orEmpty(),
        workerToken = joinTokens?.worker.orEmpty(),
        rotateInProgress = isRootRotationInProgress ?: false
    )

fun ExternalCA.toDockerExternalCA(): DockerExternalCA =
    DockerExternalCA(
        protocol = protocol ?: "cfssl",
        url = url.orEmpty(),
        options = options.orEmpty()
    )

fun DockerExternalCA.toExternalCA(): ExternalCA =
    ExternalCA(
        protocol = protocol,
        url = url,
        options = options
    )

fun DockerSwarmUpdate.toSwarmSpec(spec: SwarmSpec): SwarmSpec =
    spec.copy(
        encryptionConfig = spec.encryptionConfig?.copy(
            isAutoLockManagers = autoLock ?: spec.encryptionConfig.isAutoLockManagers
        ) ?: EncryptionConfig(isAutoLockManagers = autoLock),
        caConfig = spec.caConfig?.copy(
            nodeCertExpiry = certExpiry ?: spec.caConfig.nodeCertExpiry,
            externalCAs = externalCAs?.map { it.toExternalCA() } ?: spec.caConfig.externalCAs
        ) ?: CAConfig(
            nodeCertExpiry = certExpiry,
            externalCAs =  externalCAs?.map { it.toExternalCA() }
        ),
        dispatcher = spec.dispatcher?.copy(
            heartbeatPeriod = dispatcherHeartBeat ?: spec.dispatcher.heartbeatPeriod
        ) ?: DispatcherConfig(
            heartbeatPeriod = dispatcherHeartBeat
        ),
        raft = spec.raft?.copy(
            snapshotInterval = snapshotInterval ?: spec.raft.snapshotInterval,
            keepOldSnapshots = maxSnapshots ?: spec.raft.keepOldSnapshots
        ) ?: RaftConfig(
            snapshotInterval = snapshotInterval,
            keepOldSnapshots = maxSnapshots
        ),
        orchestration = spec.orchestration?.copy(
            taskHistoryRetentionLimit = taskHistoryLimit ?: spec.orchestration.taskHistoryRetentionLimit
        ) ?: OrchestrationConfig(
            taskHistoryRetentionLimit = taskHistoryLimit
        )
    )
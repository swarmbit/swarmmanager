package com.swarmbit.services.docker.service.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.swarmbit.docker.api.common.config.DockerConfig
import com.swarmbit.docker.api.services.model.Service
import com.swarmbit.docker.api.tasks.model.Mount
import com.swarmbit.services.docker.common.model.DockerPort
import com.swarmbit.services.docker.common.model.Protocol
import com.swarmbit.services.docker.secret.model.DockerSecret

@JsonInclude(JsonInclude.Include.NON_NULL)
data class DockerService(
    val id: String? = null,
    val createdAt: Long? = null,
    val updatedAt: Long? = null,
    val image: String? = null,
    val global: Boolean? = null,
    val name: String? = null,
    val replicas: Long? = null,
    val ports: List<DockerPort>? = null,
    val env: List<String>? = null,
    val configs: List<DockerConfig>? = null,
    val secrets: List<DockerSecret>? = null,
    val labels: Map<String, String>? = null,
    val constraints: List<DockerConstraint>? = null,
    val placementPreferences: Map<String, String>? = null,
    /**
     * Container general
     */
    val readOnly: Boolean? = null,
    val entrypoint: String? = null,
    val containerLabels: Map<String, String>? = null,
    val args: List<String>? = null,
    val groups: List<String>? = null,
    val user: String? = null,
    val workDir: String? = null,
    val stopGracePeriod: Long? = null,
    val stopSignal: String? = null,
    /**
     * Logs
     */
    val logDriver: String? = null,
    val logOptions: Map<String, String>? = null,
    /**
     * Registry
     */
    val isDockerHubRegistry: Boolean? = null,
    val registryName: String? = null,
    val registryUsername: String? = null,
    val registryPassword: String? = null,

    val mounts: List<Mount>? = null,
    val forceUpdate: Boolean? = null,
    /**
     * Network
     */
    val endpointMode: String? = null,
    val networks: List<String>? = null,
    val hostname: String? = null,
    val hosts: List<String>? = null,
    /**
     * Dns
     */
    val dnsServers: MutableList<String>? = null,
    val dnsOptions: List<String>? = null,
    val dnsSearches: List<String>? = null,
    /**
     * Health Check
     */
    val healthCmd: String? = null,
    val healthRetries: Int? = null,
    val healthStartPeriod: Long? = null,
    val healthInterval: Long? = null,
    val healthTimeout: Long? = null,
    val noHealthCheck: Boolean? = null,

    /**
     * Reserve
     */
    val reserveCpu: Long? = null,
    val reserveMemory: Long? = null,
    /**
     * Limits
     */
    val limitCpu: Long? = null,
    val limitMemory: Long? = null,
    /**
     * Restart config
     */
    val restartCondition: String? = null,
    val restartDelay: Long? = null,
    val restartMaxAttempts: Long? = null,
    val restartWindow: Long? = null,
    /**
     * Update config
     */
    val updateDelay: Long? = null,
    val updateFailureAction: String? = null,
    val updateMaxFailureRatio: Double? = null,
    val updateMonitor: Long? = null,
    val updateOrder: String? = null,
    val updateParallelism: Long? = null,
    /**
     * Rollback config
     */
    val rollbackDelay: Long? = null,
    val rollbackFailureAction: String? = null,
    val rollbackMaxFailureRatio: Double? = null,
    val rollbackMonitor: Long? = null,
    val rollbackOrder: String? = null,
    val rollbackParallelism: Long? = null,

    val rollback: Boolean? = null
)

data class DockerServiceSummary(
    val id: String,
    val name: String,
    val runningReplicas: Long,
    val replicas: Long,
    val global: Boolean,
    val image: String,
    val ports: List<DockerPort>
)

data class DockerLogs(
    val logLine: String
)

data class DockerConstraint(
    val name: String? = null,
    val value: String? = null,
    val different: Boolean? = null
)

fun Service.toDockerService(): DockerService = DockerService()

fun Service.toDockerServiceSummary(runningReplicas: Long, replicas: Long, global: Boolean): DockerServiceSummary =
    DockerServiceSummary(
        id = id.orEmpty(),
        name = spec?.name.orEmpty(),
        runningReplicas = runningReplicas,
        replicas = replicas,
        global = global,
        image = spec?.taskTemplate?.containerSpec?.image.orEmpty(),
        ports = spec?.endpointSpec?.ports?.map {
            DockerPort(
                protocol = Protocol.getProtocol(it.protocol.orEmpty()) ?: Protocol.TCP,
                published = it.publishedPort ?: 0,
                target = it.targetPort ?: 0
            )
        } ?: emptyList()
    )
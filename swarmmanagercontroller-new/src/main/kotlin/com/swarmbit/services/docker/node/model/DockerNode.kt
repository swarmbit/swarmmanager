package com.swarmbit.services.docker.node.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.swarmbit.docker.api.networks.model.Network
import com.swarmbit.docker.api.nodes.model.Node
import com.swarmbit.docker.api.nodes.model.NodeSpec
import com.swarmbit.docker.api.nodes.parameters.NodeRole
import com.swarmbit.docker.api.tasks.model.NetworkAttachmentSpec
import com.swarmbit.services.docker.network.model.DockerNetworkCreate

@JsonInclude(JsonInclude.Include.NON_NULL)
data class DockerNode(
    val id: String,
    val hostname: String,
    val addr: String,
    val labels: Map<String, String>,
    val role: String,
    val state: String,
    val availability: String,
    val nanoCPUs: Long,
    val memoryBytes: Long,
    val engineVersion: String,
    val reachability: String,
    val isLeader: Boolean
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class DockerNodeSummary(
    val id: String,
    val hostname: String,
    val status: String,
    val availability: String,
    val isManager: Boolean,
    val isLeader: Boolean,
    val managerReachability: String,
    val numberOfRunningTasks: Int
)

data class DockerNodeUpdate(
    val role: String? = null,
    val availability: String? = null,
    val labels: Map<String, String>? = null,
)

fun Node.toDockerNode(): DockerNode =
    DockerNode(
        id = id.orEmpty(),
        hostname = description?.hostname.orEmpty(),
        addr = status?.addr.orEmpty(),
        labels = spec?.labels.orEmpty(),
        role = spec?.role.orEmpty(),
        state = status?.state.orEmpty(),
        availability = spec?.availability.orEmpty(),
        nanoCPUs = description?.resources?.nanoCPUs ?: 0,
        memoryBytes = description?.resources?.memoryBytes ?: 0,
        engineVersion = description?.engine?.engineVersion.orEmpty(),
        reachability = managerStatus?.reachability.orEmpty(),
        isLeader = managerStatus?.isLeader ?: false
    )

fun Node.toDockerNodeSummary(numberOfRunningTasks: Int): DockerNodeSummary =
    DockerNodeSummary(
        id = id.orEmpty(),
        hostname = description?.hostname.orEmpty(),
        availability = spec?.availability.orEmpty(),
        status = status?.state.orEmpty(),
        isManager = spec?.role.orEmpty() == NodeRole.MANAGER.name.toLowerCase(),
        isLeader = managerStatus?.isLeader ?: false,
        managerReachability = managerStatus?.reachability.orEmpty(),
        numberOfRunningTasks = numberOfRunningTasks
    )

fun DockerNodeUpdate.toNodeSpec(spec: NodeSpec): NodeSpec =
    spec.copy(availability = availability, role = role, labels = labels)
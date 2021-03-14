package com.swarmbit.services.docker.node

import com.swarmbit.docker.api.nodes.NodesApi
import com.swarmbit.docker.api.nodes.model.NodeSpec
import com.swarmbit.docker.api.nodes.parameters.NodesDeleteParameters
import com.swarmbit.docker.api.nodes.parameters.NodesUpdateParameters
import com.swarmbit.docker.api.tasks.TasksApi
import com.swarmbit.docker.api.tasks.parameters.TasksFilters
import com.swarmbit.docker.api.tasks.parameters.TasksListParameters
import com.swarmbit.services.docker.node.model.DockerNode
import com.swarmbit.services.docker.node.model.DockerNodeSummary
import com.swarmbit.services.docker.node.model.DockerNodeUpdate
import com.swarmbit.services.docker.node.model.toDockerNode
import com.swarmbit.services.docker.node.model.toDockerNodeSummary
import com.swarmbit.services.docker.node.model.toNodeSpec
import io.micronaut.context.annotation.Context

@Context
class DockerNodeServiceImpl(
    private val nodesApi: NodesApi,
    private val tasksApi: TasksApi
) : DockerNodeService {

    override fun ls(swarmId: String): List<DockerNodeSummary> {
        val tasks = tasksApi.listTasks(
            swarmId,
            TasksListParameters()
                .setFilters(
                    TasksFilters().setDesiredState(TasksFilters.RUNNING_STATE)
                )
        )
        return nodesApi.listNodes(swarmId).map {
            val numberOfRunningReplicas = tasks.filter { task ->
                task.status?.taskState == TasksFilters.RUNNING_STATE &&
                    task.nodeId == it.id
            }.size
            it.toDockerNodeSummary(numberOfRunningReplicas)
        }
    }

    override fun inspect(swarmId: String, nodeId: String): DockerNode =
        nodesApi.inspectNode(swarmId, nodeId).toDockerNode()

    override fun update(swarmId: String, nodeId: String, dockerNodeUpdate: DockerNodeUpdate) {
        nodesApi.inspectNode(swarmId, nodeId).let {
            nodesApi.updateNode(
                swarmId,
                nodeId,
                NodesUpdateParameters()
                    .setVersionQueryParam(it.version?.index ?: 0 + 1L)
                    .setNode(dockerNodeUpdate.toNodeSpec(it.spec ?: NodeSpec()))
            )
        }
    }

    override fun rm(swarmId: String, nodeId: String, force: Boolean) =
        nodesApi.deleteNode(swarmId, nodeId, NodesDeleteParameters().setForceQueryParam(force))
}

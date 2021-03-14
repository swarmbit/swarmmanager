package com.swarmbit.services.docker.service

import com.swarmbit.docker.api.services.ServicesApi
import com.swarmbit.docker.api.services.parameters.ServicesListParameters
import com.swarmbit.docker.api.tasks.TasksApi
import com.swarmbit.docker.api.tasks.parameters.TasksFilters
import com.swarmbit.docker.api.tasks.parameters.TasksListParameters
import com.swarmbit.services.docker.common.model.DockerState
import com.swarmbit.services.docker.service.model.DockerLogs
import com.swarmbit.services.docker.service.model.DockerService
import com.swarmbit.services.docker.service.model.DockerServiceSummary
import com.swarmbit.services.docker.service.model.toDockerServiceSummary
import io.micronaut.context.annotation.Context

@Context
class DockerServiceServiceImpl(
    private val servicesApi: ServicesApi,
    private val tasksApi: TasksApi
) : DockerServiceService {

    override fun create(swarmId: String, dockerServiceCreate: DockerService): DockerService {
        TODO("Not yet implemented")
    }

    override fun rm(swarmId: String, serviceId: String) {
        TODO("Not yet implemented")
    }

    override fun ls(swarmId: String): List<DockerServiceSummary> {
        return servicesApi
            .listServices(swarmId = swarmId, parameters = ServicesListParameters())
            .let { services ->
                val tasks = tasksApi.listTasks(
                    swarmId,
                    TasksListParameters().setFilters(TasksFilters().setDesiredState(TasksFilters.RUNNING_STATE))
                )
                services.map { service ->
                    val serviceTasks = tasks.filter { it.serviceId == service.id }
                    var global = false
                    val replicas = if (service.spec?.mode?.replicated != null) {
                        service.spec.mode.replicated.replicas ?: 0L
                    } else {
                        global = true
                        tasks.groupBy { it.nodeId }.size.toLong()
                    }
                    val runningReplicas = serviceTasks.filter { it.status?.taskState == TasksFilters.RUNNING_STATE }.size.toLong()
                    service.toDockerServiceSummary(replicas = replicas, global = global, runningReplicas = runningReplicas)
                }
            }
    }

    override fun logs(swarmId: String, serviceId: String): List<DockerLogs> {
        TODO("Not yet implemented")
    }

    override fun ps(swarmId: String, serviceId: String): List<DockerState> {
        TODO("Not yet implemented")
    }

    override fun inspect(swarmId: String, serviceId: String): DockerService {
        TODO("Not yet implemented")
    }

    override fun update(swarmId: String, serviceId: String, dockerServiceUpdate: DockerService) {
        TODO("Not yet implemented")
    }
}

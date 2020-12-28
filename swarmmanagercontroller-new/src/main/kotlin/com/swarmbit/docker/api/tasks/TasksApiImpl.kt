package com.swarmbit.docker.api.tasks

import com.swarmbit.docker.api.common.AbstractApiImpl
import com.swarmbit.docker.api.common.client.DockerWebClient
import com.swarmbit.docker.api.common.rest.model.ResponseType
import com.swarmbit.docker.api.services.parameters.ServicesLogsParameters
import com.swarmbit.docker.api.tasks.model.Task
import com.swarmbit.docker.api.tasks.parameters.TasksListParameters
import io.micronaut.context.annotation.Context

@Context
class TasksApiImpl(dockerWebClient: DockerWebClient) : AbstractApiImpl(dockerWebClient), TasksApi {

    companion object {
        private const val TASKS_PATH = "/tasks"
        private const val LOGS_PATH = "/logs"
    }

    override fun listTasks(swarmId: String, parameters: TasksListParameters): List<Task> {
        return listObjects(TASKS_PATH, swarmId, object : ResponseType<List<Task>>() {}, parameters)
    }

    override fun inspectTask(swarmId: String, id: String): Task {
        return inspectObject(TASKS_PATH, swarmId, object : ResponseType<Task>() {}, id)
            ?: throw IllegalArgumentException("No task found for swarmId ($swarmId) and taskId (${id})")
    }

    override fun getTaskLogs(swarmId: String, id: String, parameters: ServicesLogsParameters): ByteArray {
        return getObjectLogs("$TASKS_PATH/$id$LOGS_PATH", swarmId, parameters) ?: ByteArray(0)
    }

}
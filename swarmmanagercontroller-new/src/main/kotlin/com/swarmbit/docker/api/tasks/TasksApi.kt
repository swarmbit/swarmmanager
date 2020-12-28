package com.swarmbit.docker.api.tasks

import com.swarmbit.docker.api.services.parameters.ServicesLogsParameters
import com.swarmbit.docker.api.tasks.model.Task
import com.swarmbit.docker.api.tasks.parameters.TasksListParameters

interface TasksApi {
    fun listTasks(swarmId: String, parameters: TasksListParameters): List<Task>
    fun inspectTask(swarmId: String, id: String): Task
    fun getTaskLogs(swarmId: String, id: String, parameters: ServicesLogsParameters): ByteArray
}

package com.swarmbit.rest

import com.swarmbit.docker.api.tasks.TasksApi
import com.swarmbit.docker.api.tasks.model.Task
import com.swarmbit.docker.api.tasks.parameters.TasksListParameters
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("tasks")
class TasksController(private val tasksApi: TasksApi) {

    @Get
    fun getTasks(): List<Task> {
        return tasksApi.listTasks("swarm", TasksListParameters())
    }

}
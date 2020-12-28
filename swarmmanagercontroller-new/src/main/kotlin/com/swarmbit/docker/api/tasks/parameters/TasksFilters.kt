package com.swarmbit.docker.api.tasks.parameters

import com.swarmbit.docker.api.common.AbstractFilters

class TasksFilters : AbstractFilters() {

    companion object {
        const val RUNNING_STATE = "running"
        const val SHUTDOWN_STATE = "shutdown"
        const val ACCEPTED_STATE = "accepted"
    }

    fun setDesiredState(desiredState: String): TasksFilters {
        addFilter("desired-state", desiredState)
        return this
    }

    fun setId(id: String): TasksFilters {
        addFilter("id", id)
        return this
    }

    fun setLabel(label: String): TasksFilters {
        addFilter("label", label)
        return this
    }

    fun setName(name: String): TasksFilters {
        addFilter("name", name)
        return this
    }

    fun setNode(node: String): TasksFilters {
        addFilter("node", node)
        return this
    }

    fun setService(service: String): TasksFilters {
        addFilter("service", service)
        return this
    }
}

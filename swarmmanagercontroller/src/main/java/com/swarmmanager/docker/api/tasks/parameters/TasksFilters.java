package com.swarmmanager.docker.api.tasks.parameters;

import com.swarmmanager.docker.api.common.AbstractFilters;
import com.swarmmanager.docker.api.common.Filters;

public class TasksFilters extends AbstractFilters implements Filters {

    public static final String RUNNING_STATE = "running";

    public static final String SHUTDOWN_STATE = "shutdown";

    public static final String ACCEPTED_STATE = "accepted";

    public TasksFilters setDesiredState(String desiredState) {
        addFilter("desired-state", desiredState);
        return this;
    }

    public TasksFilters setId(String id) {
        addFilter("id", id);
        return this;
    }

    public TasksFilters setLabel(String label) {
        addFilter("label", label);
        return this;
    }

    public TasksFilters setName(String name) {
        addFilter("name", name);
        return this;
    }

    public TasksFilters setNode(String node) {
        addFilter("node", node);
        return this;
    }

    public TasksFilters setService(String service) {
        addFilter("service", service);
        return this;
    }

}

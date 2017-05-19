package com.swarmmanager.docker.api.tasks.parameters;

import com.swarmmanager.docker.api.common.annotation.DockerFilterParam;

public class TasksFilters {

    public static final String RUNNING_STATE = "running";

    public static final String SHUTDOWN_STATE = "shutdown";

    public static final String ACCEPTED_STATE = "accepted";

    @DockerFilterParam(name = "desired-state")
    private String desiredState;

    @DockerFilterParam
    private String id;

    @DockerFilterParam
    private String label;

    @DockerFilterParam
    private String name;

    @DockerFilterParam
    private String node;

    @DockerFilterParam
    private String service;

    public String getDesiredState() {
        return desiredState;
    }

    public void setDesiredState(String desiredState) {
        this.desiredState = desiredState;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}

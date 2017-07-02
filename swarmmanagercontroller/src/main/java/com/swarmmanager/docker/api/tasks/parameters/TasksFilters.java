package com.swarmmanager.docker.api.tasks.parameters;

import com.swarmmanager.docker.api.common.FilterParameter;
import com.swarmmanager.docker.api.common.FilterParameterValue;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

public class TasksFilters {

    public static final String RUNNING_STATE = "running";

    public static final String SHUTDOWN_STATE = "shutdown";

    public static final String ACCEPTED_STATE = "accepted";

    private FilterParameter desiredState;

    private FilterParameter id;

    private FilterParameter label;

    private FilterParameter name;

    private FilterParameter node;

    private FilterParameter service;

    public TasksFilters setDesiredState(String desiredState) {
        this.desiredState = new FilterParameter("desired-state").addValue(new FilterParameterValue(desiredState));
        return this;
    }

    public TasksFilters setId(String id) {
        this.id = new FilterParameter("id").addValue(new FilterParameterValue(id));
        return this;
    }

    public TasksFilters setLabel(String label) {
        this.label = new FilterParameter("label").addValue(new FilterParameterValue(label));
        return this;
    }

    public TasksFilters setName(String name) {
        this.name = new FilterParameter("name").addValue(new FilterParameterValue(name));
        return this;
    }

    public TasksFilters setNode(String node) {
        this.node = new FilterParameter("node").addValue(new FilterParameterValue(node));
        return this;
    }

    public TasksFilters setService(String service) {
        this.service = new FilterParameter("service").addValue(new FilterParameterValue(service));
        return this;
    }

    public String getJson() {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        if (desiredState != null) {
            desiredState.addFilterJson(jsonObjectBuilder);
        }
        if (id != null) {
            id.addFilterJson(jsonObjectBuilder);
        }
        if (label != null) {
            label.addFilterJson(jsonObjectBuilder);
        }
        if (name != null) {
            name.addFilterJson(jsonObjectBuilder);
        }
        if (node != null) {
            node.addFilterJson(jsonObjectBuilder);
        }
        if (service != null) {
            service.addFilterJson(jsonObjectBuilder);
        }
        return jsonObjectBuilder.build().toString();
    }
}

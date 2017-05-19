package com.swarmmanager.docker.api.services.parameters;

import com.swarmmanager.docker.api.common.annotation.DockerFilterParam;

public class ServicesFilters {

    @DockerFilterParam
    private String id;

    @DockerFilterParam
    private String label;

    @DockerFilterParam
    private String name;

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
}

package com.swarmmanager.docker.api.secrets.parameters;

import com.swarmmanager.docker.api.common.annotation.DockerFilterParam;

public class SecretsFilters {

    @DockerFilterParam
    private String names;

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }
}

package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swarmmanager.docker.api.common.json.DockerRemoteApiJson;
import com.swarmmanager.docker.api.common.annotation.DockerRemoteApiVersions;

@JsonIgnoreProperties(ignoreUnknown = true)
@DockerRemoteApiVersions(versions = "v1.28")
public class TasksDefaultsJson implements DockerRemoteApiJson {

    @JsonProperty("LogDriver")
    private DriverJson logDriver;

    public DriverJson getLogDriver() {
        return logDriver;
    }

    public void setLogDriver(DriverJson logDriver) {
        this.logDriver = logDriver;
    }
}

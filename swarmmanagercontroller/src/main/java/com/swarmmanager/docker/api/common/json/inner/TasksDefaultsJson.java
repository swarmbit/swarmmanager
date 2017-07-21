package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TasksDefaultsJson {

    @JsonProperty("LogDriver")
    private DriverJson logDriver;

    public DriverJson getLogDriver() {
        return logDriver;
    }

    public void setLogDriver(DriverJson logDriver) {
        this.logDriver = logDriver;
    }
}

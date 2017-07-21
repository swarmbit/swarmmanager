package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DispatcherConfigJson {

    @JsonProperty("HeartbeatPeriod")
    private String heartbeatPeriod;

    public String getHeartbeatPeriod() {
        return heartbeatPeriod;
    }

    public void setHeartbeatPeriod(String heartbeatPeriod) {
        this.heartbeatPeriod = heartbeatPeriod;
    }
}

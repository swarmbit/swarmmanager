package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DispatcherConfigJson {

    @JsonProperty("HeartbeatPeriod")
    private Long heartbeatPeriod;

    public Long getHeartbeatPeriod() {
        return heartbeatPeriod;
    }

    public void setHeartbeatPeriod(Long heartbeatPeriod) {
        this.heartbeatPeriod = heartbeatPeriod;
    }
}

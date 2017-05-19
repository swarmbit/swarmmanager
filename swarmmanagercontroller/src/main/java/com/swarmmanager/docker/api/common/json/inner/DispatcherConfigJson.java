package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swarmmanager.docker.api.common.annotation.DockerRemoteApiVersions;
import com.swarmmanager.docker.api.common.json.DockerRemoteApiJson;

@JsonIgnoreProperties(ignoreUnknown = true)
@DockerRemoteApiVersions(versions = "v1.28")
public class DispatcherConfigJson implements DockerRemoteApiJson {

    @JsonProperty("HeartbeatPeriod")
    private String heartbeatPeriod;

    public String getHeartbeatPeriod() {
        return heartbeatPeriod;
    }

    public void setHeartbeatPeriod(String heartbeatPeriod) {
        this.heartbeatPeriod = heartbeatPeriod;
    }
}

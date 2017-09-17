package com.swarmmanager.docker.api.common.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swarmmanager.docker.api.common.annotation.DockerRemoteApiMinVersion;

@JsonIgnoreProperties(ignoreUnknown = true)
@DockerRemoteApiMinVersion("v1.25")
public class UnlockKeyJson {

    @JsonProperty("UnlockKey")
    private String unlockKey;

    public String getUnlockKey() {
        return unlockKey;
    }

    public void setUnlockKey(String unlockKey) {
        this.unlockKey = unlockKey;
    }
}

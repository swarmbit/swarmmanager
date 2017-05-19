package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swarmmanager.docker.api.common.annotation.DockerRemoteApiVersions;
import com.swarmmanager.docker.api.common.json.DockerRemoteApiJson;

@JsonIgnoreProperties(ignoreUnknown = true)
@DockerRemoteApiVersions(versions = "v1.28")
public class EncryptionConfigJson implements DockerRemoteApiJson {

    @JsonProperty("AutoLockManagers")
    private boolean autoLockManagers;

    public boolean isAutoLockManagers() {
        return autoLockManagers;
    }

    public void setAutoLockManagers(boolean autoLockManagers) {
        this.autoLockManagers = autoLockManagers;
    }
}

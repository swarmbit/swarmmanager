package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EncryptionConfigJson {

    @JsonProperty("AutoLockManagers")
    private boolean autoLockManagers;

    public boolean isAutoLockManagers() {
        return autoLockManagers;
    }

    public void setAutoLockManagers(boolean autoLockManagers) {
        this.autoLockManagers = autoLockManagers;
    }
}

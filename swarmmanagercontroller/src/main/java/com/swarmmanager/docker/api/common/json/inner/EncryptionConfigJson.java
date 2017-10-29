package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EncryptionConfigJson {

    @JsonProperty("AutoLockManagers")
    private Boolean autoLockManagers;

    public Boolean isAutoLockManagers() {
        return autoLockManagers;
    }

    public void setAutoLockManagers(Boolean autoLockManagers) {
        this.autoLockManagers = autoLockManagers;
    }
}

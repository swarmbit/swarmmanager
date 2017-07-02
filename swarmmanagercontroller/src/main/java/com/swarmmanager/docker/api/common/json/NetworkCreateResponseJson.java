package com.swarmmanager.docker.api.common.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NetworkCreateResponseJson {

    @JsonProperty("Id")
    private String id;

    @JsonProperty("Warning")
    private String warning;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }
}

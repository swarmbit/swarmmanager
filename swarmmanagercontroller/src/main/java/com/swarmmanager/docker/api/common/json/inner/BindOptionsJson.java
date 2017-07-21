package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BindOptionsJson {

    @JsonProperty("Propagation")
    private String propagation;

    public String getPropagation() {
        return propagation;
    }

    public void setPropagation(String propagation) {
        this.propagation = propagation;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BindOptionsJson{");
        sb.append("propagation='").append(propagation).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReplicatedServiceJson {

    @JsonProperty("Replicas")
    private long replicas;

    public long getReplicas() {
        return replicas;
    }

    public void setReplicas(long replicas) {
        this.replicas = replicas;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ReplicatedServiceJson{");
        sb.append("replicas=").append(replicas);
        sb.append('}');
        return sb.toString();
    }
}

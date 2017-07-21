package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VersionJson {

    @JsonProperty("Index")
    private long index;

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VersionJson{");
        sb.append("index=").append(index);
        sb.append('}');
        return sb.toString();
    }
}

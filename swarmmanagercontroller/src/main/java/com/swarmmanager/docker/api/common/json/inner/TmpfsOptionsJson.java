package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TmpfsOptionsJson {

    @JsonProperty("SizeBytes")
    private long sizeBytes;

    @JsonProperty("Mode")
    private int mode;

    public long getSizeBytes() {
        return sizeBytes;
    }

    public void setSizeBytes(long sizeBytes) {
        this.sizeBytes = sizeBytes;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TmpfsOptionsJson{");
        sb.append("sizeBytes=").append(sizeBytes);
        sb.append(", mode=").append(mode);
        sb.append('}');
        return sb.toString();
    }
}

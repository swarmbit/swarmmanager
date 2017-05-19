package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.swarmmanager.docker.api.common.json.DockerRemoteApiJson;
import com.swarmmanager.docker.api.common.annotation.DockerRemoteApiVersions;

@DockerRemoteApiVersions(versions = "v1.28")
public class ResourcesJson implements DockerRemoteApiJson {

    @JsonProperty("NanoCPUs")
    private long nanoCPUs;

    @JsonProperty("MemoryBytes")
    private long memoryBytes;

    public long getNanoCPUs() {
        return nanoCPUs;
    }

    public void setNanoCPUs(long nanoCPUs) {
        this.nanoCPUs = nanoCPUs;
    }

    public long getMemoryBytes() {
        return memoryBytes;
    }

    public void setMemoryBytes(long memoryBytes) {
        this.memoryBytes = memoryBytes;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ResourcesJson{");
        sb.append("nanoCPUs=").append(nanoCPUs);
        sb.append(", memoryBytes=").append(memoryBytes);
        sb.append('}');
        return sb.toString();
    }
}

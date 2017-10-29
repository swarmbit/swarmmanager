package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResourcesJson {

    @JsonProperty("NanoCPUs")
    private Long nanoCPUs;

    @JsonProperty("MemoryBytes")
    private Long memoryBytes;

    public Long getNanoCPUs() {
        return nanoCPUs;
    }

    public void setNanoCPUs(Long nanoCPUs) {
        this.nanoCPUs = nanoCPUs;
    }

    public Long getMemoryBytes() {
        return memoryBytes;
    }

    public void setMemoryBytes(Long memoryBytes) {
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

package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RestartPolicyJson {

    @JsonProperty("Condition")
    private String condition;

    @JsonProperty("Delay")
    private String delay;

    @JsonProperty("MaxAttempts")
    private long maxAttempts;

    @JsonProperty("Window")
    private String windows;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public long getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(long maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public String getWindows() {
        return windows;
    }

    public void setWindows(String windows) {
        this.windows = windows;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RestartPolicyJson{");
        sb.append("condition=").append(condition);
        sb.append(", delay=").append(delay);
        sb.append(", maxAttempts=").append(maxAttempts);
        sb.append(", windows=").append(windows);
        sb.append('}');
        return sb.toString();
    }
}

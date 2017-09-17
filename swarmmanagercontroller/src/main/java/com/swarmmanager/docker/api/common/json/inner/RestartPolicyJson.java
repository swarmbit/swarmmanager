package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RestartPolicyJson {

    @JsonProperty("Condition")
    private String condition;

    @JsonProperty("Delay")
    private long delay;

    @JsonProperty("MaxAttempts")
    private long maxAttempts;

    @JsonProperty("Window")
    private long window;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public long getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(long maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public long getWindow() {
        return window;
    }

    public void setWindow(long windows) {
        this.window = window;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RestartPolicyJson{");
        sb.append("condition=").append(condition);
        sb.append(", delay=").append(delay);
        sb.append(", maxAttempts=").append(maxAttempts);
        sb.append(", window=").append(window);
        sb.append('}');
        return sb.toString();
    }
}

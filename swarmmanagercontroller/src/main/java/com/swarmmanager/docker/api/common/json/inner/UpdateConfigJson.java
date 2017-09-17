package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swarmmanager.docker.api.common.annotation.DockerRemoteApiMinVersion;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateConfigJson {

    @JsonProperty("Parallelism")
    private long parallelism;

    @JsonProperty("Delay")
    private long delay;

    @DockerRemoteApiMinVersion(version = "v1.28", comment = "rollback option")
    @JsonProperty("FailureAction")
    private String failureAction;

    @JsonProperty("Monitor")
    private String monitor;

    @DockerRemoteApiMinVersion("v1.25")
    @JsonProperty("MaxFailureRatio")
    private float maxFailureRatio;

    public long getParallelism() {
        return parallelism;
    }

    public void setParallelism(long parallelism) {
        this.parallelism = parallelism;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public String getFailureAction() {
        return failureAction;
    }

    public void setFailureAction(String failureAction) {
        this.failureAction = failureAction;
    }

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    public float getMaxFailureRatio() {
        return maxFailureRatio;
    }

    public void setMaxFailureRatio(float maxFailureRatio) {
        this.maxFailureRatio = maxFailureRatio;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UpdateConfigJson{");
        sb.append("parallelism=").append(parallelism);
        sb.append(", delay=").append(delay);
        sb.append(", failureAction='").append(failureAction).append('\'');
        sb.append(", monitor=").append(monitor);
        sb.append(", maxFailureRatio=").append(maxFailureRatio);
        sb.append('}');
        return sb.toString();
    }
}

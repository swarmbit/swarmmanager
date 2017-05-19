package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swarmmanager.docker.api.common.annotation.DockerRemoteApiVersions;
import com.swarmmanager.docker.api.common.json.DockerRemoteApiJson;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
@DockerRemoteApiVersions(versions = "v1.28")
public class HealthConfigJson implements DockerRemoteApiJson {

    @JsonProperty("Test")
    private String[] test;

    @JsonProperty("Interval")
    private String interval;

    @JsonProperty("Timeout")
    private String timeout;

    @JsonProperty("Retries")
    private int retries;

    public String[] getTest() {
        return test;
    }

    public void setTest(String[] test) {
        this.test = test;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HealthConfigJson{");
        sb.append("test=").append(Arrays.toString(test));
        sb.append(", interval=").append(interval);
        sb.append(", timeout=").append(timeout);
        sb.append(", retries=").append(retries);
        sb.append('}');
        return sb.toString();
    }
}

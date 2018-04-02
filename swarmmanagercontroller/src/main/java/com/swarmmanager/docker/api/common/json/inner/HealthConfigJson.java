package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swarmmanager.docker.api.common.annotation.DockerRemoteApiMinVersion;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HealthConfigJson {

    @JsonProperty("Test")
    private String[] test;

    @JsonProperty("Interval")
    private Long interval;

    @JsonProperty("Timeout")
    private Long timeout;

    @JsonProperty("Retries")
    private Integer retries;

    @DockerRemoteApiMinVersion("v1.29")
    @JsonProperty("StartPeriod")
    private Long startPeriod;

    public String[] getTest() {
        return test;
    }

    public void setTest(String[] test) {
        this.test = test;
    }

    public Long getInterval() {
        return interval;
    }

    public void setInterval(Long interval) {
        this.interval = interval;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public Integer getRetries() {
        return retries;
    }

    public void setRetries(Integer retries) {
        this.retries = retries;
    }

    public Long getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(Long startPeriod) {
        this.startPeriod = startPeriod;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HealthConfigJson{");
        sb.append("test=").append(Arrays.toString(test));
        sb.append(", interval='").append(interval).append('\'');
        sb.append(", timeout='").append(timeout).append('\'');
        sb.append(", retries=").append(retries);
        sb.append(", startPeriod=").append(startPeriod);
        sb.append('}');
        return sb.toString();
    }
}

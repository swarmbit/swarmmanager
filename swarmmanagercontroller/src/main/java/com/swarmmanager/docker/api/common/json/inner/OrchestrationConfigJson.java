package com.swarmmanager.docker.api.common.json.inner;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrchestrationConfigJson {

    @JsonProperty("TaskHistoryRetentionLimit")
    private long taskHistoryRetentionLimit;

    public long getTaskHistoryRetentionLimit() {
        return taskHistoryRetentionLimit;
    }

    public void setTaskHistoryRetentionLimit(long taskHistoryRetentionLimit) {
        this.taskHistoryRetentionLimit = taskHistoryRetentionLimit;
    }
}

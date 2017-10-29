package com.swarmmanager.docker.api.common.json.inner;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrchestrationConfigJson {

    @JsonProperty("TaskHistoryRetentionLimit")
    private Long taskHistoryRetentionLimit;

    public Long getTaskHistoryRetentionLimit() {
        return taskHistoryRetentionLimit;
    }

    public void setTaskHistoryRetentionLimit(Long taskHistoryRetentionLimit) {
        this.taskHistoryRetentionLimit = taskHistoryRetentionLimit;
    }
}

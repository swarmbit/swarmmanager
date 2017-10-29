package com.swarmmanager.docker.api.common.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swarmmanager.docker.api.common.json.inner.CAConfigJson;
import com.swarmmanager.docker.api.common.json.inner.DispatcherConfigJson;
import com.swarmmanager.docker.api.common.json.inner.EncryptionConfigJson;
import com.swarmmanager.docker.api.common.json.inner.OrchestrationConfigJson;
import com.swarmmanager.docker.api.common.json.inner.RaftConfigJson;
import com.swarmmanager.docker.api.common.json.inner.TasksDefaultsJson;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SwarmSpecJson {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Labels")
    private Map<String, String> labels;

    @JsonProperty("Orchestration")
    private OrchestrationConfigJson orchestration;

    @JsonProperty("Raft")
    private RaftConfigJson raft;

    @JsonProperty("Dispatcher")
    private DispatcherConfigJson dispatcher;

    @JsonProperty("CAConfig")
    private CAConfigJson caConfig;

    @JsonProperty("TasksDefaults")
    private TasksDefaultsJson tasksDefaults;

    @JsonProperty("EncryptionConfig")
    private EncryptionConfigJson encryptionConfig;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }

    public OrchestrationConfigJson getOrchestration() {
        return orchestration;
    }

    public void setOrchestration(OrchestrationConfigJson orchestration) {
        this.orchestration = orchestration;
    }

    public RaftConfigJson getRaft() {
        return raft;
    }

    public void setRaft(RaftConfigJson raft) {
        this.raft = raft;
    }

    public DispatcherConfigJson getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(DispatcherConfigJson dispatcher) {
        this.dispatcher = dispatcher;
    }

    public CAConfigJson getCaConfig() {
        return caConfig;
    }

    public void setCaConfig(CAConfigJson caConfig) {
        this.caConfig = caConfig;
    }

    public TasksDefaultsJson getTasksDefaults() {
        return tasksDefaults;
    }

    public void setTasksDefaults(TasksDefaultsJson tasksDefaults) {
        this.tasksDefaults = tasksDefaults;
    }

    public EncryptionConfigJson getEncryptionConfig() {
        return encryptionConfig;
    }

    public void setEncryptionConfig(EncryptionConfigJson encryptionConfig) {
        this.encryptionConfig = encryptionConfig;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SwarmSpecJson{");
        sb.append("name='").append(name).append('\'');
        sb.append(", labels=").append(labels);
        sb.append(", orchestration=").append(orchestration);
        sb.append(", raft=").append(raft);
        sb.append(", dispatcher=").append(dispatcher);
        sb.append(", caConfig=").append(caConfig);
        sb.append(", tasksDefaults=").append(tasksDefaults);
        sb.append(", encryptionConfig=").append(encryptionConfig);
        sb.append('}');
        return sb.toString();
    }
}

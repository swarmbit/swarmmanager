package com.swarmmanager.docker.api.common.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swarmmanager.docker.api.common.json.inner.EndpointSpecJson;
import com.swarmmanager.docker.api.common.json.inner.TaskSpecJson;
import com.swarmmanager.docker.api.common.json.inner.UpdateConfigJson;
import com.swarmmanager.docker.api.common.annotation.DockerRemoteApiMinVersion;
import com.swarmmanager.docker.api.common.json.inner.NetworkAttachmentConfigJson;
import com.swarmmanager.docker.api.common.json.inner.ServiceModeJson;

import java.util.Arrays;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceSpecJson {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Labels")
    private Map<String, String>  labels;

    @JsonProperty("TaskTemplate")
    private TaskSpecJson taskTemplate;

    @JsonProperty("Mode")
    private ServiceModeJson mode;

    @JsonProperty("UpdateConfig")
    private UpdateConfigJson updateConfig;

    @DockerRemoteApiMinVersion("v1.28")
    @JsonProperty("RollbackConfig")
    private UpdateConfigJson rollbackConfig;

    @JsonProperty("Networks")
    private NetworkAttachmentConfigJson[] networks;

    @JsonProperty("EndpointSpec")
    private EndpointSpecJson endpointSpec;

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

    public TaskSpecJson getTaskTemplate() {
        return taskTemplate;
    }

    public void setTaskTemplate(TaskSpecJson taskTemplate) {
        this.taskTemplate = taskTemplate;
    }

    public ServiceModeJson getMode() {
        return mode;
    }

    public void setMode(ServiceModeJson mode) {
        this.mode = mode;
    }

    public UpdateConfigJson getUpdateConfig() {
        return updateConfig;
    }

    public void setUpdateConfig(UpdateConfigJson updateConfig) {
        this.updateConfig = updateConfig;
    }

    public UpdateConfigJson getRollbackConfig() {
        return rollbackConfig;
    }

    public void setRollbackConfig(UpdateConfigJson rollbackConfig) {
        this.rollbackConfig = rollbackConfig;
    }

    public NetworkAttachmentConfigJson[] getNetworks() {
        return networks;
    }

    public void setNetworks(NetworkAttachmentConfigJson[] networks) {
        this.networks = networks;
    }

    public EndpointSpecJson getEndpointSpec() {
        return endpointSpec;
    }

    public void setEndpointSpec(EndpointSpecJson endpointSpec) {
        this.endpointSpec = endpointSpec;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ServiceSpecJson{");
        sb.append("name='").append(name).append('\'');
        sb.append(", labels=").append(labels);
        sb.append(", taskTemplate=").append(taskTemplate);
        sb.append(", mode=").append(mode);
        sb.append(", updateConfig=").append(updateConfig);
        sb.append(", networks=").append(Arrays.toString(networks));
        sb.append(", endpointSpec=").append(endpointSpec);
        sb.append('}');
        return sb.toString();
    }
}

package com.swarmmanager.docker.api.common.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swarmmanager.docker.api.common.json.inner.NetworkAttachmentConfigJson;
import com.swarmmanager.docker.api.common.json.inner.TaskStatusJson;
import com.swarmmanager.docker.api.common.json.inner.VersionJson;

import java.util.Arrays;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskJson {

    @JsonProperty("ID")
    private String id;

    @JsonProperty("Version")
    private VersionJson version;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Labels")
    private Map<String, String> labels;

    @JsonProperty("ServiceID")
    private String serviceId;

    @JsonProperty("Slot")
    private int slot;

    @JsonProperty("NodeID")
    private String nodeId;

    @JsonProperty("Status")
    private TaskStatusJson status;

    @JsonProperty("DesiredState")
    private String desiredState;

    @JsonProperty("NetworksAttachments")
    private NetworkAttachmentConfigJson[] networksAttachments;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public VersionJson getVersion() {
        return version;
    }

    public void setVersion(VersionJson version) {
        this.version = version;
    }

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

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public TaskStatusJson getStatus() {
        return status;
    }

    public void setStatus(TaskStatusJson status) {
        this.status = status;
    }

    public String getDesiredState() {
        return desiredState;
    }

    public void setDesiredState(String desiredState) {
        this.desiredState = desiredState;
    }

    public NetworkAttachmentConfigJson[] getNetworksAttachments() {
        return networksAttachments;
    }

    public void setNetworksAttachments(NetworkAttachmentConfigJson[] networksAttachments) {
        this.networksAttachments = networksAttachments;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TaskJson{");
        sb.append("id='").append(id).append('\'');
        sb.append(", version=").append(version);
        sb.append(", name='").append(name).append('\'');
        sb.append(", labels=").append(labels);
        sb.append(", serviceId='").append(serviceId).append('\'');
        sb.append(", slot=").append(slot);
        sb.append(", nodeId='").append(nodeId).append('\'');
        sb.append(", status=").append(status);
        sb.append(", desiredState='").append(desiredState).append('\'');
        sb.append(", networksAttachments=").append(Arrays.toString(networksAttachments));
        sb.append('}');
        return sb.toString();
    }
}

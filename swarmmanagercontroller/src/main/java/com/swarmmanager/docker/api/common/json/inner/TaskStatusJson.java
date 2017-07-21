package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskStatusJson {

    @JsonProperty("Timestamp")
    private String timestamp;

    @JsonProperty("State")
    private String taskState;

    @JsonProperty("Message")
    private String message;

    @JsonProperty("Err")
    private String err;

    @JsonProperty("ContainerStatus")
    private ContainerStatusJson containerStatus;

    @JsonProperty("PortStatus")
    private PortStatusJson portStatus;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTaskState() {
        return taskState;
    }

    public void setTaskState(String taskState) {
        this.taskState = taskState;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public ContainerStatusJson getContainerStatus() {
        return containerStatus;
    }

    public void setContainerStatus(ContainerStatusJson containerStatus) {
        this.containerStatus = containerStatus;
    }

    public PortStatusJson getPortStatus() {
        return portStatus;
    }

    public void setPortStatus(PortStatusJson portStatus) {
        this.portStatus = portStatus;
    }
}

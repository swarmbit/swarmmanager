package co.uk.swarmbit.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TaskStatusJson{");
        sb.append("timestamp='").append(timestamp).append('\'');
        sb.append(", taskState='").append(taskState).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append(", err='").append(err).append('\'');
        sb.append(", containerStatus=").append(containerStatus);
        sb.append(", portStatus=").append(portStatus);
        sb.append('}');
        return sb.toString();
    }
}

package co.uk.swarmbit.docker.api.common.json.inner;

import co.uk.swarmbit.docker.api.common.annotation.DockerRemoteApiMinVersion;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateConfigJson {

    @JsonProperty("Parallelism")
    private Long parallelism;

    @JsonProperty("Delay")
    private Long delay;

    @DockerRemoteApiMinVersion(version = "v1.28", comment = "rollback option")
    @JsonProperty("FailureAction")
    private String failureAction;

    @JsonProperty("Monitor")
    private Long monitor;

    @DockerRemoteApiMinVersion("v1.29")
    @JsonProperty("Order")
    private String order;

    @DockerRemoteApiMinVersion("v1.25")
    @JsonProperty("MaxFailureRatio")
    private Double maxFailureRatio;

    public Long getParallelism() {
        return parallelism;
    }

    public void setParallelism(Long parallelism) {
        this.parallelism = parallelism;
    }

    public Long getDelay() {
        return delay;
    }

    public void setDelay(Long delay) {
        this.delay = delay;
    }

    public String getFailureAction() {
        return failureAction;
    }

    public void setFailureAction(String failureAction) {
        this.failureAction = failureAction;
    }

    public Long getMonitor() {
        return monitor;
    }

    public void setMonitor(Long monitor) {
        this.monitor = monitor;
    }

    public Double getMaxFailureRatio() {
        return maxFailureRatio;
    }

    public void setMaxFailureRatio(Double maxFailureRatio) {
        this.maxFailureRatio = maxFailureRatio;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UpdateConfigJson{");
        sb.append("parallelism=").append(parallelism);
        sb.append(", delay='").append(delay).append('\'');
        sb.append(", failureAction='").append(failureAction).append('\'');
        sb.append(", monitor='").append(monitor).append('\'');
        sb.append(", order='").append(order).append('\'');
        sb.append(", maxFailureRatio=").append(maxFailureRatio);
        sb.append('}');
        return sb.toString();
    }
}

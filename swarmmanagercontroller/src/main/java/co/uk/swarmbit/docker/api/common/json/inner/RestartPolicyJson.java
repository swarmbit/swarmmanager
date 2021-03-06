package co.uk.swarmbit.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestartPolicyJson {

    @JsonProperty("Condition")
    private String condition;

    @JsonProperty("Delay")
    private Long delay;

    @JsonProperty("MaxAttempts")
    private Long maxAttempts;

    @JsonProperty("Window")
    private Long window;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Long getDelay() {
        return delay;
    }

    public void setDelay(Long delay) {
        this.delay = delay;
    }

    public Long getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(Long maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public Long getWindow() {
        return window;
    }

    public void setWindow(Long window) {
        this.window = window;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RestartPolicyJson{");
        sb.append("condition=").append(condition);
        sb.append(", delay=").append(delay);
        sb.append(", maxAttempts=").append(maxAttempts);
        sb.append(", window=").append(window);
        sb.append('}');
        return sb.toString();
    }
}

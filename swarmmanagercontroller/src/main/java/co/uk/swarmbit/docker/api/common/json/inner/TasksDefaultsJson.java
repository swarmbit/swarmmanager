package co.uk.swarmbit.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TasksDefaultsJson {

    @JsonProperty("LogDriver")
    private DriverJson logDriver;

    public DriverJson getLogDriver() {
        return logDriver;
    }

    public void setLogDriver(DriverJson logDriver) {
        this.logDriver = logDriver;
    }
}

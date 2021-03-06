package co.uk.swarmbit.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IPAMJson {

    @JsonProperty("Driver")
    private String driver;

    @JsonProperty("Options")
    private Map<String, String> Options;

    @JsonProperty("Config")
    private IPAMConfigJson[] config;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public Map<String, String> getOptions() {
        return Options;
    }

    public void setOptions(Map<String, String> options) {
        Options = options;
    }

    public IPAMConfigJson[] getConfig() {
        return config;
    }

    public void setConfig(IPAMConfigJson[] config) {
        this.config = config;
    }
}

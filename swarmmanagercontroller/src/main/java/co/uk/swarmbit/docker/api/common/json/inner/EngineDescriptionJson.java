package co.uk.swarmbit.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EngineDescriptionJson {

    @JsonProperty("EngineVersion")
    private String engineVersion;

    @JsonProperty("Labels")
    private Map<String, String> labels;

    @JsonProperty("Plugins")
    private PluginDescriptionJson[] plugins;

    public String getEngineVersion() {
        return engineVersion;
    }

    public void setEngineVersion(String engineVersion) {
        this.engineVersion = engineVersion;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }

    public PluginDescriptionJson[] getPlugins() {
        return plugins;
    }

    public void setPlugins(PluginDescriptionJson[] plugins) {
        this.plugins = plugins;
    }
}

package co.uk.swarmbit.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConfigReferenceJson {

    @JsonProperty("File")
    private ConfigReferenceFileTargetJson file;

    @JsonProperty("ConfigID")
    private String configID;

    @JsonProperty("ConfigName")
    private String configName;

    public ConfigReferenceFileTargetJson getFile() {
        return file;
    }

    public void setFile(ConfigReferenceFileTargetJson file) {
        this.file = file;
    }

    public String getConfigID() {
        return configID;
    }

    public void setConfigID(String configID) {
        this.configID = configID;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ConfigReferenceJson{");
        sb.append("file=").append(file);
        sb.append(", configID='").append(configID).append('\'');
        sb.append(", configName='").append(configName).append('\'');
        sb.append('}');
        return sb.toString();
    }

}

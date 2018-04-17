package co.uk.swarmbit.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PluginSpecJson {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Remote")
    private String remote;

    @JsonProperty("Disabled")
    private boolean disabled;

    @JsonProperty("PluginPrivilege")
    private PluginPrivilegeJson pluginPrivilege;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemote() {
        return remote;
    }

    public void setRemote(String remote) {
        this.remote = remote;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public PluginPrivilegeJson getPluginPrivilege() {
        return pluginPrivilege;
    }

    public void setPluginPrivilege(PluginPrivilegeJson pluginPrivilege) {
        this.pluginPrivilege = pluginPrivilege;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PluginSpecJson{");
        sb.append("name='").append(name).append('\'');
        sb.append(", remote='").append(remote).append('\'');
        sb.append(", disabled=").append(disabled);
        sb.append(", pluginPrivilege=").append(pluginPrivilege);
        sb.append('}');
        return sb.toString();
    }
}

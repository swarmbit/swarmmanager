package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swarmmanager.docker.api.common.json.DockerRemoteApiJson;
import com.swarmmanager.docker.api.common.annotation.DockerRemoteApiVersions;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
@DockerRemoteApiVersions(versions = "v1.28")
public class NetworkAttachmentConfigJson implements DockerRemoteApiJson {

    @JsonProperty("Target")
    private String target;

    @JsonProperty("Aliases")
    private String[] aliases;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String[] getAliases() {
        return aliases;
    }

    public void setAliases(String[] aliases) {
        this.aliases = aliases;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NetworkAttachmentConfigJson{");
        sb.append("target='").append(target).append('\'');
        sb.append(", aliases=").append(Arrays.toString(aliases));
        sb.append('}');
        return sb.toString();
    }
}

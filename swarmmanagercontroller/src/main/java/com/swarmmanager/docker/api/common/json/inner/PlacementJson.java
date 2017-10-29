package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swarmmanager.docker.api.common.annotation.DockerRemoteApiMinVersion;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlacementJson {

    @JsonProperty("Constraints")
    private String[] constraints;

    @DockerRemoteApiMinVersion("v1.28")
    @JsonProperty("Preferences")
    private PlacementPreferenceJson[]  preferences ;

    @DockerRemoteApiMinVersion("v1.30")
    @JsonProperty("Platforms")
    private PlatformJson[] platforms;

    public String[] getConstraints() {
        return constraints;
    }

    public void setConstraints(String[] constraints) {
        this.constraints = constraints;
    }

    public PlacementPreferenceJson[] getPreferences() {
        return preferences;
    }

    public void setPreferences(PlacementPreferenceJson[] preferences) {
        this.preferences = preferences;
    }

    public PlatformJson[] getPlatforms() {
        return platforms;
    }

    public void setPlatforms(PlatformJson[] platforms) {
        this.platforms = platforms;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PlacementJson{");
        sb.append("constraints=").append(Arrays.toString(constraints));
        sb.append(", preferences=").append(Arrays.toString(preferences));
        sb.append(", platforms=").append(Arrays.toString(platforms));
        sb.append('}');
        return sb.toString();
    }
}

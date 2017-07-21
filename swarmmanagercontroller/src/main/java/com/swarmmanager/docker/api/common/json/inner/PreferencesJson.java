package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PreferencesJson {

    @JsonProperty("Spread")
    private SpreadOverJson spread;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PreferencesJson{");
        sb.append("spread=").append(spread);
        sb.append('}');
        return sb.toString();
    }
}

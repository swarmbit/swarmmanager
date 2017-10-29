package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlacementPreferenceJson {

    @JsonProperty("Spread")
    private SpreadOverJson Spread;

    public SpreadOverJson getSpread() {
        return Spread;
    }

    public void setSpread(SpreadOverJson spread) {
        Spread = spread;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PlacementPreferenceJson{");
        sb.append("Spread=").append(Spread);
        sb.append('}');
        return sb.toString();
    }

}

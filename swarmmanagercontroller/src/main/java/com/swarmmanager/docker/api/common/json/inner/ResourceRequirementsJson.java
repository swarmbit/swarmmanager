package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResourceRequirementsJson {

    @JsonProperty("Limits")
    private ResourcesJson limits;

    @JsonProperty("Reservations")
    private ResourcesJson reservations;

    public ResourcesJson getLimits() {
        return limits;
    }

    public void setLimits(ResourcesJson limits) {
        this.limits = limits;
    }

    public ResourcesJson getReservations() {
        return reservations;
    }

    public void setReservations(ResourcesJson reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ResourceRequirementsJson{");
        sb.append("limits=").append(limits);
        sb.append(", reservations=").append(reservations);
        sb.append('}');
        return sb.toString();
    }
}

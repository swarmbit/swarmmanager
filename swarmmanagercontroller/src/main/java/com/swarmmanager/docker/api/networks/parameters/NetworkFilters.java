package com.swarmmanager.docker.api.networks.parameters;

import com.swarmmanager.docker.api.common.AbstractFilters;

public class NetworkFilters extends AbstractFilters {

    public NetworkFilters setId(String id) {
        addFilter("id", id);
        return this;
    }

    public NetworkFilters setLabel(String label) {
        addFilter("label", label);
        return this;
    }

    public NetworkFilters setDriver(String driver) {
        addFilter("driver", driver);
        return this;
    }

    public NetworkFilters setName(String name) {
        addFilter("name", name);
        return this;
    }

    public NetworkFilters setType(NetworkType type) {
        addFilter("type", type.toString());
        return this;
    }

}

package com.swarmmanager.docker.api.networks.parameters;

import com.swarmmanager.docker.api.common.AbstractFilters;
import com.swarmmanager.docker.api.common.annotation.DockerRemoteApiMinVersion;

public class NetworkFilters extends AbstractFilters {
    public static enum Type {
        CUSTOM,
        BUILTIN;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }

    }

    public static enum Scope {
        SWARM,
        GLOBAL,
        LOCAL;

        @Override
        public String toString() {
            return this.toString().toLowerCase();
        }
    }

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

    public NetworkFilters setType(Type type) {
        addFilter("type", type.toString());
        return this;
    }

    @DockerRemoteApiMinVersion("v1.29")
    public NetworkFilters setScope(Scope type) {
        addFilter("type", type.toString());
        return this;
    }

}

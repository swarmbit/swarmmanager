package co.uk.swarmbit.docker.api.networks.parameters;

import co.uk.swarmbit.docker.api.common.AbstractFilters;
import co.uk.swarmbit.docker.api.common.annotation.DockerRemoteApiMinVersion;

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
            return super.toString().toLowerCase();
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
    public NetworkFilters setScope(Scope scope) {
        addFilter("scope", scope.toString());
        return this;
    }

}

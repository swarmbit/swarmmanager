package co.uk.swarmbit.docker.api.services.parameters;

import co.uk.swarmbit.docker.api.common.AbstractFilters;
import co.uk.swarmbit.docker.api.common.Filters;
import co.uk.swarmbit.docker.api.common.annotation.DockerRemoteApiMinVersion;

public class ServicesFilters extends AbstractFilters implements Filters {
    public static enum Mode {
        REPLICATED,
        GLOBAL;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }
    public ServicesFilters setId(String id) {
        addFilter("id", id);
        return this;
    }

    public ServicesFilters setLabel(String label) {
        addFilter("label", label);
        return this;
    }

    public ServicesFilters setName(String name) {
        addFilter("name", name);
        return this;
    }

    @DockerRemoteApiMinVersion("v1.28")
    public ServicesFilters setMode(Mode mode) {
        addFilter("mode", mode.toString());
        return this;
    }

}

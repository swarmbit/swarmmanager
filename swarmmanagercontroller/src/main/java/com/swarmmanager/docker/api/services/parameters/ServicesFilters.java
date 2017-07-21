package com.swarmmanager.docker.api.services.parameters;

import com.swarmmanager.docker.api.common.AbstractFilters;
import com.swarmmanager.docker.api.common.Filters;
import com.swarmmanager.docker.api.common.annotation.DockerRemoteApiMinVersion;

public class ServicesFilters extends AbstractFilters implements Filters {
    public static enum Mode {
        REPLICATED,
        GLOBAL;

        @Override
        public String toString() {
            return this.toString().toLowerCase();
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

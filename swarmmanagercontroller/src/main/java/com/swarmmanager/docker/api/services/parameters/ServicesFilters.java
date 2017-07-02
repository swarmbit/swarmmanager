package com.swarmmanager.docker.api.services.parameters;

import com.swarmmanager.docker.api.common.AbstractFilters;
import com.swarmmanager.docker.api.common.Filters;

public class ServicesFilters extends AbstractFilters implements Filters {

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

}

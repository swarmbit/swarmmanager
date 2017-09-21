package com.swarmmanager.docker.api.configs.parameters;

import com.swarmmanager.docker.api.common.AbstractFilters;
import com.swarmmanager.docker.api.common.Filters;

public class ConfigsFilters extends AbstractFilters implements Filters {


    public ConfigsFilters setId(String id) {
        addFilter("id", id);
        return this;
    }

    public ConfigsFilters setName(String name) {
        addFilter("name", name);
        return this;
    }

    public ConfigsFilters setLabel(String label) {
        addFilter("label", label);
        return this;
    }

}

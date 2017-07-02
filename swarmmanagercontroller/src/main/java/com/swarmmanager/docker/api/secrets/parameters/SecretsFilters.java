package com.swarmmanager.docker.api.secrets.parameters;

import com.swarmmanager.docker.api.common.AbstractFilters;
import com.swarmmanager.docker.api.common.Filters;

public class SecretsFilters extends AbstractFilters implements Filters {


    public SecretsFilters setId(String id) {
        addFilter("id", id);
        return this;
    }

    public SecretsFilters setName(String name) {
        addFilter("name", name);
        return this;
    }

    public SecretsFilters setLabel(String label) {
        addFilter("label", label);
        return this;
    }

}

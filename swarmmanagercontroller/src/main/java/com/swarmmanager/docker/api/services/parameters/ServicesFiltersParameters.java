package com.swarmmanager.docker.api.services.parameters;

import com.swarmmanager.docker.api.common.FiltersParameters;
import com.swarmmanager.rest.QueryParam;

import java.util.Optional;

import static com.swarmmanager.docker.api.common.util.JsonEncoder.encodeJson;
import static com.swarmmanager.docker.api.common.util.JsonEncoder.toDockerJsonFilters;

public class ServicesFiltersParameters implements FiltersParameters {

    private static final String FILTERS_NAME = "filters";

    private Optional<QueryParam> filters;

    public ServicesFiltersParameters() {
        filters = Optional.empty();
    }

    public Optional<QueryParam> getFilters() {
        return filters;
    }

    public ServicesFiltersParameters setFilters(ServicesFilters filtersValue) {
        if (filtersValue != null) {
            filters = Optional.of(new QueryParam(FILTERS_NAME, encodeJson(toDockerJsonFilters(filtersValue))));
        }
        return this;
    }
}

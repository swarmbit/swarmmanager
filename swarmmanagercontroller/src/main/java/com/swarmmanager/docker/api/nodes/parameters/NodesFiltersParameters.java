package com.swarmmanager.docker.api.nodes.parameters;

import com.swarmmanager.docker.api.common.FiltersParameters;
import com.swarmmanager.docker.api.common.util.JsonEncoder;
import com.swarmmanager.rest.QueryParam;

import java.util.Optional;

public class NodesFiltersParameters implements FiltersParameters {

    private static final String FILTERS_NAME = "filters";

    private Optional<QueryParam> filters;

    public NodesFiltersParameters() {
        filters = Optional.empty();
    }

    public Optional<QueryParam> getFilters() {
        return filters;
    }

    public NodesFiltersParameters setFiltersQueryParam(NodesFilters filtersValue) {
        this.filters = Optional.of(new QueryParam(FILTERS_NAME, JsonEncoder.encodeJson(JsonEncoder.toDockerJsonFilters(filtersValue))));
        return this;
    }
}

package com.swarmmanager.docker.api.nodes.parameters;

import com.swarmmanager.docker.api.common.FiltersParameters;
import com.swarmmanager.rest.QueryParam;

import java.util.Optional;

import static com.swarmmanager.docker.api.common.util.EncoderDecoder.jsonUrlEncode;

public class NodesListParameters implements FiltersParameters {

    private static final String FILTERS_NAME = "filters";

    private Optional<QueryParam> filters;

    public NodesListParameters() {
        filters = Optional.empty();
    }

    public NodesListParameters setFilters(NodesFilters filters) {
        if (filters != null) {
            this.filters = Optional.of(new QueryParam(FILTERS_NAME, jsonUrlEncode(filters.getJson())));
        }
        return this;
    }

    public Optional<QueryParam> getFilters() {
        return filters;
    }

}

package com.swarmmanager.docker.api.networks.parameters;

import com.swarmmanager.docker.api.common.parameters.FiltersParameters;
import com.swarmmanager.rest.QueryParam;

import java.util.Optional;

import static com.swarmmanager.docker.api.common.util.EncoderDecoder.jsonUrlEncode;

public class NetworkListParameters implements FiltersParameters {

    private static final String FILTERS_NAME = "filters";

    private Optional<QueryParam> filters;

    public NetworkListParameters() {
        filters = Optional.empty();
    }

    public NetworkListParameters setFilters(NetworkFilters filters) {
        if (filters != null) {
            this.filters = Optional.of(new QueryParam(FILTERS_NAME, jsonUrlEncode(filters.getJson())));
        }
        return this;
    }

    public Optional<QueryParam> getFilters() {
        return filters;
    }

}
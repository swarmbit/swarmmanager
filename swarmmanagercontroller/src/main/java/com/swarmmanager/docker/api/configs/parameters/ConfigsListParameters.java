package com.swarmmanager.docker.api.configs.parameters;

import com.swarmmanager.docker.api.common.parameters.FiltersParameters;
import com.swarmmanager.rest.QueryParam;

import java.util.Optional;

import static com.swarmmanager.util.EncoderDecoder.jsonUrlEncode;

public class ConfigsListParameters implements FiltersParameters {

    private static final String FILTERS_NAME = "filters";

    private Optional<QueryParam> filters;

    public ConfigsListParameters() {
        filters = Optional.empty();
    }

    public Optional<QueryParam> getFilters() {
        return filters;
    }

    public ConfigsListParameters setFilters(ConfigsFilters filters) {
        if (filters != null) {
            this.filters = Optional.of(new QueryParam(FILTERS_NAME, jsonUrlEncode(filters.getJson())));
        }
        return this;
    }
}

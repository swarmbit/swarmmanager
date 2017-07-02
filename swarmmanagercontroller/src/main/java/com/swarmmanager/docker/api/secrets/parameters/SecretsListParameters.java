package com.swarmmanager.docker.api.secrets.parameters;

import com.swarmmanager.docker.api.common.parameters.FiltersParameters;
import com.swarmmanager.rest.QueryParam;

import java.util.Optional;

import static com.swarmmanager.docker.api.common.util.EncoderDecoder.jsonUrlEncode;

public class SecretsListParameters implements FiltersParameters {

    private static final String FILTERS_NAME = "filters";

    private Optional<QueryParam> filters;

    public SecretsListParameters() {
        filters = Optional.empty();
    }

    public Optional<QueryParam> getFilters() {
        return filters;
    }

    public SecretsListParameters setFilters(SecretsFilters filters) {
        if (filters != null) {
            this.filters = Optional.of(new QueryParam(FILTERS_NAME, jsonUrlEncode(filters.getJson())));
        }
        return this;
    }
}

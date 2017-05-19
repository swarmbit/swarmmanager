package com.swarmmanager.docker.api.services.parameters;

import com.swarmmanager.rest.QueryParam;

import java.util.Optional;

import static com.swarmmanager.docker.api.common.util.JsonEncoder.encodeJson;
import static com.swarmmanager.docker.api.common.util.JsonEncoder.toDockerJsonFilters;

public class ServicesListParameters {

    private static final String FILTERS_NAME = "filters";

    private Optional<QueryParam> filters;

    public ServicesListParameters() {
        filters = Optional.empty();
    }

    public Optional<QueryParam> getFilters() {
        return filters;
    }

    public void setFilters(ServicesFilters filtersValue) {
        if (filtersValue != null) {
            filters = Optional.of(new QueryParam(FILTERS_NAME, encodeJson(toDockerJsonFilters(filtersValue))));
        }
    }
}

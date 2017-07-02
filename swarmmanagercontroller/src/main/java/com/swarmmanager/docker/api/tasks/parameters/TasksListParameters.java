package com.swarmmanager.docker.api.tasks.parameters;

import com.swarmmanager.docker.api.common.parameters.FiltersParameters;
import com.swarmmanager.docker.api.common.util.EncoderDecoder;
import com.swarmmanager.rest.QueryParam;

import java.util.Optional;

public class TasksListParameters implements FiltersParameters {

    private static final String FILTERS_NAME = "filters";

    private Optional<QueryParam> filters;

    public TasksListParameters() {
        filters = Optional.empty();
    }

    public Optional<QueryParam> getFilters() {
        return filters;
    }

    public void setFilters(TasksFilters filters) {
        this.filters = Optional.of(new QueryParam(FILTERS_NAME, EncoderDecoder.jsonUrlEncode(filters.getJson())));
    }
}

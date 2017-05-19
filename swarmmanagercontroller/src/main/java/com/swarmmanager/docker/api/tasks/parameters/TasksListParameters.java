package com.swarmmanager.docker.api.tasks.parameters;

import com.swarmmanager.docker.api.common.util.JsonEncoder;
import com.swarmmanager.rest.QueryParam;

import java.util.Optional;

public class TasksListParameters {

    private static final String FILTERS_NAME = "filters";

    private Optional<QueryParam> filters;

    public TasksListParameters() {
        filters = Optional.empty();
    }

    public Optional<QueryParam> getFilters() {
        return filters;
    }

    public void setFilters(TasksFilters filtersValue) {
        this.filters = Optional.of(new QueryParam(FILTERS_NAME, JsonEncoder.encodeJson(JsonEncoder.toDockerJsonFilters(filtersValue))));
    }
}

package com.swarmmanager.docker.api.nodes.parameters;

import com.swarmmanager.docker.api.common.util.JsonEncoder;
import com.swarmmanager.rest.QueryParam;

import java.util.Optional;

public class NodesListParameters {

    private static final String FILTERS_NAME = "filters";

    private Optional<QueryParam> filtersQueryParam;

    public NodesListParameters() {
        filtersQueryParam = Optional.empty();
    }

    public Optional<QueryParam> getFiltersQueryParam() {
        return filtersQueryParam;
    }

    public void setFiltersQueryParam(NodesFilters filtersValue) {
        this.filtersQueryParam = Optional.of(new QueryParam(FILTERS_NAME, JsonEncoder.encodeJson(JsonEncoder.toDockerJsonFilters(filtersValue))));
    }
}

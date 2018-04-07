package co.uk.swarmbit.docker.api.tasks.parameters;

import co.uk.swarmbit.util.EncoderDecoder;
import co.uk.swarmbit.docker.api.common.parameters.FiltersParameters;
import co.uk.swarmbit.rest.QueryParam;

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

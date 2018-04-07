package co.uk.swarmbit.docker.api.configs.parameters;

import co.uk.swarmbit.rest.QueryParam;
import co.uk.swarmbit.util.EncoderDecoder;
import co.uk.swarmbit.docker.api.common.parameters.FiltersParameters;

import java.util.Optional;

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
            this.filters = Optional.of(new QueryParam(FILTERS_NAME, EncoderDecoder.jsonUrlEncode(filters.getJson())));
        }
        return this;
    }
}

package co.uk.swarmbit.docker.api.services.parameters;

import co.uk.swarmbit.util.EncoderDecoder;
import co.uk.swarmbit.docker.api.common.parameters.FiltersParameters;
import co.uk.swarmbit.rest.QueryParam;

import java.util.Optional;

public class ServicesListParameters implements FiltersParameters {

    private static final String FILTERS_NAME = "filters";

    private Optional<QueryParam> filters;

    public ServicesListParameters() {
        filters = Optional.empty();
    }

    public Optional<QueryParam> getFilters() {
        return filters;
    }

    public ServicesListParameters setFilters(ServicesFilters filters) {
        if (filters != null) {
            this.filters = Optional.of(new QueryParam(FILTERS_NAME, EncoderDecoder.jsonUrlEncode(filters.getJson())));
        }
        return this;
    }
}

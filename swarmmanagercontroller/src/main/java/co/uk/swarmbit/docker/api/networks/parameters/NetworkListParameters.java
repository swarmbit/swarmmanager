package co.uk.swarmbit.docker.api.networks.parameters;

import co.uk.swarmbit.rest.QueryParam;
import co.uk.swarmbit.util.EncoderDecoder;
import co.uk.swarmbit.docker.api.common.parameters.FiltersParameters;

import java.util.Optional;

public class NetworkListParameters implements FiltersParameters {

    private static final String FILTERS_NAME = "filters";

    private Optional<QueryParam> filters;

    public NetworkListParameters() {
        filters = Optional.empty();
    }

    public NetworkListParameters setFilters(NetworkFilters filters) {
        if (filters != null) {
            this.filters = Optional.of(new QueryParam(FILTERS_NAME, EncoderDecoder.jsonUrlEncode(filters.getJson())));
        }
        return this;
    }

    public Optional<QueryParam> getFilters() {
        return filters;
    }

}
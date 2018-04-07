package co.uk.swarmbit.docker.api.nodes.parameters;

import co.uk.swarmbit.rest.QueryParam;
import co.uk.swarmbit.util.EncoderDecoder;
import co.uk.swarmbit.docker.api.common.parameters.FiltersParameters;

import java.util.Optional;

public class NodesListParameters implements FiltersParameters {

    private static final String FILTERS_NAME = "filters";

    private Optional<QueryParam> filters;

    public NodesListParameters() {
        filters = Optional.empty();
    }

    public NodesListParameters setFilters(NodesFilters filters) {
        if (filters != null) {
            this.filters = Optional.of(new QueryParam(FILTERS_NAME, EncoderDecoder.jsonUrlEncode(filters.getJson())));
        }
        return this;
    }

    public Optional<QueryParam> getFilters() {
        return filters;
    }

}

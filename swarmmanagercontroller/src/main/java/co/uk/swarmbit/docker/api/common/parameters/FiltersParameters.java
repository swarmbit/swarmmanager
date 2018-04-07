package co.uk.swarmbit.docker.api.common.parameters;

import co.uk.swarmbit.rest.QueryParam;

import java.util.Optional;

public interface FiltersParameters {

    Optional<QueryParam> getFilters();

}

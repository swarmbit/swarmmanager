package com.swarmmanager.docker.api.common.parameters;

import com.swarmmanager.rest.QueryParam;

import java.util.Optional;

public interface FiltersParameters {

    Optional<QueryParam> getFilters();

}

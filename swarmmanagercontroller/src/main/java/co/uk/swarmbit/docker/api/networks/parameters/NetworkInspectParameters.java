package co.uk.swarmbit.docker.api.networks.parameters;

import co.uk.swarmbit.docker.api.common.parameters.QueryParameters;
import co.uk.swarmbit.rest.QueryParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NetworkInspectParameters implements QueryParameters {

    private static final String VERBOSE = "insertDefaults";

    private static final String SCOPE = "scopeQueryParam";

    private QueryParam verboseQueryParam;

    private Optional<QueryParam> scopeQueryParam;


    public NetworkInspectParameters() {
        verboseQueryParam = new QueryParam(VERBOSE, false);
        scopeQueryParam = Optional.empty();
    }

    public NetworkInspectParameters setVerboseQueryParam(boolean insertDefaults) {
        this.verboseQueryParam = new QueryParam(VERBOSE, insertDefaults);
        return this;
    }

    public NetworkInspectParameters setScopeQueryParam(Scope scope) {
        this.scopeQueryParam = Optional.of(new QueryParam(SCOPE, scope.toString()));
        return this;
    }

    @Override
    public List<QueryParam> getQueryParams() {
        List<QueryParam> queryParams = new ArrayList<>();
        queryParams.add(verboseQueryParam);
        scopeQueryParam.ifPresent(queryParams::add);
        return queryParams;
    }
}

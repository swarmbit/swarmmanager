package co.uk.swarmbit.docker.api.services.parameters;

import co.uk.swarmbit.docker.api.common.parameters.QueryParameters;
import co.uk.swarmbit.rest.QueryParam;

import java.util.ArrayList;
import java.util.List;

public class ServiceInspectParameters implements QueryParameters {

    private static final String INSERT_DEFAULTS = "insertDefaults";

    private QueryParam insertDefaultsQueryParam;

    public ServiceInspectParameters() {
        insertDefaultsQueryParam = new QueryParam(INSERT_DEFAULTS, false);
    }

    public ServiceInspectParameters setInsertDefaultsQueryParam(boolean insertDefaults) {
        this.insertDefaultsQueryParam = new QueryParam(INSERT_DEFAULTS, insertDefaults);
        return this;
    }

    @Override
    public List<QueryParam> getQueryParams() {
        List<QueryParam> queryParams = new ArrayList<>();
        queryParams.add(insertDefaultsQueryParam);
        return queryParams;
    }
}

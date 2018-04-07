package co.uk.swarmbit.docker.api.nodes.parameters;

import co.uk.swarmbit.docker.api.common.json.NodeSpecJson;
import co.uk.swarmbit.docker.api.common.parameters.QueryParameters;
import co.uk.swarmbit.docker.api.common.parameters.RequestBodyParameter;
import co.uk.swarmbit.rest.QueryParam;

import java.util.ArrayList;
import java.util.List;

public class NodeUpdateParameters implements RequestBodyParameter, QueryParameters {

    private static final String VERSION_NAME = "version";

    private QueryParam versionQueryParam;

    private NodeSpecJson node;

    public NodeUpdateParameters() {
        node = new NodeSpecJson();
        versionQueryParam = new QueryParam(VERSION_NAME, 0L);
    }

    public NodeUpdateParameters setVersionQueryParam(long versionValue) {
        this.versionQueryParam = new QueryParam(VERSION_NAME, versionValue);
        return this;
    }

    public NodeUpdateParameters setNode(NodeSpecJson node) {
        this.node = node;
        return this;
    }

    @Override
    public Object getRequestBody() {
        return node;
    }

    @Override
    public List<QueryParam> getQueryParams() {
        List<QueryParam> queryParams = new ArrayList<>();
        queryParams.add(versionQueryParam);
        return queryParams;
    }

}

package com.swarmmanager.docker.api.nodes;

import com.swarmmanager.docker.api.nodes.parameters.NodeDeleteParameters;
import com.swarmmanager.docker.api.nodes.parameters.NodeInspectParameters;
import com.swarmmanager.docker.api.nodes.parameters.NodeUpdateParameters;
import com.swarmmanager.docker.api.nodes.parameters.NodesListParameters;
import com.swarmmanager.docker.api.common.client.DockerWebClient;
import com.swarmmanager.docker.api.common.json.NodeJson;
import com.swarmmanager.rest.RestExecutorFactory;
import com.swarmmanager.rest.RestMethod;
import com.swarmmanager.rest.RestParameters;
import com.swarmmanager.rest.RestResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NodesApiImpl implements NodesApi {

    private static final String NODES_PATH = "/nodes";

    private static final String UPDATE_PATH = "/update";

    @Autowired
    private DockerWebClient dockerWebClient;

    @Override
    public List<NodeJson> listNodes(NodesListParameters parameters) {
        if (parameters != null) {
            RestParameters restParameters = new RestParameters(dockerWebClient.getBaseResource())
                    .setPath(NODES_PATH);
            if (parameters.getFiltersQueryParam().isPresent()) {
                restParameters.addQueryParam(parameters.getFiltersQueryParam().get());
            }
            return RestExecutorFactory.createRestExecutor(RestMethod.GET).execute(restParameters, new RestResponseType<List<NodeJson>>(){});
        }
        return new ArrayList<>();
    }

    @Override
    public NodeJson inspectNode(NodeInspectParameters parameters) {
        if (parameters != null) {
            RestParameters restParameters = new RestParameters(dockerWebClient.getBaseResource())
                    .setPath(NODES_PATH + "/" + parameters.getId());
            return RestExecutorFactory.createRestExecutor(RestMethod.GET).execute(restParameters, new RestResponseType<NodeJson>(){});
        }
        return new NodeJson();
    }

    @Override
    public void updateNode(NodeUpdateParameters parameters) {
        if (parameters != null) {
            RestParameters restParameters = new RestParameters(dockerWebClient.getBaseResource())
                    .setPath(NODES_PATH + "/" + parameters.getId() + "/" + UPDATE_PATH)
                    .setRequestBody(parameters.getNode())
                    .addQueryParam(parameters.getVersionQueryParam());
            RestExecutorFactory.createRestExecutor(RestMethod.POST).execute(restParameters, new RestResponseType<Void>(){});
        }
    }

    @Override
    public void deleteNode(NodeDeleteParameters parameters) {
        if (parameters != null) {
            RestParameters restParameters = new RestParameters(dockerWebClient.getBaseResource())
                    .setPath(NODES_PATH + "/" + parameters.getId())
                    .addQueryParam(parameters.getForceQueryParam());
            RestExecutorFactory.createRestExecutor(RestMethod.DELETE).execute(restParameters, new RestResponseType<Void>(){});
        }
    }
}

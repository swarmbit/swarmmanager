package com.swarmmanager.docker.api.nodes;

import com.swarmmanager.docker.api.common.AbstractApiImpl;
import com.swarmmanager.docker.api.nodes.parameters.NodeDeleteParameters;
import com.swarmmanager.docker.api.nodes.parameters.NodeUpdateParameters;
import com.swarmmanager.docker.api.nodes.parameters.NodesListParameters;
import com.swarmmanager.docker.api.common.json.NodeJson;
import com.swarmmanager.rest.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NodesApiImpl extends AbstractApiImpl implements NodesApi {

    private static final String NODES_PATH = "/nodes";

    private static final String UPDATE_PATH = "/update";

    @Override
    public List<NodeJson> listNodes(String swarmId, NodesListParameters parameters) {
        return listObjects(NODES_PATH, swarmId, new RestResponseType<List<NodeJson>>() {}, parameters);
    }

    @Override
    public NodeJson inspectNode(String swarmId, String id) {
        return  inspectObject(NODES_PATH, swarmId, new RestResponseType<NodeJson>() {}, id);
    }

    @Override
    public void updateNode(String swarmId, String id, NodeUpdateParameters parameters) {
        updateObject(NODES_PATH + "/" + id + UPDATE_PATH, swarmId, new RestResponseType<Void>() {}, parameters, parameters);
    }

    @Override
    public void deleteNode(String swarmId, String id, NodeDeleteParameters parameters) {
        deleteObject(NODES_PATH + "/" + id, swarmId, new RestResponseType<Void>(){}, parameters);
    }

}

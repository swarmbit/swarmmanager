package com.swarmmanager.docker.api.nodes;

import com.swarmmanager.docker.api.nodes.parameters.NodeDeleteParameters;
import com.swarmmanager.docker.api.nodes.parameters.NodeUpdateParameters;
import com.swarmmanager.docker.api.nodes.parameters.NodesListParameters;
import com.swarmmanager.docker.api.common.json.NodeJson;

import java.util.List;

public interface NodesApi {

    List<NodeJson> listNodes(String swarmId, NodesListParameters parameters);

    NodeJson inspectNode(String swarmId, String id);

    void updateNode(String swarmId, String id, NodeUpdateParameters parameters);

    void deleteNode(String swarmId, String id, NodeDeleteParameters parameters);

}

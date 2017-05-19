package com.swarmmanager.docker.api.nodes;

import com.swarmmanager.docker.api.nodes.parameters.NodeDeleteParameters;
import com.swarmmanager.docker.api.nodes.parameters.NodeInspectParameters;
import com.swarmmanager.docker.api.nodes.parameters.NodeUpdateParameters;
import com.swarmmanager.docker.api.nodes.parameters.NodesListParameters;
import com.swarmmanager.docker.api.common.json.NodeJson;

import java.util.List;

public interface NodesApi {

    List<NodeJson> listNodes(NodesListParameters parameters);

    NodeJson inspectNode(NodeInspectParameters parameters);

    void updateNode(NodeUpdateParameters parameters);

    void deleteNode(NodeDeleteParameters parameters);

}

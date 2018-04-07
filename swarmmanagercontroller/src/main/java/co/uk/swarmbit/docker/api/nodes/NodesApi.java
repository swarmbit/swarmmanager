package co.uk.swarmbit.docker.api.nodes;

import co.uk.swarmbit.docker.api.nodes.parameters.NodeDeleteParameters;
import co.uk.swarmbit.docker.api.nodes.parameters.NodeUpdateParameters;
import co.uk.swarmbit.docker.api.nodes.parameters.NodesListParameters;
import co.uk.swarmbit.docker.api.common.json.NodeJson;

import java.util.List;

public interface NodesApi {

    List<NodeJson> listNodes(String swarmId, NodesListParameters parameters);

    NodeJson inspectNode(String swarmId, String id);

    void updateNode(String swarmId, String id, NodeUpdateParameters parameters);

    void deleteNode(String swarmId, String id, NodeDeleteParameters parameters);

}

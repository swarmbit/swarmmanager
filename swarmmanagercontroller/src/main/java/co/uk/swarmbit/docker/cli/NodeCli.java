package co.uk.swarmbit.docker.cli;

import co.uk.swarmbit.docker.cli.model.Node;
import co.uk.swarmbit.docker.cli.model.NodeSummary;
import co.uk.swarmbit.docker.cli.model.State;

import java.util.List;

public interface NodeCli {

    List<NodeSummary> ls(String swarmId);

    State ps(String swarmId, String nodeId);

    Node inspect(String swarmId, String nodeId);

    void rm(String swarmId, String nodeId, boolean force);

    void update(String swarmId, String nodeId, Node node);

}

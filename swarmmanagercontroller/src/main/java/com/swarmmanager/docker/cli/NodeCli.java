package com.swarmmanager.docker.cli;

import com.swarmmanager.docker.cli.model.Node;
import com.swarmmanager.docker.cli.model.NodeState;
import com.swarmmanager.docker.cli.model.NodeSummary;

import java.util.List;

public interface NodeCli {

    List<NodeSummary> ls();

    NodeState ps(String nodeId);

    Node inspect(String nodeId);

    void rm(String nodeId);

    void update(String nodeId, Node node);

}

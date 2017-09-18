package com.swarmmanager.docker.cli;

import com.swarmmanager.docker.cli.model.Node;
import com.swarmmanager.docker.cli.model.NodeSummary;
import com.swarmmanager.docker.cli.model.State;

import java.util.List;

public interface NodeCli {

    List<NodeSummary> ls();

    State ps(String nodeId);

    Node inspect(String nodeId);

    void rm(String nodeId, boolean force);

    void update(String nodeId, Node node);

}

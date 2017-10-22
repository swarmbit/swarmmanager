package com.swarmmanager.docker.cli;

import com.swarmmanager.docker.cli.model.Node;
import com.swarmmanager.docker.cli.model.NodeSummary;
import com.swarmmanager.docker.cli.model.State;

import java.util.List;

public interface NodeCli {

    List<NodeSummary> ls(String swarmId);

    State ps(String swarmId, String nodeId);

    Node inspect(String swarmId, String nodeId);

    void rm(String swarmId, String nodeId, boolean force);

    void update(String swarmId, String nodeId, Node node);

}

package com.swarmmanager.docker.cli;

import com.swarmmanager.docker.cli.model.Network;
import com.swarmmanager.docker.cli.model.NetworkSummary;

import java.util.List;

public interface NetworkCli {

    Network create(String swarmId, Network network);

    List<NetworkSummary> ls(String swarmId);

    Network inspect(String swarmId, String networkId);

    void rm(String swarmId, String networkId);

}

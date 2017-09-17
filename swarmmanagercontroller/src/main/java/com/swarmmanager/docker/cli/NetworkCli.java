package com.swarmmanager.docker.cli;

import com.swarmmanager.docker.cli.model.Network;
import com.swarmmanager.docker.cli.model.NetworkSummary;

import java.util.List;

public interface NetworkCli {

    Network create(Network network);

    List<NetworkSummary> ls();

    Network inspect(String networkId);

    void rm(String networkId);

}

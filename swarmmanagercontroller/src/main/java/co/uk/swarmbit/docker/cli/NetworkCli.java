package co.uk.swarmbit.docker.cli;

import co.uk.swarmbit.docker.cli.model.NetworkSummary;
import co.uk.swarmbit.docker.cli.model.Network;

import java.util.List;

public interface NetworkCli {

    Network create(String swarmId, Network network);

    List<NetworkSummary> ls(String swarmId);

    Network inspect(String swarmId, String networkId);

    void rm(String swarmId, String networkId);

}

package com.swarmmanager.docker.cli.impl;

import com.swarmmanager.docker.cli.NetworkCli;
import com.swarmmanager.docker.cli.model.Network;
import com.swarmmanager.docker.cli.model.NetworkSummary;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NetworkCliImpl implements NetworkCli {

    @Override
    public Network create(Network network) {
        return null;
    }

    @Override
    public List<NetworkSummary> ls() {
        return null;
    }

    @Override
    public Network inspect(String networkId) {
        return null;
    }

    @Override
    public void rm(String networkId) {

    }

}

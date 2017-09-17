package com.swarmmanager.docker.cli;

import com.swarmmanager.docker.cli.model.Swarm;

public interface SwarmCli {

    void update(Swarm swarm);

    Swarm inspect();

}

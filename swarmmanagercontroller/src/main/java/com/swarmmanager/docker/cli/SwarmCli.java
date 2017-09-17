package com.swarmmanager.docker.cli;

import com.swarmmanager.docker.cli.model.Swarm;
import com.swarmmanager.docker.cli.model.Unlock;

public interface SwarmCli {

    void update(Swarm swarm);

    Swarm inspect();

    Unlock unlock();

    void rotate();

}

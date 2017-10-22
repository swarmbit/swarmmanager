package com.swarmmanager.docker.cli;

import com.swarmmanager.docker.cli.model.Swarm;
import com.swarmmanager.docker.cli.model.Unlock;

public interface SwarmCli {

    void update(String swarmId, Swarm swarm);

    Swarm inspect(String swarmId);

    Unlock unlock(String swarmId);

    void rotate(String swarmId);

}

package co.uk.swarmbit.docker.cli;

import co.uk.swarmbit.docker.cli.model.Swarm;
import co.uk.swarmbit.docker.cli.model.Unlock;

public interface SwarmCli {

    void update(String swarmId, Swarm swarm);

    Swarm inspect(String swarmId);

    Unlock unlock(String swarmId);

    void rotate(String swarmId);

}

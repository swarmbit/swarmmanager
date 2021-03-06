package co.uk.swarmbit.docker.cli;

import co.uk.swarmbit.docker.cli.model.Config;

import java.util.List;

public interface ConfigCli {

    Config create(String swarmId, Config config);

    void rm(String swarmId, String configId);

    List<Config> ls(String swarmId);

    Config inspect(String swarmId, String configId);

    void update(String swarmId, String configId, Config config);

}

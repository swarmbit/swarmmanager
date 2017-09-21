package com.swarmmanager.docker.cli;

import com.swarmmanager.docker.cli.model.Config;

import java.util.List;

public interface ConfigCli {

    Config create(Config config);

    void rm(String configId);

    List<Config> ls();

    Config inspect(String configId);

    void update(String configId, Config config);

}

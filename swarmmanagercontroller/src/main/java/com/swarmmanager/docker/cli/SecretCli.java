package com.swarmmanager.docker.cli;

import com.swarmmanager.docker.cli.model.Secret;

import java.util.List;

public interface SecretCli {

    Secret create(Secret secret);

    void rm(String secretId);

    List<Secret> ls();

    Secret inspect(String secretId);

    void update(String secretId, Secret secret);

}

package co.uk.swarmbit.docker.cli;

import co.uk.swarmbit.docker.cli.model.Secret;

import java.util.List;

public interface SecretCli {

    Secret create(String swarmId, Secret secret);

    void rm(String swarmId, String secretId);

    List<Secret> ls(String swarmId);

    Secret inspect(String swarmId, String secretId);

    void update(String swarmId, String secretId, Secret secret);

}

package com.swarmmanager.docker.cli;

import com.swarmmanager.docker.cli.model.Secret;

import java.util.List;

public interface SecretCli {
    //secretJson.setData(EncoderDecoder.base64URLEncode(secretJson.getData()));
    Secret create(Secret secret);

    void rm(String secretId);

    List<Secret> ls();

    Secret inspect(String secretId);

}

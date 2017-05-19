package com.swarmmanager.docker.api.secrets.parameters;

import com.swarmmanager.docker.api.common.json.SecretSpecJson;

public class SecretCreateParameters {

    private SecretSpecJson secret;

    public SecretCreateParameters() {
        secret = new SecretSpecJson();
    }

    public SecretSpecJson getSecret() {
        return secret;
    }

    public void setSecret(SecretSpecJson secret) {
        this.secret = secret;
    }
}

package com.swarmmanager.docker.api.secrets;

import com.swarmmanager.docker.api.common.json.SecretCreateResponseJson;
import com.swarmmanager.docker.api.common.json.SecretJson;
import com.swarmmanager.docker.api.secrets.parameters.SecretCreateParameters;
import com.swarmmanager.docker.api.secrets.parameters.SecretUpdateParameters;
import com.swarmmanager.docker.api.secrets.parameters.SecretsListParameters;

import java.util.List;

public interface SecretsApi {

    List<SecretJson> listSecrets(String swarmId, SecretsListParameters parameters);

    SecretJson inspectSecret(String swarmId, String id);

    SecretCreateResponseJson createSecret(String swarmId, SecretCreateParameters parameters);

    void deleteSecret(String swarmId, String id);

    void updateSecret(String swarmId, String id, SecretUpdateParameters parameters);
}

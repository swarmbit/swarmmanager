package com.swarmmanager.docker.api.secrets;

import com.swarmmanager.docker.api.common.json.SecretCreateResponseJson;
import com.swarmmanager.docker.api.common.json.SecretJson;
import com.swarmmanager.docker.api.secrets.parameters.SecretCreateParameters;
import com.swarmmanager.docker.api.secrets.parameters.SecretUpdateParameters;
import com.swarmmanager.docker.api.secrets.parameters.SecretsFiltersParameters;

import java.util.List;

public interface SecretsApi {

    List<SecretJson> listSecrets(SecretsFiltersParameters parameters);

    SecretJson inspectSecret(String id);

    SecretCreateResponseJson createSecret(SecretCreateParameters parameters);

    void deleteSecret(String id);

    void updateSecret(String id, SecretUpdateParameters parameters);
}

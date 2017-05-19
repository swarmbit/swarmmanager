package com.swarmmanager.docker.api.secrets;

import com.swarmmanager.docker.api.common.json.SecretCreateResponseJson;
import com.swarmmanager.docker.api.common.json.SecretJson;
import com.swarmmanager.docker.api.secrets.parameters.SecretCreateParameters;
import com.swarmmanager.docker.api.secrets.parameters.SecretDeleteParameters;
import com.swarmmanager.docker.api.secrets.parameters.SecretInspectParameters;
import com.swarmmanager.docker.api.secrets.parameters.SecretUpdateParameters;
import com.swarmmanager.docker.api.secrets.parameters.SecretsListParameters;

import java.util.List;

public interface SecretsApi {

    List<SecretJson> listSecrets(SecretsListParameters parameters);

    SecretCreateResponseJson createSecret(SecretCreateParameters parameters);

    SecretJson inspectSecret(SecretInspectParameters parameters);

    void deleteSecret(SecretDeleteParameters parameters);

    void updateSecret(SecretUpdateParameters parameters);
}

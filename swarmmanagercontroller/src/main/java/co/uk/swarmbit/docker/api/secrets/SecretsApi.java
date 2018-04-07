package co.uk.swarmbit.docker.api.secrets;

import co.uk.swarmbit.docker.api.common.json.SecretCreateResponseJson;
import co.uk.swarmbit.docker.api.common.json.SecretJson;
import co.uk.swarmbit.docker.api.secrets.parameters.SecretCreateParameters;
import co.uk.swarmbit.docker.api.secrets.parameters.SecretUpdateParameters;
import co.uk.swarmbit.docker.api.secrets.parameters.SecretsListParameters;

import java.util.List;

public interface SecretsApi {

    List<SecretJson> listSecrets(String swarmId, SecretsListParameters parameters);

    SecretJson inspectSecret(String swarmId, String id);

    SecretCreateResponseJson createSecret(String swarmId, SecretCreateParameters parameters);

    void deleteSecret(String swarmId, String id);

    void updateSecret(String swarmId, String id, SecretUpdateParameters parameters);
}

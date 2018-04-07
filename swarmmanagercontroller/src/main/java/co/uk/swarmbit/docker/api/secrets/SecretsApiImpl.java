package co.uk.swarmbit.docker.api.secrets;

import co.uk.swarmbit.docker.api.common.AbstractApiImpl;
import co.uk.swarmbit.docker.api.common.json.SecretCreateResponseJson;
import co.uk.swarmbit.docker.api.secrets.parameters.SecretCreateParameters;
import co.uk.swarmbit.docker.api.secrets.parameters.SecretUpdateParameters;
import co.uk.swarmbit.docker.api.secrets.parameters.SecretsListParameters;
import co.uk.swarmbit.rest.RestResponseType;
import co.uk.swarmbit.docker.api.common.json.SecretJson;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SecretsApiImpl extends AbstractApiImpl implements SecretsApi {

    private static final String SECRETS_PATH = "/secrets";

    private static final String CREATE_PATH = "/create";

    private static final String UPDATE_PATH = "/update";

    @Override
    public List<SecretJson> listSecrets(String swarmId, SecretsListParameters parameters) {
        return listObjects(SECRETS_PATH, swarmId, new RestResponseType<List<SecretJson>>() {}, parameters);
    }

    @Override
    public SecretJson inspectSecret(String swarmId, String id) {
        return inspectObject(SECRETS_PATH, swarmId, new RestResponseType<SecretJson>() {}, id);
    }

    @Override
    public SecretCreateResponseJson createSecret(String swarmId, SecretCreateParameters parameters) {
        return createObject(SECRETS_PATH + CREATE_PATH, swarmId, new RestResponseType<SecretCreateResponseJson>() {}, parameters);
    }

    @Override
    public void deleteSecret(String swarmId, String id) {
        deleteObject(SECRETS_PATH + "/" + id, swarmId, new RestResponseType<Void>() {});
    }

    @Override
    public void updateSecret(String swarmId, String id, SecretUpdateParameters parameters) {
        updateObject(SECRETS_PATH + "/" + id + UPDATE_PATH, swarmId, new RestResponseType<Void>() {}, parameters, parameters);
    }
}

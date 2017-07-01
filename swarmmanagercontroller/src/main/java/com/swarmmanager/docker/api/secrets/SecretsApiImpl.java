package com.swarmmanager.docker.api.secrets;

import com.swarmmanager.docker.api.common.AbstractApiImpl;
import com.swarmmanager.docker.api.common.json.SecretCreateResponseJson;
import com.swarmmanager.docker.api.common.json.SecretJson;
import com.swarmmanager.docker.api.secrets.parameters.SecretCreateParameters;
import com.swarmmanager.docker.api.secrets.parameters.SecretUpdateParameters;
import com.swarmmanager.docker.api.secrets.parameters.SecretsFiltersParameters;
import com.swarmmanager.rest.RestResponseType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SecretsApiImpl extends AbstractApiImpl implements SecretsApi {

    private static final String SECRETS_PATH = "/secrets";

    private static final String CREATE_PATH = "/create";

    private static final String UPDATE_PATH = "/update";

    @Override
    public List<SecretJson> listSecrets(SecretsFiltersParameters parameters) {
        return listObjects(SECRETS_PATH, new RestResponseType<List<SecretJson>>() {}, parameters);
    }

    @Override
    public SecretJson inspectSecret(String id) {
        return inspectObject(SECRETS_PATH, new RestResponseType<SecretJson>() {}, id);
    }

    @Override
    public SecretCreateResponseJson createSecret(SecretCreateParameters parameters) {
        return createObject(SECRETS_PATH + "/" + CREATE_PATH, new RestResponseType<SecretCreateResponseJson>() {}, parameters);
    }

    @Override
    public void deleteSecret(String id) {
        deleteObject(SECRETS_PATH + "/" + id, new RestResponseType<Void>() {});
    }

    @Override
    public void updateSecret(String id, SecretUpdateParameters parameters) {
        updateObject(SECRETS_PATH + "/" + id + "/" + UPDATE_PATH, new RestResponseType<Void>() {}, parameters, parameters);
    }
}

package com.swarmmanager.docker.api.secrets;

import com.swarmmanager.docker.api.common.client.DockerWebClient;
import com.swarmmanager.docker.api.common.json.SecretCreateResponseJson;
import com.swarmmanager.docker.api.common.json.SecretJson;
import com.swarmmanager.docker.api.secrets.parameters.SecretCreateParameters;
import com.swarmmanager.docker.api.secrets.parameters.SecretDeleteParameters;
import com.swarmmanager.docker.api.secrets.parameters.SecretInspectParameters;
import com.swarmmanager.docker.api.secrets.parameters.SecretUpdateParameters;
import com.swarmmanager.docker.api.secrets.parameters.SecretsListParameters;
import com.swarmmanager.rest.QueryParam;
import com.swarmmanager.rest.RestExecutorFactory;
import com.swarmmanager.rest.RestMethod;
import com.swarmmanager.rest.RestParameters;
import com.swarmmanager.rest.RestResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class SecretsApiImpl implements SecretsApi {

    private static final String SECRETS_PATH = "/secrets";
    private static final String CREATE_PATH = "/create";
    private static final String UPDATE_PATH = "/update";

    @Autowired
    private DockerWebClient dockerWebClient;

    @Override
    public List<SecretJson> listSecrets(SecretsListParameters parameters) {
        if (parameters != null) {
            RestParameters restParameters = new RestParameters(dockerWebClient.getBaseResource())
                    .setPath(SECRETS_PATH);
            Optional<QueryParam> filters = parameters.getFilters();
            if (filters.isPresent()) {
                restParameters.addQueryParam(filters.get());
            }
            return RestExecutorFactory.createRestExecutor(RestMethod.GET).execute(restParameters, new RestResponseType<List<SecretJson>>() {});
        }
        return new ArrayList<>();
    }

    @Override
    public SecretCreateResponseJson createSecret(SecretCreateParameters parameters) {
        if (parameters != null) {
            RestParameters restParameters = new RestParameters(dockerWebClient.getBaseResource())
                    .setPath(SECRETS_PATH + CREATE_PATH)
                    .setRequestBody(parameters.getSecret());

            return RestExecutorFactory.createRestExecutor(RestMethod.POST).execute(restParameters, new RestResponseType<SecretCreateResponseJson>(){});
        }
        SecretCreateResponseJson response = new SecretCreateResponseJson();
        return response;
    }

    @Override
    public SecretJson inspectSecret(SecretInspectParameters parameters) {
        if (parameters != null) {
            RestParameters restParameters = new RestParameters(dockerWebClient.getBaseResource())
                    .setPath(SECRETS_PATH + "/" + parameters.getId());
            return RestExecutorFactory.createRestExecutor(RestMethod.GET).execute(restParameters, new RestResponseType<SecretJson>(){});
        }
        return new SecretJson();
    }

    @Override
    public void deleteSecret(SecretDeleteParameters parameters) {
        if (parameters != null) {
            RestParameters restParameters = new  RestParameters(dockerWebClient.getBaseResource())
                    .setPath(SECRETS_PATH + "/" + parameters.getId());
            RestExecutorFactory.createRestExecutor(RestMethod.DELETE).execute(restParameters, new RestResponseType<Void>() {});
        }
    }

    @Override
    public void updateSecret(SecretUpdateParameters parameters) {
        if (parameters != null) {
            RestParameters restParameters = new RestParameters(dockerWebClient.getBaseResource())
                    .setPath(SECRETS_PATH + "/" + parameters.getId() + UPDATE_PATH)
                    .addQueryParam(parameters.getVersionQueryParam());
            RestExecutorFactory.createRestExecutor(RestMethod.POST).execute(restParameters, new RestResponseType<Void>() {});
        }
    }
}

package com.swarmmanager.api;

import com.swarmmanager.docker.api.common.json.SecretCreateResponseJson;
import com.swarmmanager.docker.api.common.json.SecretJson;
import com.swarmmanager.docker.api.common.json.SecretSpecJson;
import com.swarmmanager.docker.api.secrets.SecretsApi;
import com.swarmmanager.docker.api.secrets.parameters.SecretCreateParameters;
import com.swarmmanager.docker.api.secrets.parameters.SecretDeleteParameters;
import com.swarmmanager.docker.api.secrets.parameters.SecretInspectParameters;
import com.swarmmanager.docker.api.secrets.parameters.SecretUpdateParameters;
import com.swarmmanager.docker.api.secrets.parameters.SecretsListParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/secrets")
public class SecretController {

    @Autowired
    private SecretsApi secretsApi;

    @RequestMapping(method = RequestMethod.GET, value = "list", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<SecretJson> listSecrets() {
        return secretsApi.listSecrets(new SecretsListParameters());
    }

    @RequestMapping(method = RequestMethod.POST, value = "create", produces = {MediaType.APPLICATION_JSON_VALUE})
    public SecretCreateResponseJson createSecret(@RequestBody SecretSpecJson secretJson) {
        SecretCreateParameters parameters = new SecretCreateParameters();
        parameters.setSecret(secretJson);
        return secretsApi.createSecret(parameters);
    }

    @RequestMapping(method = RequestMethod.GET, value = "inspect/{secretId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public SecretJson inspectSecret(@PathVariable String secretId) {
        SecretInspectParameters parameters = new SecretInspectParameters();
        parameters.setId(secretId);
        return secretsApi.inspectSecret(parameters);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "delete/{secretId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteSecret(@PathVariable String secretId) {
        SecretDeleteParameters parameters = new SecretDeleteParameters();
        parameters.setId(secretId);
        secretsApi.deleteSecret(parameters);
    }
    @RequestMapping(method = RequestMethod.PUT, value = "update/{secretId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void updateSecret(@PathVariable String secretId) {
        SecretUpdateParameters parameters = new SecretUpdateParameters();
        parameters.setId(secretId);
        secretsApi.updateSecret(parameters);
    }
}

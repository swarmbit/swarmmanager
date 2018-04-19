package co.uk.swarmbit.docker.cli.impl;

import co.uk.swarmbit.docker.api.common.json.SecretCreateResponseJson;
import co.uk.swarmbit.docker.api.common.json.SecretJson;
import co.uk.swarmbit.docker.api.common.json.SecretSpecJson;
import co.uk.swarmbit.docker.api.common.json.inner.DriverJson;
import co.uk.swarmbit.docker.api.common.json.inner.VersionJson;
import co.uk.swarmbit.docker.api.common.util.DockerDateFormatter;
import co.uk.swarmbit.docker.api.secrets.SecretsApi;
import co.uk.swarmbit.docker.api.secrets.parameters.SecretCreateParameters;
import co.uk.swarmbit.docker.api.secrets.parameters.SecretUpdateParameters;
import co.uk.swarmbit.docker.api.secrets.parameters.SecretsListParameters;
import co.uk.swarmbit.docker.cli.SecretCli;
import co.uk.swarmbit.docker.cli.model.Secret;
import co.uk.swarmbit.util.EncoderDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class SecretCliImpl implements SecretCli {

    private final SecretsApi secretsApi;

    @Autowired
    public SecretCliImpl(SecretsApi secretsApi) {
        this.secretsApi = secretsApi;
    }

    @Override
    public Secret create(String swarmId, Secret secret) {
        SecretSpecJson secretSpecJson = new SecretSpecJson();
        secretSpecJson.setName(secret.getName());
        secretSpecJson.setLabels(secret.getLabels());
        secretSpecJson.setData(EncoderDecoder.base64Encode(secret.getData()));
        DriverJson templating = new DriverJson();
        templating.setName(secret.getTemplatingName());
        templating.setOptions(secret.getTemplatingOptions());
        secretSpecJson.setTemplating(templating);
        DriverJson driver = new DriverJson();
        driver.setName(secret.getDriverName());
        driver.setOptions(secret.getDriverOptions());
        secretSpecJson.setTemplating(driver);
        SecretCreateResponseJson response = secretsApi.createSecret(swarmId, new SecretCreateParameters()
                .setSecret(secretSpecJson));
        secret.setId(response.getId());
        return secret;
    }

    @Override
    public void rm(String swarmId, String secretId) {
        secretsApi.deleteSecret(swarmId, secretId);
    }

    @Override
    public List<Secret> ls(String swarmId) {
        List<Secret> secrets = new ArrayList<>();
        List<SecretJson> secretJsons = secretsApi.listSecrets(swarmId, new SecretsListParameters());
        secretJsons.forEach(secretJson -> secrets.add(fromSecretJson(secretJson)));
        return secrets;
    }

    @Override
    public Secret inspect(String swarmId, String secretId) {
        SecretJson secretJson = secretsApi.inspectSecret(swarmId, secretId);
        return fromSecretJson(secretJson);
    }

    @Override
    public void update(String swarmId, String secretId, Secret secret) {
        SecretJson secretJson = secretsApi.inspectSecret(swarmId, secretId);
        VersionJson versionJson = secretJson.getVersion();
        SecretUpdateParameters parameters = new SecretUpdateParameters();
        parameters.setVersionQueryParam(versionJson.getIndex());
        if (secret.getLabels() != null) {
            secretJson.getSpec().setLabels(secret.getLabels());
        }
        parameters.setSecrect(secretJson.getSpec());
        secretsApi.updateSecret(swarmId, secretId, parameters);
    }

    private Secret fromSecretJson(SecretJson secretJson) {
        Secret secret = new Secret();
        secret.setId(secretJson.getId());
        ZonedDateTime createdAt = DockerDateFormatter.fromDateStringToZonedDateTime(secretJson.getCreatedAt());
        ZonedDateTime updatedAt = DockerDateFormatter.fromDateStringToZonedDateTime(secretJson.getUpdatedAt());
        secret.setCreatedAt(createdAt.toInstant().toEpochMilli());
        secret.setUpdatedAt(updatedAt.toInstant().toEpochMilli());
        secret.setName(secretJson.getSpec().getName());
        secret.setLabels(secretJson.getSpec().getLabels());
        secret.setData(EncoderDecoder.base64URLDecode(secretJson.getSpec().getData()));
        DriverJson templating = secretJson.getSpec().getTemplating();
        if (templating != null) {
            secret.setTemplatingName(templating.getName());
            secret.setTemplatingOptions(templating.getOptions());
        }
        DriverJson driver = secretJson.getSpec().getDriver();
        if (driver != null) {
            secret.setDriverName(driver.getName());
            secret.setDriverOptions(driver.getOptions());
        }
        return secret;
    }
}

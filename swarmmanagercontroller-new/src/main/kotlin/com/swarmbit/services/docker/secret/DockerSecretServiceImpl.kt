package com.swarmbit.services.docker.secret

import com.swarmbit.docker.api.secrets.SecretsApi
import com.swarmbit.docker.api.secrets.model.SecretSpec
import com.swarmbit.docker.api.secrets.parameters.SecretsCreateParameters
import com.swarmbit.docker.api.secrets.parameters.SecretsUpdateParameters
import com.swarmbit.services.docker.secret.model.DockerSecret
import com.swarmbit.services.docker.secret.model.DockerSecretCreate
import com.swarmbit.services.docker.secret.model.DockerSecretUpdate
import com.swarmbit.services.docker.secret.model.toDockerSecret
import com.swarmbit.services.docker.secret.model.toSecretSpec
import io.micronaut.context.annotation.Context

@Context
class DockerSecretServiceImpl(
    private val secretsApi: SecretsApi
) : DockerSecretService {

    override fun create(swarmId: String, dockerSecretCreate: DockerSecretCreate): DockerSecret =
        secretsApi.createSecret(
            swarmId,
            SecretsCreateParameters().setSecret(dockerSecretCreate.toSecretSpec())
        ).let {
            secretsApi.inspectSecret(swarmId, it.id.orEmpty()).toDockerSecret()
        }

    override fun rm(swarmId: String, secretId: String) =
        secretsApi.deleteSecret(swarmId, secretId)

    override fun ls(swarmId: String): List<DockerSecret> =
        secretsApi.listSecrets(swarmId).map { it.toDockerSecret() }

    override fun inspect(swarmId: String, secretId: String): DockerSecret =
        secretsApi.inspectSecret(swarmId, secretId).toDockerSecret()

    override fun update(swarmId: String, secretId: String, dockerSecretUpdate: DockerSecretUpdate): Unit =
        secretsApi.inspectSecret(swarmId, secretId).let {
            secretsApi.updateSecret(
                swarmId,
                secretId,
                SecretsUpdateParameters()
                    .setVersionQueryParam(it.version?.index ?: 0 + 1L)
                    .setSecret(dockerSecretUpdate.toSecretSpec(it.spec ?: SecretSpec()))
            )
        }
}

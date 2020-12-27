package com.swarmbit.docker.api.secrets

import com.swarmbit.docker.api.common.AbstractApiImpl
import com.swarmbit.docker.api.common.client.DockerWebClient
import com.swarmbit.docker.api.common.rest.model.ResponseType
import com.swarmbit.docker.api.secrets.model.Secret
import com.swarmbit.docker.api.secrets.model.SecretCreateResponse
import com.swarmbit.docker.api.secrets.parameters.SecretsCreateParameters
import com.swarmbit.docker.api.secrets.parameters.SecretsListParameters
import com.swarmbit.docker.api.secrets.parameters.SecretsUpdateParameters
import io.micronaut.context.annotation.Context

@Context
class SecretsApiImpl(dockerWebClient: DockerWebClient): AbstractApiImpl(dockerWebClient), SecretsApi {

    companion object {
        private const val SECRETS_PATH = "/secrets"
        private const val CREATE_PATH = "/create"
        private const val UPDATE_PATH = "/update"
    }

    override fun listSecrets(swarmId: String, parameters: SecretsListParameters): List<Secret> {
        return listObjects(SECRETS_PATH, swarmId, object : ResponseType<List<Secret>>() {}, parameters)
    }

    override fun inspectSecret(swarmId: String, id: String): Secret {
        return inspectObject(SECRETS_PATH, swarmId, object : ResponseType<Secret>() {}, id)
                ?: throw IllegalArgumentException("No secret found for swarmId ($swarmId) and configId (${id})")
    }

    override fun createSecret(swarmId: String, parameters: SecretsCreateParameters): SecretCreateResponse {
        return createObject(SECRETS_PATH + CREATE_PATH, swarmId, object : ResponseType<SecretCreateResponse>() {}, parameters)!!
    }

    override fun deleteSecret(swarmId: String, id: String) {
        deleteObject("$SECRETS_PATH/$id", swarmId, object : ResponseType<Void>() {})
    }

    override fun updateSecret(swarmId: String, id: String, parameters: SecretsUpdateParameters) {
        updateObject("$SECRETS_PATH/$id$UPDATE_PATH", swarmId, object : ResponseType<Void>() {}, parameters, parameters)

    }

}
package com.swarmbit.docker.api.secrets

import com.swarmbit.docker.api.secrets.model.Secret
import com.swarmbit.docker.api.secrets.model.SecretCreateResponse
import com.swarmbit.docker.api.secrets.parameters.SecretsCreateParameters
import com.swarmbit.docker.api.secrets.parameters.SecretsListParameters
import com.swarmbit.docker.api.secrets.parameters.SecretsUpdateParameters

interface SecretsApi {
    fun listSecrets(swarmId: String, parameters: SecretsListParameters): List<Secret>
    fun inspectSecret(swarmId: String, id: String): Secret
    fun createSecret(swarmId: String, parameters: SecretsCreateParameters): SecretCreateResponse
    fun deleteSecret(swarmId: String, id: String)
    fun updateSecret(swarmId: String, id: String, parameters: SecretsUpdateParameters)
}

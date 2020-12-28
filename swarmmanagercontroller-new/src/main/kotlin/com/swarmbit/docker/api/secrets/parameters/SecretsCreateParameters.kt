package com.swarmbit.docker.api.secrets.parameters

import com.swarmbit.docker.api.common.parameters.RequestBodyParameter
import com.swarmbit.docker.api.secrets.model.SecretSpec

class SecretsCreateParameters : RequestBodyParameter {

    private var secret: SecretSpec = SecretSpec()

    fun setSecret(secret: SecretSpec): SecretsCreateParameters {
        this.secret = secret
        return this
    }

    override val requestBody: Any
        get() = secret
}

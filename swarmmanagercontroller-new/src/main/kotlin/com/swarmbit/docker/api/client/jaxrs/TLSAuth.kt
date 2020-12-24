package com.swarmbit.docker.api.client.jaxrs

import com.swarmbit.docker.api.config.DockerClientConfig
import com.swarmbit.docker.api.exception.UnsupportedConfiguration

enum class TLSAuth {
    TLS, TLS_AUTHENTICATE_SERVER, TLS_AUTHENTICATE_CLIENT, TLS_AUTHENTICATE_SERVER_CLIENT;

    companion object {
        fun getTLSMode(config: DockerClientConfig): TLSAuth {
            if (config.tlsMode == "tls") {
                return if (config.tlsCacert != null && config.tlsKey != null) {
                    TLS_AUTHENTICATE_CLIENT
                } else TLS
            } else if (config.tlsMode == "tlsverify") {
                if (config.tlsCacert != null) {
                    return if (config.tlsCert != null && config.tlsKey != null) {
                        TLS_AUTHENTICATE_SERVER_CLIENT
                    } else TLS_AUTHENTICATE_SERVER
                }
                throw UnsupportedConfiguration("Should provide tlscacert when using tlsverify")
            }
            throw UnsupportedConfiguration("TLS mode should be tls or tlsverify")
        }
    }
}

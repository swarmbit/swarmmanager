package com.swarmmanager.docker.api.common.client.jaxrs.tls;

import com.swarmmanager.docker.api.common.client.DockerWebClientProperties;
import com.swarmmanager.exception.UnsupportedConfiguration;
import org.apache.commons.lang3.StringUtils;

public enum TLSAuth {
    TLS,
    TLS_AUTHENTICATE_SERVER,
    TLS_AUTHENTICATE_CLIENT,
    TLS_AUTHENTICATE_SERVER_CLIENT;

    public static TLSAuth getTLSMode(DockerWebClientProperties properties){
        if (StringUtils.equals(properties.getDockerApiTlsMode(), "tls")) {
            if (properties.getDockerApiTlsCert().isPresent() && properties.getDockerApiTlsKey().isPresent()) {
                return TLS_AUTHENTICATE_CLIENT;
            }
            return TLS;
        } else if (StringUtils.equals(properties.getDockerApiTlsMode(), "tlsverify")) {
            if (properties.getDockerApiTlsCacert().isPresent()) {
                if (properties.getDockerApiTlsCert().isPresent() && properties.getDockerApiTlsKey().isPresent()) {
                    return TLS_AUTHENTICATE_SERVER_CLIENT;
                }
                return TLS_AUTHENTICATE_SERVER;
            }
            throw new UnsupportedConfiguration("Should provide tlscacert when using tlsverify");
        }
        throw new UnsupportedConfiguration("TLS mode should be tls or tlsverify");
    }
}

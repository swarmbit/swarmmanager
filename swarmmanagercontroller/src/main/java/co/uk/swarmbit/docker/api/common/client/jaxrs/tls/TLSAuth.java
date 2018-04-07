package co.uk.swarmbit.docker.api.common.client.jaxrs.tls;

import co.uk.swarmbit.docker.config.DockerClientConfig;
import co.uk.swarmbit.exception.UnsupportedConfiguration;
import org.apache.commons.lang3.StringUtils;

public enum TLSAuth {
    TLS,
    TLS_AUTHENTICATE_SERVER,
    TLS_AUTHENTICATE_CLIENT,
    TLS_AUTHENTICATE_SERVER_CLIENT;

    public static TLSAuth getTLSMode(DockerClientConfig config){
        if (StringUtils.equals(config.getTlsMode(), "tls")) {
            if (config.getTlsCert().isPresent() && config.getTlsKey().isPresent()) {
                return TLS_AUTHENTICATE_CLIENT;
            }
            return TLS;
        } else if (StringUtils.equals(config.getTlsMode(), "tlsverify")) {
            if (config.getTlsCacert().isPresent()) {
                if (config.getTlsCert().isPresent() && config.getTlsKey().isPresent()) {
                    return TLS_AUTHENTICATE_SERVER_CLIENT;
                }
                return TLS_AUTHENTICATE_SERVER;
            }
            throw new UnsupportedConfiguration("Should provide tlscacert when using tlsverify");
        }
        throw new UnsupportedConfiguration("TLS mode should be tls or tlsverify");
    }
}

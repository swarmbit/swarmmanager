package com.swarmmanager.docker.api.common.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Optional;

@ConfigurationProperties
public class DockerWebClientProperties {

    @Value("${docker.api.version:v1.27}")
    private String dockerApiVersion;

    @Value("${docker.api.protocol:unix}")
    private String dockerApiProtocol;

    @Value("${docker.api.unix.socket.path:/var/run/docker.sock}")
    private String dockerApiUnixSocketPath;

    @Value("${docker.api.address:#{null}}")
    private String dockerApiAddress;

    @Value("${docker.api.tls.mode:tls}")
    private String dockerApiTlsMode;

    @Value("${docker.api.tls.cacert:#{null}}")
    private String dockerApiTlsCacert;

    @Value("${docker.api.tls.key:#{null}}")
    private String dockerApiTlsKey;

    @Value("${docker.api.tls.cert:#{null}}")
    private String dockerApiTlsCert;

    @Value("${docker.api.read.timeout:#{null}}")
    private Integer dockerApiReadTimeout;

    @Value("${docker.api.connect.timeout:#{null}}")
    private Integer dockerApiConnectTimeout;

    @Value("${docker.api.max.total.connections:#{null}}")
    private Integer dockerApiMaxTotalConnections;

    @Value("${docker.api.max.per.route.connections:#{null}}")
    private Integer dockerApiMaxPerRouteConnections;

    @Value("${docker.api.connection.request.timeout:#{null}}")
    private Integer dockerApiConnectionRequestTimeout;

    public String getDockerApiVersion() {
        return dockerApiVersion;
    }

    public String getDockerApiProtocol() {
        return dockerApiProtocol;
    }

    public Optional<String> getDockerApiUnixSocketPath() {
        return Optional.ofNullable(dockerApiUnixSocketPath);
    }

    public Optional<String> getDockerApiAddress() {
        return Optional.ofNullable(dockerApiAddress);
    }

    public String getDockerApiTlsMode() {
        return dockerApiTlsMode;
    }

    public Optional<String> getDockerApiTlsCacert() {
        return Optional.ofNullable(dockerApiTlsCacert);
    }

    public Optional<String> getDockerApiTlsCert() {
        return Optional.ofNullable(dockerApiTlsCert);
    }

    public Optional<String> getDockerApiTlsKey() {
        return Optional.ofNullable(dockerApiTlsKey);
    }

    public Optional<Integer> getDockerApiReadTimeout() {
        return Optional.ofNullable(dockerApiReadTimeout);
    }

    public Optional<Integer> getDockerApiConnectTimeout() {
        return Optional.ofNullable(dockerApiConnectTimeout);
    }

    public Optional<Integer> getDockerApiMaxTotalConnections() {
        return Optional.ofNullable(dockerApiMaxTotalConnections);
    }

    public Optional<Integer> getDockerApiMaxPerRouteConnections() {
        return Optional.ofNullable(dockerApiMaxPerRouteConnections);
    }

    public Optional<Integer> getDockerApiConnectionRequestTimeout() {
        return Optional.ofNullable(dockerApiConnectionRequestTimeout);
    }

}

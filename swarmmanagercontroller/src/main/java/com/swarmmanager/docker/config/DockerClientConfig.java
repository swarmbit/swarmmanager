package com.swarmmanager.docker.config;


import java.util.Optional;

public class DockerClientConfig {

    private String protocol;

    private String unixSocketPath;

    private String address;

    /**
     * tls or tlsverify
     */
    private String tlsMode;

    private String tlsCacert;

    private String tlsKey;

    private String tlsCert;

    private Integer readTimeout;

    private Integer connectTimeout;

    private Integer maxTotalConnections;

    private Integer maxPerRouteConnections;

    private Integer connectionRequestTimeout;

    public DockerClientConfig() {
        //unixSocketPath = "/var/run/docker.sock";
        tlsMode = "tls";
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setUnixSocketPath(String unixSocketPath) {
        this.unixSocketPath = unixSocketPath;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTlsMode(String tlsMode) {
        this.tlsMode = tlsMode;
    }

    public void setTlsCacert(String tlsCacert) {
        this.tlsCacert = tlsCacert;
    }

    public void setTlsKey(String tlsKey) {
        this.tlsKey = tlsKey;
    }

    public void setTlsCert(String tlsCert) {
        this.tlsCert = tlsCert;
    }

    public void setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public void setMaxTotalConnections(Integer maxTotalConnections) {
        this.maxTotalConnections = maxTotalConnections;
    }

    public void setMaxPerRouteConnections(Integer maxPerRouteConnections) {
        this.maxPerRouteConnections = maxPerRouteConnections;
    }

    public void setConnectionRequestTimeout(Integer connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    public String getUnixSocketPath() {
        return unixSocketPath;
    }

    public Optional<String> getAddress() {
        return Optional.ofNullable(address);
    }

    public String getTlsMode() {
        return tlsMode;
    }

    public Optional<String> getTlsCacert() {
        return Optional.ofNullable(tlsCacert);
    }

    public Optional<String> getTlsCert() {
        return Optional.ofNullable(tlsCert);
    }

    public Optional<String> getTlsKey() {
        return Optional.ofNullable(tlsKey);
    }

    public Optional<Integer> getReadTimeout() {
        return Optional.ofNullable(readTimeout);
    }

    public Optional<Integer> getConnectTimeout() {
        return Optional.ofNullable(connectTimeout);
    }

    public Optional<Integer> getMaxTotalConnections() {
        return Optional.ofNullable(maxTotalConnections);
    }

    public Optional<Integer> getMaxPerRouteConnections() {
        return Optional.ofNullable(maxPerRouteConnections);
    }

    public Optional<Integer> getConnectionRequestTimeout() {
        return Optional.ofNullable(connectionRequestTimeout);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DockerClientConfig{");
        sb.append("protocol='").append(protocol).append('\'');
        sb.append(", unixSocketPath='").append(unixSocketPath).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", tlsMode='").append(tlsMode).append('\'');
        sb.append(", tlsCacert='").append(tlsCacert).append('\'');
        sb.append(", tlsKey='").append(tlsKey).append('\'');
        sb.append(", tlsCert='").append(tlsCert).append('\'');
        sb.append(", readTimeout=").append(readTimeout);
        sb.append(", connectTimeout=").append(connectTimeout);
        sb.append(", maxTotalConnections=").append(maxTotalConnections);
        sb.append(", maxPerRouteConnections=").append(maxPerRouteConnections);
        sb.append(", connectionRequestTimeout=").append(connectionRequestTimeout);
        sb.append('}');
        return sb.toString();
    }
}

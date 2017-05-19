package com.swarmmanager.docker.api.common.client;

import com.swarmmanager.docker.api.common.client.jaxrs.CustomPoolingHttpClientConnectionManager;
import com.swarmmanager.docker.api.common.client.jaxrs.tls.SSLContextFactory;
import com.swarmmanager.docker.api.common.client.jaxrs.unixsocket.UnixConnectionSocketFactory;
import com.swarmmanager.exception.UnsupportedConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.util.List;

public enum DockerWebClientProtocol {
    UNIX, HTTP, HTTPS;

    public void setProtocolSpecificConfiguration(ClientConfig clientConfig, DockerWebClientProperties properties) {
        switch (this) {
            case HTTP:
                configureProxy(clientConfig, properties, "http");
                break;
            case HTTPS:
                configureProxy(clientConfig, properties, "https");
                break;
        }
    }

    public PoolingHttpClientConnectionManager getConnectionManagerForProtocol(DockerWebClientProperties properties) {
        switch (this) {
            case UNIX:
                return new CustomPoolingHttpClientConnectionManager(properties, getUnixSchemeRegistry(properties));
            case HTTP:
                return new CustomPoolingHttpClientConnectionManager(properties, getHttpSchemeRegistry());
            case HTTPS:
                return new CustomPoolingHttpClientConnectionManager(properties, getHttpsSchemeRegistry(properties));
        }
        return null;
    }

    public String getUrlForProtocol(DockerWebClientProperties properties) {
        switch (this) {
            case UNIX:
                return "unix://localhost:80";
            case HTTP:
                return getUrl(properties, "http");
            case HTTPS:
                return getUrl(properties, "https");
        }
        return null;
    }

    public static DockerWebClientProtocol getDockerWebClientProtocol(String protocol) throws UnsupportedConfiguration {
        if (protocol == null) {
            throw new UnsupportedConfiguration("Protocol configured not supported");
        }
        switch (StringUtils.upperCase(protocol)) {
            case "UNIX":
                return UNIX;
            case "HTTP":
                return HTTP;
            case "HTTPS":
                return HTTPS;
            default:
                throw new UnsupportedConfiguration("Protocol configured not supported");
        }
    }

    private org.apache.http.config.Registry<ConnectionSocketFactory> getHttpSchemeRegistry() {
        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.create();
        registryBuilder.register("http", PlainConnectionSocketFactory.getSocketFactory());
        return registryBuilder.build();
    }

    private org.apache.http.config.Registry<ConnectionSocketFactory> getHttpsSchemeRegistry(DockerWebClientProperties properties) {
        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.create();
        registryBuilder.register("https", new SSLConnectionSocketFactory(SSLContextFactory.createSSLContext(properties)));
        return registryBuilder.build();
    }

    private org.apache.http.config.Registry<ConnectionSocketFactory> getUnixSchemeRegistry(DockerWebClientProperties properties) {
        if (!properties.getDockerApiUnixSocketPath().isPresent()) {
            throw new UnsupportedConfiguration("Socket Path not defined for UNIX protocol");
        }

        String socketPath = properties.getDockerApiUnixSocketPath().get();
        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.create();
        registryBuilder.register("unix", new UnixConnectionSocketFactory(socketPath));
        return registryBuilder.build();
    }

    private void configureProxy(ClientConfig clientConfig, DockerWebClientProperties properties, String protocol) {
        String address = getUrl(properties, protocol);
        List<Proxy> proxies = ProxySelector.getDefault().select(URI.create(address));
        for (Proxy proxy : proxies) {
            InetSocketAddress inetSocketaddress = (InetSocketAddress) proxy.address();
            if (inetSocketaddress != null) {
                String hostname = inetSocketaddress.getHostName();
                int port = inetSocketaddress.getPort();

                clientConfig.property(ClientProperties.PROXY_URI, "http://" + hostname + ":" + port);

                String httpProxyUser = System.getProperty(protocol + ".proxyUser");
                if (httpProxyUser != null) {
                    clientConfig.property(ClientProperties.PROXY_USERNAME, httpProxyUser);
                    String httpProxyPassword = System.getProperty(protocol + ".proxyPassword");
                    if (httpProxyPassword != null) {
                        clientConfig.property(ClientProperties.PROXY_PASSWORD, httpProxyPassword);
                    }
                }
            }
        }
    }

    private String getUrl(DockerWebClientProperties properties, String protocol) {
        if(!properties.getDockerApiAddress().isPresent()) {
            throw new UnsupportedConfiguration("Remote API address not defined for " + protocol + " protocol");
        }

        String address = properties.getDockerApiAddress().get();
        return protocol + "://" + address;
    }
}

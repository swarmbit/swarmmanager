package co.uk.swarmbit.docker.api.common.client;

import co.uk.swarmbit.docker.api.common.client.jaxrs.unixsocket.UnixConnectionSocketFactory;
import co.uk.swarmbit.docker.config.DockerClientConfig;
import co.uk.swarmbit.docker.api.common.client.jaxrs.CustomPoolingHttpClientConnectionManager;
import co.uk.swarmbit.docker.api.common.client.jaxrs.tls.SSLContextFactory;
import co.uk.swarmbit.exception.UnsupportedConfiguration;
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

    public void setProtocolSpecificConfiguration(ClientConfig clientConfig, DockerClientConfig config) {
        switch (this) {
            case HTTP:
                configureProxy(clientConfig, config, "http");
                break;
            case HTTPS:
                configureProxy(clientConfig, config, "https");
                break;
        }
    }

    public PoolingHttpClientConnectionManager getConnectionManagerForProtocol(DockerClientConfig config) {
        switch (this) {
            case UNIX:
                return new CustomPoolingHttpClientConnectionManager(config, getUnixSchemeRegistry(config));
            case HTTP:
                return new CustomPoolingHttpClientConnectionManager(config, getHttpSchemeRegistry());
            case HTTPS:
                return new CustomPoolingHttpClientConnectionManager(config, getHttpsSchemeRegistry(config));
        }
        return null;
    }

    public String getUrlForProtocol(DockerClientConfig config) {
        switch (this) {
            case UNIX:
                return "unix://localhost:80";
            case HTTP:
                return getUrl(config, "http");
            case HTTPS:
                return getUrl(config, "https");
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

    private org.apache.http.config.Registry<ConnectionSocketFactory> getHttpsSchemeRegistry(DockerClientConfig config) {
        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.create();
        registryBuilder.register("https", new SSLConnectionSocketFactory(SSLContextFactory.createSSLContext(config)));
        return registryBuilder.build();
    }

    private org.apache.http.config.Registry<ConnectionSocketFactory> getUnixSchemeRegistry(DockerClientConfig config) {
        String socketPath = config.getUnixSocketPath();
        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.create();
        registryBuilder.register("unix", new UnixConnectionSocketFactory(socketPath));
        return registryBuilder.build();
    }

    private void configureProxy(ClientConfig clientConfig, DockerClientConfig config, String protocol) {
        String address = getUrl(config, protocol);
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

    private String getUrl(DockerClientConfig config, String protocol) {
        if(!config.getAddress().isPresent()) {
            throw new UnsupportedConfiguration("Remote API address not defined for " + protocol + " protocol");
        }

        String address = config.getAddress().get();
        return protocol + "://" + address;
    }
}

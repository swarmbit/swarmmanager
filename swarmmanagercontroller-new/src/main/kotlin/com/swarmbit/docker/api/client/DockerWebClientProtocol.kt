package com.swarmbit.docker.api.client

import com.swarmbit.docker.api.client.jaxrs.CustomPoolingHttpClientConnectionManager
import com.swarmbit.docker.api.client.jaxrs.SSLContextFactory
import com.swarmbit.docker.api.client.jaxrs.unixsocket.UnixConnectionSocketFactory
import com.swarmbit.docker.api.config.DockerClientConfig
import com.swarmbit.docker.api.exception.UnsupportedConfiguration
import org.apache.http.config.Registry
import org.apache.http.config.RegistryBuilder
import org.apache.http.conn.socket.ConnectionSocketFactory
import org.apache.http.conn.socket.PlainConnectionSocketFactory
import org.apache.http.conn.ssl.SSLConnectionSocketFactory
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import org.glassfish.jersey.client.ClientConfig
import org.glassfish.jersey.client.ClientProperties
import java.net.InetSocketAddress
import java.net.ProxySelector
import java.net.URI

enum class DockerWebClientProtocol {
    UNIX, HTTP, HTTPS;

    fun setProtocolSpecificConfiguration(clientConfig: ClientConfig, config: DockerClientConfig) {
        if (this == HTTP) configureProxy(clientConfig, config, "http")
        else if (this == HTTPS) configureProxy(clientConfig, config, "https")
    }

    fun getConnectionManagerForProtocol(config: DockerClientConfig): PoolingHttpClientConnectionManager {
        return when (this) {
            UNIX -> CustomPoolingHttpClientConnectionManager(config, getUnixSchemeRegistry(config))
            HTTP -> CustomPoolingHttpClientConnectionManager(config, httpSchemeRegistry)
            HTTPS -> CustomPoolingHttpClientConnectionManager(config, getHttpsSchemeRegistry(config))
        }
    }

    fun getUrlForProtocol(config: DockerClientConfig): String {
        return when (this) {
            UNIX -> "unix://localhost:80"
            HTTP -> getUrl(config, "http")
            HTTPS -> getUrl(config, "https")
        }
    }

    private val httpSchemeRegistry: Registry<ConnectionSocketFactory>
        get() {
            val registryBuilder = RegistryBuilder.create<ConnectionSocketFactory>()
            registryBuilder.register("http", PlainConnectionSocketFactory.getSocketFactory())
            return registryBuilder.build()
        }

    private fun getHttpsSchemeRegistry(config: DockerClientConfig): Registry<ConnectionSocketFactory> {
        val registryBuilder = RegistryBuilder.create<ConnectionSocketFactory>()
        registryBuilder.register("https", SSLConnectionSocketFactory(SSLContextFactory.createSSLContext(config)))
        return registryBuilder.build()
    }

    private fun getUnixSchemeRegistry(config: DockerClientConfig): Registry<ConnectionSocketFactory> {
        val socketPath: String = config.unixSocketPath
        val registryBuilder = RegistryBuilder.create<ConnectionSocketFactory>()
        registryBuilder.register("unix", UnixConnectionSocketFactory(socketPath))
        return registryBuilder.build()
    }

    private fun configureProxy(clientConfig: ClientConfig, config: DockerClientConfig, protocol: String) {
        val address = getUrl(config, protocol)
        val proxies = ProxySelector.getDefault().select(URI.create(address))
        for (proxy in proxies) {
            val inetSocketaddress = proxy.address() as InetSocketAddress
            val hostname = inetSocketaddress.hostName
            val port = inetSocketaddress.port
            clientConfig.property(ClientProperties.PROXY_URI, "http://$hostname:$port")
            val httpProxyUser = System.getProperty("$protocol.proxyUser")
            if (httpProxyUser != null) {
                clientConfig.property(ClientProperties.PROXY_USERNAME, httpProxyUser)
                val httpProxyPassword = System.getProperty("$protocol.proxyPassword")
                if (httpProxyPassword != null) {
                    clientConfig.property(ClientProperties.PROXY_PASSWORD, httpProxyPassword)
                }
            }
        }
    }

    private fun getUrl(config: DockerClientConfig, protocol: String): String {
        if (config.address == null) {
            throw UnsupportedConfiguration("Remote API address not defined for $protocol protocol")
        }
        val address: String = config.address
        return "$protocol://$address"
    }

    companion object {
        fun getDockerWebClientProtocol(protocol: String?): DockerWebClientProtocol {
            if (protocol == null) {
                throw UnsupportedConfiguration("Protocol configured not supported")
            }
            return when (protocol.toUpperCase()) {
                "UNIX" -> UNIX
                "HTTP" -> HTTP
                "HTTPS" -> HTTPS
                else -> throw UnsupportedConfiguration("Protocol configured not supported")
            }
        }
    }
}

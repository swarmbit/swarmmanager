package com.swarmbit.docker.api.common.client

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider
import com.swarmbit.docker.api.common.client.jaxrs.filter.CustomLoggingFilter
import com.swarmbit.docker.api.common.client.jaxrs.filter.JsonClientFilter
import com.swarmbit.docker.api.common.client.jaxrs.filter.ResponseStatusExceptionFilter
import com.swarmbit.docker.api.common.config.DockerClientConfig
import com.swarmbit.docker.api.common.config.DockerConfig
import com.swarmbit.docker.api.common.config.DockerSwarmConfig
import com.swarmbit.docker.api.common.exception.SwarmNotFoundException
import com.swarmbit.docker.api.common.exception.UnsupportedConfiguration
import io.micronaut.context.annotation.Context
import org.apache.http.client.config.RequestConfig
import org.glassfish.jersey.CommonProperties
import org.glassfish.jersey.apache.connector.ApacheClientProperties
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider
import org.glassfish.jersey.client.ClientConfig
import org.glassfish.jersey.client.ClientProperties
import java.util.function.Consumer
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.client.WebTarget

@Context
class DockerWebClientImpl(dockerConfig: DockerConfig) : DockerWebClient {

    private var dockerSwarmWebTargetList: MutableList<DockerSwarmWebTarget> = mutableListOf()

    init {
        val swarms: List<DockerSwarmConfig> = dockerConfig.swarms
        if (swarms.isEmpty()) {
            throw UnsupportedConfiguration("Requires at least one swarm")
        }
        swarms.forEach(
            Consumer { swarm: DockerSwarmConfig ->
                val dockerClientConfig: DockerClientConfig = swarm.client
                val clientConfig = ClientConfig()
                clientConfig.connectorProvider(ApacheConnectorProvider())
                clientConfig.property(CommonProperties.FEATURE_AUTO_DISCOVERY_DISABLE, true)
                clientConfig.register(ResponseStatusExceptionFilter::class.java)
                clientConfig.register(JsonClientFilter::class.java)
                clientConfig.register(JacksonJsonProvider::class.java)
                clientConfig.register(CustomLoggingFilter::class.java)
                if (dockerClientConfig.readTimeout != null) {
                    clientConfig.property(ClientProperties.READ_TIMEOUT, dockerClientConfig.readTimeout)
                }
                if (dockerClientConfig.connectTimeout != null) {
                    clientConfig.property(ClientProperties.CONNECT_TIMEOUT, dockerClientConfig.connectTimeout)
                }
                if (dockerClientConfig.connectionRequestTimeout != null) {
                    clientConfig.property(
                        ApacheClientProperties.REQUEST_CONFIG,
                        RequestConfig.custom()
                            .setConnectionRequestTimeout(dockerClientConfig.connectionRequestTimeout).build()
                    )
                }
                val protocol: DockerWebClientProtocol = DockerWebClientProtocol.getDockerWebClientProtocol(dockerClientConfig.protocol)
                protocol.setProtocolSpecificConfiguration(clientConfig, dockerClientConfig)
                clientConfig.property(ApacheClientProperties.CONNECTION_MANAGER, protocol.getConnectionManagerForProtocol(dockerClientConfig))
                val clientBuilder = ClientBuilder.newBuilder().withConfig(clientConfig)
                val client = clientBuilder.build()
                val dockerSwarmWebTarget = DockerSwarmWebTarget(
                    swarm.id,
                    client.target(protocol.getUrlForProtocol(dockerClientConfig)).path(swarm.apiVersion)
                )
                dockerSwarmWebTargetList.add(dockerSwarmWebTarget)
            }
        )
    }

    override fun getBaseResource(): WebTarget {
        return dockerSwarmWebTargetList[0].baseResource
    }

    override fun getBaseResource(id: String): WebTarget {
        var baseResource: WebTarget? = null
        for (dockerSwarmWebTarget in dockerSwarmWebTargetList) {
            if (id.equals(dockerSwarmWebTarget.id, ignoreCase = true)) {
                baseResource = dockerSwarmWebTarget.baseResource
            }
        }
        if (baseResource == null) {
            throw SwarmNotFoundException("Swarm not found")
        }
        return baseResource
    }
}

private data class DockerSwarmWebTarget(
    val id: String,
    val baseResource: WebTarget
)

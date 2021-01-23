package com.swarmbit.docker.api.common.config

import io.micronaut.context.annotation.ConfigurationInject
import io.micronaut.context.annotation.ConfigurationProperties

@ConfigurationProperties("docker")
data class DockerConfig @ConfigurationInject constructor(
    val swarms: List<DockerSwarmConfig>
)

data class DockerSwarmConfig(
    val id: String,
    val name: String,
    val apiVersion: String,
    val description: String,
    val client: DockerClientConfig = DockerClientConfig()
)

data class DockerClientConfig(
    val protocol: String? = "unix",
    val address: String? = null,
    var unixSocketPath: String = "/var/run/docker.sock",
    val readTimeout: Int? = null,
    val connectTimeout: Int? = null,
    val maxTotalConnections: Int? = null,
    val maxPerRouteConnections: Int? = null,
    val connectionRequestTimeout: Int? = null,
    /**
     * tls or tlsverify
     */
    val tlsMode: String = "tls",
    val tlsCacert: String? = null,
    val tlsKey: String? = null,
    val tlsCert: String? = null
)

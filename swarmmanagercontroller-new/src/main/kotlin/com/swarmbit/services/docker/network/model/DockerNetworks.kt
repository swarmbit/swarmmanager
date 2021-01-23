package com.swarmbit.services.docker.network.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.swarmbit.docker.api.common.formatter.dockerToEpochMillis
import com.swarmbit.docker.api.networks.model.IPAM
import com.swarmbit.docker.api.networks.model.IPAMConfig
import com.swarmbit.docker.api.networks.model.Network

@JsonInclude(JsonInclude.Include.NON_NULL)
data class DockerNetwork(
    val id: String,
    val created: Long,
    val name: String,
    val labels: Map<String, String>,
    val options: Map<String, String>,
    val driver: String,
    val ipamDriver: String,
    val ipamConfigs: List<DockerIpamConfig>,
    val ipamOptions: Map<String, String>,
    val ipv6: Boolean,
    val internal: Boolean,
    val attachable: Boolean
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class DockerIpamConfig(
    val subnet: String,
    val ipRange: String,
    val gateway: String,
    val auxAddress: Map<String, String>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class DockerNetworkSummary(
    val id: String,
    val name: String,
    val driver: String,
    val scope: String
)

data class DockerNetworkCreate(
    val name: String? = null,
    val labels: Map<String, String>? = null,
    val options: Map<String, String>? = null,
    val driver: String? = null,
    val ipamDriver: String? = null,
    val ipamConfigs: List<DockerIpamConfig>? = null,
    val ipamOptions: Map<String, String>? = null,
    val ipv6: Boolean? = null,
    val internal: Boolean? = null,
    val attachable: Boolean? = null
)

fun DockerNetworkCreate.toNetwork(): Network =
    Network(
        name = name,
        labels = labels,
        options = options,
        driver = driver,
        ipam = IPAM(
            driver = driver,
            config = ipamConfigs?.map { it.toIPAMConfig() },
            options = ipamOptions
        ),
        isEnableIPv6 = ipv6,
        isInternal = internal,
        isAttachable = attachable
    )

fun Network.toDockerNetworkSummary(): DockerNetworkSummary =
    DockerNetworkSummary(
        id = id.orEmpty(),
        scope = scope.orEmpty(),
        name = name.orEmpty(),
        driver = driver.orEmpty()
    )

fun Network.toDockerNetwork(): DockerNetwork =
    DockerNetwork(
        id = id.orEmpty(),
        created = this.created?.dockerToEpochMillis() ?: 0L,
        name = name.orEmpty(),
        labels = labels.orEmpty(),
        options = options.orEmpty(),
        driver = driver.orEmpty(),
        ipamDriver = ipam?.driver.orEmpty(),
        ipamConfigs = ipam?.config?.map { it.toDockerIpamConfig() }.orEmpty(),
        ipamOptions = ipam?.options.orEmpty(),
        ipv6 = isEnableIPv6 ?: false,
        internal = isInternal ?: false,
        attachable = isAttachable ?: false
    )

fun IPAMConfig.toDockerIpamConfig(): DockerIpamConfig =
    DockerIpamConfig(
        subnet = subnet.orEmpty(),
        auxAddress = auxAddress.orEmpty(),
        gateway = gateway.orEmpty(),
        ipRange = ipRange.orEmpty()
    )

fun DockerIpamConfig.toIPAMConfig(): IPAMConfig =
    IPAMConfig(
        subnet = subnet,
        auxAddress = auxAddress,
        gateway = gateway,
        ipRange = ipRange
    )
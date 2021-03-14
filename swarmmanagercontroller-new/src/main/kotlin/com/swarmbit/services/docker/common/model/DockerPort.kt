package com.swarmbit.services.docker.common.model

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class DockerPort(
    val published: Int,
    val target: Int,
    var protocol: Protocol
)

enum class Protocol {
    TCP, UDP;

    companion object {
        fun getProtocol(protocolName: String) =
            when (protocolName.toUpperCase()) {
                "UDP" -> UDP
                "TCP" -> TCP
                else -> null
            }
    }
}

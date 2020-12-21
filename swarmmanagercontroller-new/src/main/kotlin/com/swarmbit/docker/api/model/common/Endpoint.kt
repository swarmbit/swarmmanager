package com.swarmbit.docker.api.model.common

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Endpoint(
        @JsonProperty("Spec")
        val spec: EndpointSpec? = null,
        @JsonProperty("Ports")
        val ports: List<PortConfig>? = null,
        @JsonProperty("VirtualIPs")
        val virtualIPs: List<EndpointVirtualIP>? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class EndpointSpec(
        @JsonProperty("Mode")
        val mode: String? = null,
        @JsonProperty("Ports")
        val ports: List<PortConfig>? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class EndpointVirtualIP(
        @JsonProperty("NetworkID")
        val networkId: String? = null,

        @JsonProperty("Addr")
        val addr: String? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class PortConfig(
        @JsonProperty("Name")
        val name: String? = null,
        @JsonProperty("Protocol")
        val protocol: String? = null,
        @JsonProperty("TargetPort")
        val targetPort: Int? = null,
        @JsonProperty("PublishedPort")
        val publishedPort: Int? = null,
        @JsonProperty("PublishMode")
        val publishMode: String? = null
)
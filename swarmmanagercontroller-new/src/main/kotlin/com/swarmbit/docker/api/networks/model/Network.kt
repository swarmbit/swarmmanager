package com.swarmbit.docker.api.networks.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class Network(
        @JsonProperty("Name")
        val name: String? = null,
        @JsonProperty("Id")
        val id: String? = null,
        @JsonProperty("Labels")
        val labels: Map<String, String>? = null,
        @JsonProperty("Options")
        val options: Map<String, String>? = null,
        @JsonProperty("Created")
        val created: String? = null,
        @JsonProperty("Scope")
        val scope: String? = null,
        @JsonProperty("Driver")
        val driver: String? = null,
        @JsonProperty("Attachable")
        val isAttachable: Boolean? = null,
        @JsonProperty("Internal")
        val isInternal: Boolean? = null,
        @JsonProperty("EnableIPv6")
        val isEnableIPv6: Boolean? = null,
        @JsonProperty("CheckDuplicate")
        val isCheckDuplicate: Boolean? = null,
        @JsonProperty("IPAM")
        val ipam: IPAM? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class IPAM(
        @JsonProperty("Driver")
        val driver: String? = null,
        @JsonProperty("Options")
        val options: Map<String, String>? = null,
        @JsonProperty("Config")
        val config: List<IPAMConfig>? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class IPAMConfig(
        @JsonProperty("Subnet")
        val subnet: String? = null,
        @JsonProperty("IPRange")
        val ipRange: String? = null,
        @JsonProperty("Gateway")
        val gateway: String? = null,
        /*
        * Apparently this is not working on version 1.30
        */
        @JsonProperty("AuxiliaryAddresses")
        val auxAddress: Map<String, String>? = null

)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class NetworkAttachmentConfig(
        @JsonProperty("Target")
        val target: String? = null,
        @JsonProperty("Aliases")
        val aliases: List<String>? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class NetworkCreateResponse(
        @JsonProperty("Id")
        val id: String? = null,
        @JsonProperty("Warning")
        val warning: String? = null

)

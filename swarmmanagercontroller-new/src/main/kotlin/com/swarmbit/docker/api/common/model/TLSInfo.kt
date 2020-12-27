package com.swarmbit.docker.api.common.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class TLSInfo(
        @JsonProperty("TrustRoot")
        val trustRoot: String? = null,
        @JsonProperty("CertIssuerSubject")
        val certIssuerSubject: String? = null
)
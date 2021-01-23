package com.swarmbit.services.docker.service.model

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class DockerService(
    val id: String,
    val createdAt: Long,
    val updatedAt: Long,
    val name: String,
    val labels: Map<String, String>,
    val data: String,
    val templatingName: String,
    val templatingOptions: Map<String, String>
)

data class DockerServiceSummary(
    val name: String
)

data class DockerLogs(
    val logLine: String
)

data class DockerServiceCreate(
    val name: String? = null,
    val labels: Map<String, String>? = null,
    val data: String? = null,
    val templatingName: String? = null,
    val templatingOptions: Map<String, String>? = null
)

data class DockerServiceUpdate(
    val labels: Map<String, String>
)
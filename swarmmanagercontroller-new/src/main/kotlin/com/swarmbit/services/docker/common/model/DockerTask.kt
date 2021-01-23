package com.swarmbit.services.docker.common.model

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.Duration

@JsonInclude(JsonInclude.Include.NON_NULL)
data class DockerTask(
    val id: String,
    val replica: Long,
    val serviceId: String,
    val serviceName: String,
    val image: String,
    val nodeId: String,
    val nodeHostname: String,
    val desiredState: String,
    val state: String,
    val lastStateChange: Duration,
    val lastStateChangeDate: Long,
    val errorMessage: String,
    val ports: List<Port>
)
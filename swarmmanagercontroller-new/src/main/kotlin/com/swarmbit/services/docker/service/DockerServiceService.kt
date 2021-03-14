package com.swarmbit.services.docker.service

import com.swarmbit.services.docker.common.model.DockerState
import com.swarmbit.services.docker.service.model.DockerLogs
import com.swarmbit.services.docker.service.model.DockerService
import com.swarmbit.services.docker.service.model.DockerServiceSummary

interface DockerServiceService {
    fun create(swarmId: String, dockerServiceCreate: DockerService): DockerService
    fun rm(swarmId: String, serviceId: String)
    fun ls(swarmId: String): List<DockerServiceSummary>
    fun logs(swarmId: String, serviceId: String): List<DockerLogs>
    fun ps(swarmId: String, serviceId: String): List<DockerState>
    fun inspect(swarmId: String, serviceId: String): DockerService
    fun update(swarmId: String, serviceId: String, dockerServiceUpdate: DockerService)
}

package com.swarmbit.services.docker.service

import com.swarmbit.docker.api.services.ServicesApi
import com.swarmbit.services.docker.common.model.DockerState
import com.swarmbit.services.docker.service.model.DockerLogs
import com.swarmbit.services.docker.service.model.DockerService
import com.swarmbit.services.docker.service.model.DockerServiceCreate
import com.swarmbit.services.docker.service.model.DockerServiceSummary
import com.swarmbit.services.docker.service.model.DockerServiceUpdate
import io.micronaut.context.annotation.Context

@Context
class DockerServiceServiceImpl(
    private val servicesApi: ServicesApi
) : DockerServiceService {

    override fun create(swarmId: String, dockerServiceCreate: DockerServiceCreate): DockerService {
        TODO("Not yet implemented")
    }

    override fun rm(swarmId: String, serviceId: String) {
        TODO("Not yet implemented")
    }

    override fun ls(swarmId: String): List<DockerServiceSummary> {
        TODO("Not yet implemented")
    }

    override fun logs(swarmId: String, serviceId: String): List<DockerLogs> {
        TODO("Not yet implemented")
    }

    override fun ps(swarmId: String, serviceId: String): List<DockerState> {
        TODO("Not yet implemented")
    }

    override fun inspect(swarmId: String, serviceId: String): DockerService {
        TODO("Not yet implemented")
    }

    override fun update(swarmId: String, serviceId: String, dockerServiceUpdate: DockerServiceUpdate) {
        TODO("Not yet implemented")
    }

}

package com.swarmbit.docker.api.services

import com.swarmbit.docker.api.common.annotation.DockerRemoteApiMinVersion
import com.swarmbit.docker.api.services.model.Service
import com.swarmbit.docker.api.services.model.ServiceGeneralResponse
import com.swarmbit.docker.api.services.parameters.ServicesCreateParameters
import com.swarmbit.docker.api.services.parameters.ServicesInspectParameters
import com.swarmbit.docker.api.services.parameters.ServicesListParameters
import com.swarmbit.docker.api.services.parameters.ServicesLogsParameters
import com.swarmbit.docker.api.services.parameters.ServicesUpdateParameters

interface ServicesApi {

    fun listServices(swarmId: String, parameters: ServicesListParameters): List<Service>

    fun inspectService(swarmId: String, id: String, parameters: ServicesInspectParameters): Service

    fun createService(swarmId: String, parameters: ServicesCreateParameters): ServiceGeneralResponse

    fun updateService(swarmId: String, id: String, parameters: ServicesUpdateParameters)

    fun deleteService(swarmId: String, id: String)

    @DockerRemoteApiMinVersion("v1.29")
    fun getServiceLogs(swarmId: String, id: String, parameters: ServicesLogsParameters): ByteArray
}

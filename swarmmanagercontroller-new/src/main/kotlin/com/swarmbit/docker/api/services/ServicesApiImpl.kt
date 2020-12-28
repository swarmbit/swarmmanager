package com.swarmbit.docker.api.services

import com.swarmbit.docker.api.common.AbstractApiImpl
import com.swarmbit.docker.api.common.client.DockerWebClient
import com.swarmbit.docker.api.common.rest.model.ResponseType
import com.swarmbit.docker.api.services.model.Service
import com.swarmbit.docker.api.services.model.ServiceGeneralResponse
import com.swarmbit.docker.api.services.parameters.ServicesCreateParameters
import com.swarmbit.docker.api.services.parameters.ServicesInspectParameters
import com.swarmbit.docker.api.services.parameters.ServicesListParameters
import com.swarmbit.docker.api.services.parameters.ServicesLogsParameters
import com.swarmbit.docker.api.services.parameters.ServicesUpdateParameters
import io.micronaut.context.annotation.Context

@Context
class ServicesApiImpl(dockerWebClient: DockerWebClient) : AbstractApiImpl(dockerWebClient), ServicesApi {
    companion object {
        private const val SERVICES_PATH = "/services"
        private const val CREATE_PATH = "/create"
        private const val UPDATE_PATH = "/update"
        private const val LOGS_PATH = "/logs"
    }

    override fun listServices(swarmId: String, parameters: ServicesListParameters): List<Service> {
        return listObjects(SERVICES_PATH, swarmId, object : ResponseType<List<Service>>() {}, parameters)
    }

    override fun inspectService(swarmId: String, id: String, parameters: ServicesInspectParameters): Service {
        return inspectObject(SERVICES_PATH, swarmId, object : ResponseType<Service>() {}, id, parameters)
            ?: throw IllegalArgumentException("No service found for swarmId ($swarmId) and serviceId (${id})")
    }

    override fun createService(swarmId: String, parameters: ServicesCreateParameters): ServiceGeneralResponse {
        return createObject(SERVICES_PATH + CREATE_PATH, swarmId, object : ResponseType<ServiceGeneralResponse>() {}, parameters, parameters)!!
    }

    override fun updateService(swarmId: String, id: String, parameters: ServicesUpdateParameters) {
        updateObject("$SERVICES_PATH/$id$UPDATE_PATH", swarmId, object : ResponseType<ServiceGeneralResponse>() {},
            parameters, parameters, parameters)
    }

    override fun deleteService(swarmId: String, id: String) {
        deleteObject("$SERVICES_PATH/$id", swarmId, object : ResponseType<Void>() {})
    }

    override fun getServiceLogs(swarmId: String, id: String, parameters: ServicesLogsParameters): ByteArray {
        return getObjectLogs("$SERVICES_PATH/$id$LOGS_PATH", swarmId, parameters) ?: ByteArray(0)
    }

}

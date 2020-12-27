package com.swarmbit.rest

import com.swarmbit.docker.api.services.ServicesApi
import com.swarmbit.docker.api.services.model.Service
import com.swarmbit.docker.api.services.parameters.ServicesListParameters
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("services")
class ServicesController(private val servicesApi: ServicesApi) {

    @Get
    fun getServices(): List<Service> {
        return servicesApi.listServices("swarm", ServicesListParameters())
    }

}
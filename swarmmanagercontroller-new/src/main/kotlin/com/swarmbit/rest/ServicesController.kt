package com.swarmbit.rest

import com.swarmbit.services.docker.service.DockerServiceService
import com.swarmbit.services.docker.service.model.DockerServiceSummary
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Status

@Controller("/api/swarms/{swarmId}/services")
class ServicesController(private val dockerServiceService: DockerServiceService) {

    @Get
    fun getServices(@PathVariable swarmId: String): List<DockerServiceSummary> {
        return dockerServiceService.ls(swarmId)
    }

    @Delete("{serviceId}")
    @Status(HttpStatus.NO_CONTENT)
    fun deleteService(@PathVariable swarmId: String, @PathVariable serviceId: String) {
        dockerServiceService.rm(swarmId, serviceId)
    }
}

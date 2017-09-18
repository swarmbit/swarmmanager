package com.swarmmanager.api;

import com.swarmmanager.auth.Role;
import com.swarmmanager.docker.cli.ServiceCli;
import com.swarmmanager.docker.cli.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service")
public class ServiceController {

    @Autowired
    private ServiceCli serviceCli;

    @Secured(Role.VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "{serviceId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Service serviceInspect(@PathVariable String serviceId) {
        return serviceCli.inspectService(serviceId);
    }

    @Secured(Role.VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "ls", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<ServiceSummary> serviceLs() {
        return serviceCli.serviceLs();
    }

    @Secured(Role.VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "{serviceId}/ps", produces = {MediaType.APPLICATION_JSON_VALUE})
    public State servicePs(@PathVariable String serviceId) {
        return serviceCli.servicePs(serviceId);
    }

    @Secured(Role.VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "{serviceId}/logs", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Logs serviceLogs(@PathVariable String serviceId) {
        return serviceCli.serviceLogs(serviceId);
    }

    @Secured(Role.USER)
    @RequestMapping(method = RequestMethod.DELETE, value = "{serviceId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void serviceRm(@PathVariable String serviceId) {
        serviceCli.serviceRm(serviceId);
    }

    @Secured(Role.USER)
    @RequestMapping(method = RequestMethod.POST, value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Service serviceCreate(@RequestBody Service service) {
        return serviceCli.serviceCreate(service);
    }

    @Secured(Role.USER)
    @RequestMapping(method = RequestMethod.PUT, value = "{serviceId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void serviceUpdate(@PathVariable String serviceId, @RequestBody Service service) {
        serviceCli.serviceUpdate(serviceId, service);
    }
}

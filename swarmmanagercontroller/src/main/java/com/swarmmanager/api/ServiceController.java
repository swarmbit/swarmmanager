package com.swarmmanager.api;


import com.swarmmanager.docker.cli.ServiceCli;
import com.swarmmanager.docker.cli.model.LogLine;
import com.swarmmanager.docker.cli.model.Service;
import com.swarmmanager.docker.cli.model.ServiceState;
import com.swarmmanager.docker.cli.model.ServiceSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service")
public class ServiceController {

    @Autowired
    private ServiceCli serviceCli;

    @RequestMapping(method = RequestMethod.GET, value = "{serviceId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Service inspectService(@PathVariable String serviceId) {
        return serviceCli.inspectService(serviceId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "ls", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<ServiceSummary> serviceLs() {
        return serviceCli.serviceLs();
    }

    @RequestMapping(method = RequestMethod.GET, value = "ps/{serviceId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ServiceState servicePs(@PathVariable String serviceId) {
        return serviceCli.servicePs(serviceId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "logs/{serviceId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<LogLine> serviceLogs(@PathVariable String serviceId) {
        return serviceCli.serviceLogs(serviceId);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "rm/{serviceId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void serviceRm(@PathVariable String serviceId) {
        serviceCli.serviceRm(serviceId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "create", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Service serviceCreate(@RequestBody Service service) {
        return serviceCli.serviceCreate(service);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "update/{serviceId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void serviceUpdate(@PathVariable String serviceId, @RequestBody Service service) {
        serviceCli.serviceUpdate(serviceId, service);
    }
}

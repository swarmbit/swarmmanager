package co.uk.swarmbit.api;

import co.uk.swarmbit.docker.cli.ServiceCli;
import co.uk.swarmbit.docker.cli.model.Logs;
import co.uk.swarmbit.docker.cli.model.Service;
import co.uk.swarmbit.auth.Role;
import co.uk.swarmbit.docker.cli.model.ServiceSummary;
import co.uk.swarmbit.docker.cli.model.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/swarms/{swarmId}/services")
public class ServiceController {

    @Autowired
    private ServiceCli serviceCli;

    @PreAuthorize(Role.IS_VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "{serviceId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Service serviceInspect(@PathVariable String swarmId, @PathVariable String serviceId) {
        return serviceCli.inspectService(swarmId, serviceId);
    }

    @PreAuthorize(Role.IS_VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<ServiceSummary> serviceLs(@PathVariable String swarmId) {
        return serviceCli.serviceLs(swarmId);
    }

    @PreAuthorize(Role.IS_VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "{serviceId}/ps", produces = {MediaType.APPLICATION_JSON_VALUE})
    public State servicePs(@PathVariable String swarmId, @PathVariable String serviceId) {
        return serviceCli.servicePs(swarmId, serviceId);
    }

    @PreAuthorize(Role.IS_VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "{serviceId}/logs", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Logs serviceLogs(@PathVariable String swarmId, @PathVariable String serviceId) {
        return serviceCli.serviceLogs(swarmId, serviceId);
    }

    @PreAuthorize(Role.IS_USER)
    @RequestMapping(method = RequestMethod.DELETE, value = "{serviceId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void serviceRm(@PathVariable String swarmId, @PathVariable String serviceId) {
        serviceCli.serviceRm(swarmId, serviceId);
    }

    @PreAuthorize(Role.IS_USER)
    @RequestMapping(method = RequestMethod.POST, value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Service serviceCreate(@PathVariable String swarmId, @RequestBody Service service) {
        return serviceCli.serviceCreate(swarmId, service);
    }

    @PreAuthorize(Role.IS_USER)
    @RequestMapping(method = RequestMethod.PUT, value = "{serviceId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Service serviceUpdate(@PathVariable String swarmId, @PathVariable String serviceId, @RequestBody Service service) {
        serviceCli.serviceUpdate(swarmId, serviceId, service);
        return service;
    }
}

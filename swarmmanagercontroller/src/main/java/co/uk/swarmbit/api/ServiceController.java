package co.uk.swarmbit.api;

import co.uk.swarmbit.auth.RoleAuthorities;
import co.uk.swarmbit.docker.cli.ServiceCli;
import co.uk.swarmbit.docker.cli.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/swarms/{swarmId}/services")
public class ServiceController {

    private final ServiceCli serviceCli;

    @Autowired
    public ServiceController(ServiceCli serviceCli) {
        this.serviceCli = serviceCli;
    }

    @PreAuthorize(RoleAuthorities.IS_VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "{serviceId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Service serviceInspect(@PathVariable String swarmId, @PathVariable String serviceId) {
        return serviceCli.inspectService(swarmId, serviceId);
    }

    @PreAuthorize(RoleAuthorities.IS_VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<ServiceSummary> serviceLs(@PathVariable String swarmId) {
        List<ServiceSummary> serviceSummaryList = serviceCli.serviceLs(swarmId);
        serviceSummaryList.sort(Comparator.comparing(ServiceSummary::getName));
        return serviceSummaryList;
    }

    @PreAuthorize(RoleAuthorities.IS_VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "{serviceId}/ps", produces = {MediaType.APPLICATION_JSON_VALUE})
    public State servicePs(@PathVariable String swarmId, @PathVariable String serviceId) {
        return serviceCli.servicePs(swarmId, serviceId);
    }

    @PreAuthorize(RoleAuthorities.IS_VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "{serviceId}/logs", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Logs serviceLogs(@PathVariable String swarmId, @PathVariable String serviceId) {
        return serviceCli.serviceLogs(swarmId, serviceId);
    }

    @PreAuthorize(RoleAuthorities.IS_USER)
    @RequestMapping(method = RequestMethod.DELETE, value = "{serviceId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void serviceRm(@PathVariable String swarmId, @PathVariable String serviceId) {
        serviceCli.serviceRm(swarmId, serviceId);
    }

    @PreAuthorize(RoleAuthorities.IS_USER)
    @RequestMapping(method = RequestMethod.POST, value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Service serviceCreate(@PathVariable String swarmId, @RequestBody Service service) {
        return serviceCli.serviceCreate(swarmId, service);
    }

    @PreAuthorize(RoleAuthorities.IS_USER)
    @RequestMapping(method = RequestMethod.PUT, value = "{serviceId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Service serviceUpdate(@PathVariable String swarmId, @PathVariable String serviceId, @RequestBody Service service) {
        serviceCli.serviceUpdate(swarmId, serviceId, service);
        return service;
    }
}

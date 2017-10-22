package com.swarmmanager.api;

import com.swarmmanager.auth.Role;
import com.swarmmanager.docker.cli.ConfigCli;
import com.swarmmanager.docker.cli.model.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/swarm/{swarmId}/config")
public class ConfigController {

    @Autowired
    private ConfigCli configCli;

    @Secured(Role.VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "ls", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Config> configLs(@PathVariable String swarmId) {
        return configCli.ls(swarmId);
    }

    @Secured(Role.USER)
    @RequestMapping(method = RequestMethod.POST, value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Config configCreate(@PathVariable String swarmId, @RequestBody Config config) {
        return configCli.create(swarmId, config);
    }

    @Secured(Role.USER)
    @RequestMapping(method = RequestMethod.PUT, value = "{configId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void configUpdate(@PathVariable String swarmId, @PathVariable String configId, @RequestBody Config config) {
        configCli.update(swarmId, configId, config);
    }

    @Secured(Role.VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "{configId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Config configInspect(@PathVariable String swarmId, @PathVariable String configId) {
        return configCli.inspect(swarmId, configId);
    }

    @Secured(Role.USER)
    @RequestMapping(method = RequestMethod.DELETE, value = "{configId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void configRm(@PathVariable String swarmId, @PathVariable String configId) {
        configCli.rm(swarmId, configId);
    }

}

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
@RequestMapping("/api/config")
public class ConfigController {

    @Autowired
    private ConfigCli configCli;

    @Secured(Role.VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "ls", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Config> configLs() {
        return configCli.ls();
    }

    @Secured(Role.USER)
    @RequestMapping(method = RequestMethod.POST, value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Config configCreate(@RequestBody Config config) {
        return configCli.create(config);
    }

    @Secured(Role.VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "{configId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Config configInspect(@PathVariable String configId) {
        return configCli.inspect(configId);
    }

    @Secured(Role.USER)
    @RequestMapping(method = RequestMethod.DELETE, value = "{configId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void configRm(@PathVariable String configId) {
        configCli.rm(configId);
    }

}

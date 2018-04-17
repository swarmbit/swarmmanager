package co.uk.swarmbit.api;

import co.uk.swarmbit.auth.RoleAuthorities;
import co.uk.swarmbit.docker.cli.ConfigCli;
import co.uk.swarmbit.docker.cli.model.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/swarms/{swarmId}/configs")
public class ConfigController {

    private final ConfigCli configCli;

    @Autowired
    public ConfigController(ConfigCli configCli) {
        this.configCli = configCli;
    }

    @PreAuthorize(RoleAuthorities.IS_VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Config> configLs(@PathVariable String swarmId) {
        List<Config> configs = configCli.ls(swarmId);
        configs.sort(Comparator.comparing(Config::getName));
        return configs;
    }

    @PreAuthorize(RoleAuthorities.IS_USER)
    @RequestMapping(method = RequestMethod.POST, value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Config configCreate(@PathVariable String swarmId, @RequestBody Config config) {
        return configCli.create(swarmId, config);
    }

    @PreAuthorize(RoleAuthorities.IS_USER)
    @RequestMapping(method = RequestMethod.PUT, value = "{configId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void configUpdate(@PathVariable String swarmId, @PathVariable String configId, @RequestBody Config config) {
        configCli.update(swarmId, configId, config);
    }

    @PreAuthorize(RoleAuthorities.IS_VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "{configId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Config configInspect(@PathVariable String swarmId, @PathVariable String configId) {
        return configCli.inspect(swarmId, configId);
    }

    @PreAuthorize(RoleAuthorities.IS_USER)
    @RequestMapping(method = RequestMethod.DELETE, value = "{configId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void configRm(@PathVariable String swarmId, @PathVariable String configId) {
        configCli.rm(swarmId, configId);
    }

}

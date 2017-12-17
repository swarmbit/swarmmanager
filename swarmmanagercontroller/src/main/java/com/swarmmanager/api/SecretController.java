package com.swarmmanager.api;

import com.swarmmanager.auth.Role;
import com.swarmmanager.docker.cli.SecretCli;
import com.swarmmanager.docker.cli.model.Secret;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/swarms/{swarmId}/secrets")
public class SecretController {

    @Autowired
    private SecretCli secretCli;

    @PreAuthorize(Role.IS_VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Secret> secretLs(@PathVariable String swarmId) {
        return secretCli.ls(swarmId);
    }

    @PreAuthorize(Role.IS_USER)
    @RequestMapping(method = RequestMethod.POST, value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Secret secretCreate(@PathVariable String swarmId, @RequestBody Secret secret) {
        return secretCli.create(swarmId, secret);
    }

    @PreAuthorize(Role.IS_VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "{secretId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Secret secretInspect(@PathVariable String swarmId, @PathVariable String secretId) {
        return secretCli.inspect(swarmId, secretId);
    }

    @PreAuthorize(Role.IS_USER)
    @RequestMapping(method = RequestMethod.PUT, value = "{secretId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void secretUpdate(@PathVariable String swarmId, @PathVariable String secretId, @RequestBody Secret secret) {
        secretCli.update(swarmId, secretId, secret);
    }
    @PreAuthorize(Role.IS_USER)
    @RequestMapping(method = RequestMethod.DELETE, value = "{secretId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void secretRm(@PathVariable String swarmId, @PathVariable String secretId) {
        secretCli.rm(swarmId, secretId);
    }

}

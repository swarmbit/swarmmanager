package com.swarmmanager.api;

import com.swarmmanager.auth.Role;
import com.swarmmanager.docker.cli.SecretCli;
import com.swarmmanager.docker.cli.model.Config;
import com.swarmmanager.docker.cli.model.Secret;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/secret")
public class SecretController {

    @Autowired
    private SecretCli secretCli;

    @Secured(Role.VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "ls", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Secret> secretLs() {
        return secretCli.ls();
    }

    @Secured(Role.USER)
    @RequestMapping(method = RequestMethod.POST, value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Secret secretCreate(@RequestBody Secret secret) {
        return secretCli.create(secret);
    }

    @Secured(Role.VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "{secretId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Secret secretInspect(@PathVariable String secretId) {
        return secretCli.inspect(secretId);
    }

    @Secured(Role.USER)
    @RequestMapping(method = RequestMethod.PUT, value = "{secretId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void secretUpdate(@PathVariable String secretId, @RequestBody Secret secret) {
        secretCli.update(secretId, secret);
    }
    @Secured(Role.USER)
    @RequestMapping(method = RequestMethod.DELETE, value = "{secretId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void secretRm(@PathVariable String secretId) {
        secretCli.rm(secretId);
    }

}

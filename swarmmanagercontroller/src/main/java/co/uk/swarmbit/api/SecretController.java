package co.uk.swarmbit.api;

import co.uk.swarmbit.auth.RoleAuthorities;
import co.uk.swarmbit.docker.cli.SecretCli;
import co.uk.swarmbit.docker.cli.model.Secret;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/swarms/{swarmId}/secrets")
public class SecretController {

    private final SecretCli secretCli;

    @Autowired
    public SecretController(SecretCli secretCli) {
        this.secretCli = secretCli;
    }

    @PreAuthorize(RoleAuthorities.IS_VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Secret> secretLs(@PathVariable String swarmId) {
        List<Secret> secrets = secretCli.ls(swarmId);
        secrets.sort(Comparator.comparing(Secret::getName));
        return secrets;
    }

    @PreAuthorize(RoleAuthorities.IS_USER)
    @RequestMapping(method = RequestMethod.POST, value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Secret secretCreate(@PathVariable String swarmId, @RequestBody Secret secret) {
        return secretCli.create(swarmId, secret);
    }

    @PreAuthorize(RoleAuthorities.IS_VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "{secretId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Secret secretInspect(@PathVariable String swarmId, @PathVariable String secretId) {
        return secretCli.inspect(swarmId, secretId);
    }

    @PreAuthorize(RoleAuthorities.IS_USER)
    @RequestMapping(method = RequestMethod.PUT, value = "{secretId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void secretUpdate(@PathVariable String swarmId, @PathVariable String secretId, @RequestBody Secret secret) {
        secretCli.update(swarmId, secretId, secret);
    }
    @PreAuthorize(RoleAuthorities.IS_USER)
    @RequestMapping(method = RequestMethod.DELETE, value = "{secretId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void secretRm(@PathVariable String swarmId, @PathVariable String secretId) {
        secretCli.rm(swarmId, secretId);
    }

}

package com.swarmmanager.api;

import com.swarmmanager.auth.Role;
import com.swarmmanager.docker.cli.SwarmCli;
import com.swarmmanager.docker.cli.model.Swarm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/swarm")
public class SwarmController {

    @Autowired
    private SwarmCli swarmCli;

    @Secured(Role.VISITOR)
    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Swarm swarmInspect() {
        return swarmCli.inspect();
    }

    @Secured(Role.ADMIN)
    @RequestMapping(method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void swarmUpdate(@RequestBody Swarm swarm) {
        swarmCli.update(swarm);
    }
}

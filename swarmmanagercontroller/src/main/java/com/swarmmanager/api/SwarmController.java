package com.swarmmanager.api;

import com.swarmmanager.auth.Role;
import com.swarmmanager.docker.cli.SwarmCli;
import com.swarmmanager.docker.cli.model.Swarm;
import com.swarmmanager.docker.cli.model.Unlock;
import com.swarmmanager.docker.config.DockerConfig;
import com.swarmmanager.docker.config.DockerSwarmConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/swarm")
public class SwarmController {

    @Autowired
    private DockerConfig dockerConfig;

    @Autowired
    private SwarmCli swarmCli;

    @Secured(Role.VISITOR)
    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<String> swarmLs() {
        return dockerConfig.getSwarms().stream().map(DockerSwarmConfig::getId).collect(toList());
    }

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

    @Secured(Role.ADMIN)
    @RequestMapping(method = RequestMethod.PUT, value = "unlock", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Unlock unlock() {
        return swarmCli.unlock();
    }

    @Secured(Role.ADMIN)
    @RequestMapping(method = RequestMethod.PUT, value = "rotate", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void rotate() {
        swarmCli.rotate();
    }
}

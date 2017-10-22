package com.swarmmanager.api;

import com.swarmmanager.auth.Role;
import com.swarmmanager.docker.cli.SwarmCli;
import com.swarmmanager.docker.cli.model.Swarm;
import com.swarmmanager.docker.cli.model.Unlock;
import com.swarmmanager.docker.config.DockerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/swarms")
public class SwarmController {

    @Autowired
    private DockerConfig dockerConfig;

    @Autowired
    private SwarmCli swarmCli;

    @Secured(Role.VISITOR)
    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<SwarmConfigModel> swarmLs() {
        return dockerConfig.getSwarms().stream().map(swarmConfig -> {
            SwarmConfigModel swarmConfigModel = new SwarmConfigModel();
            swarmConfigModel.setId(swarmConfig.getId());
            swarmConfigModel.setName(swarmConfig.getName());
            return swarmConfigModel;
        }).collect(toList());
    }

    @Secured(Role.VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "{swarmId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Swarm swarmInspect(@PathVariable String swarmId) {
        return swarmCli.inspect(swarmId);
    }

    @Secured(Role.ADMIN)
    @RequestMapping(method = RequestMethod.PUT, value = "{swarmId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void swarmUpdate(@PathVariable String swarmId, @RequestBody Swarm swarm) {
        swarmCli.update(swarmId, swarm);
    }

    @Secured(Role.ADMIN)
    @RequestMapping(method = RequestMethod.PUT, value = "{swarmId}/unlock", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Unlock unlock(@PathVariable String swarmId) {
        return swarmCli.unlock(swarmId);
    }

    @Secured(Role.ADMIN)
    @RequestMapping(method = RequestMethod.PUT, value = "{swarmId}/rotate", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void rotate(@PathVariable String swarmId) {
        swarmCli.rotate(swarmId);
    }

    public static class SwarmConfigModel {
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

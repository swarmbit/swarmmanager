package co.uk.swarmbit.api;

import co.uk.swarmbit.auth.RoleAuthorities;
import co.uk.swarmbit.docker.cli.SwarmCli;
import co.uk.swarmbit.docker.cli.model.Swarm;
import co.uk.swarmbit.docker.cli.model.Unlock;
import co.uk.swarmbit.docker.config.DockerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/swarms")
public class SwarmController {

    private final DockerConfig dockerConfig;

    private final SwarmCli swarmCli;

    @Autowired
    public SwarmController(DockerConfig dockerConfig, SwarmCli swarmCli) {
        this.dockerConfig = dockerConfig;
        this.swarmCli = swarmCli;
    }

    @PreAuthorize(RoleAuthorities.IS_VISITOR)
    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<SwarmConfigModel> swarmLs() {
        return dockerConfig.getSwarms().stream().map(swarmConfig -> {
            SwarmConfigModel swarmConfigModel = new SwarmConfigModel();
            swarmConfigModel.id = swarmConfig.getId();
            swarmConfigModel.name = swarmConfig.getName();
            swarmConfigModel.apiVersion = swarmConfig.getApiVersion();
            return swarmConfigModel;
        }).collect(toList());
    }

    @PreAuthorize(RoleAuthorities.IS_VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "{swarmId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Swarm swarmInspect(@PathVariable String swarmId) {
        return swarmCli.inspect(swarmId);
    }

    @PreAuthorize(RoleAuthorities.IS_ADMIN)
    @RequestMapping(method = RequestMethod.PUT, value = "{swarmId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void swarmUpdate(@PathVariable String swarmId, @RequestBody Swarm swarm) {
        swarmCli.update(swarmId, swarm);
    }

    @PreAuthorize(RoleAuthorities.IS_ADMIN)
    @RequestMapping(method = RequestMethod.PUT, value = "{swarmId}/unlock", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Unlock unlock(@PathVariable String swarmId) {
        return swarmCli.unlock(swarmId);
    }

    @PreAuthorize(RoleAuthorities.IS_ADMIN)
    @RequestMapping(method = RequestMethod.PUT, value = "{swarmId}/rotate", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void rotate(@PathVariable String swarmId) {
        swarmCli.rotate(swarmId);
    }

    public static class SwarmConfigModel {
        private String id;
        private String name;
        private String apiVersion;

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

        public String getApiVersion() {
            return apiVersion;
        }

        public void setApiVersion(String apiVersion) {
            this.apiVersion = apiVersion;
        }
    }
}

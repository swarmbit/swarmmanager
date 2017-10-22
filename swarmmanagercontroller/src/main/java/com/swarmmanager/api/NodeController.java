package com.swarmmanager.api;

import com.swarmmanager.auth.Role;
import com.swarmmanager.docker.cli.NodeCli;
import com.swarmmanager.docker.cli.model.Node;
import com.swarmmanager.docker.cli.model.NodeSummary;
import com.swarmmanager.docker.cli.model.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.util.List;

@RestController
@RequestMapping("/api/swarm/{swarmId}/node")
public class NodeController {

    @Autowired
    private NodeCli nodeCli;

    @Secured(Role.VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "ls", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<NodeSummary> nodeLs(@PathVariable String swarmId) {
        return nodeCli.ls(swarmId);
    }

    @Secured(Role.USER)
    @RequestMapping(method = RequestMethod.PUT, value = "{nodeId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void nodeUpdate(@PathVariable String swarmId, @PathVariable String nodeId, @RequestBody Node node) {
        nodeCli.update(swarmId, nodeId, node);
    }

    @Secured(Role.VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "{nodeId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Node nodeInspect(@PathVariable String swarmId, @PathVariable String nodeId) {
        return nodeCli.inspect(swarmId, nodeId);
    }

    @Secured(Role.USER)
    @RequestMapping(method = RequestMethod.DELETE, value = "{nodeId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void nodeRm(@PathVariable String swarmId, @PathVariable String nodeId, @QueryParam("force") boolean force) {
        nodeCli.rm(swarmId, nodeId, force);
    }

    @Secured(Role.USER)
    @RequestMapping(method = RequestMethod.GET, value = "{nodeId}/ps", produces = {MediaType.APPLICATION_JSON_VALUE})
    public State nodePs(@PathVariable String swarmId, @PathVariable String nodeId) {
        return nodeCli.ps(swarmId, nodeId);
    }

}

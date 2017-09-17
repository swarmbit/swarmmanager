package com.swarmmanager.api;

import com.swarmmanager.auth.Role;
import com.swarmmanager.docker.cli.NodeCli;
import com.swarmmanager.docker.cli.model.Node;
import com.swarmmanager.docker.cli.model.NodeState;
import com.swarmmanager.docker.cli.model.NodeSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/node")
public class NodeController {

    @Autowired
    private NodeCli nodeCli;

    @Secured(Role.VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "ls", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<NodeSummary> nodeLs() {
        return nodeCli.ls();
    }

    @Secured(Role.USER)
    @RequestMapping(method = RequestMethod.PUT, value = "{nodeId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void nodeUpdate(@PathVariable String nodeId, @RequestBody Node node) {
        nodeCli.update(nodeId, node);
    }

    @Secured(Role.VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "{nodeId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void nodeInspect(@PathVariable String nodeId) {
        nodeCli.inspect(nodeId);
    }

    @Secured(Role.USER)
    @RequestMapping(method = RequestMethod.DELETE, value = "{nodeId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void nodeRm(@PathVariable String nodeId) {
        nodeCli.rm(nodeId);
    }

    @Secured(Role.USER)
    @RequestMapping(method = RequestMethod.GET, value = "{nodeId}/ps", produces = {MediaType.APPLICATION_JSON_VALUE})
    public NodeState nodePs(@PathVariable String nodeId) {
        return nodeCli.ps(nodeId);
    }

}

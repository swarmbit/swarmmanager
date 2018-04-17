package co.uk.swarmbit.api;

import co.uk.swarmbit.auth.RoleAuthorities;
import co.uk.swarmbit.docker.cli.NodeCli;
import co.uk.swarmbit.docker.cli.model.Node;
import co.uk.swarmbit.docker.cli.model.NodeSummary;
import co.uk.swarmbit.docker.cli.model.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/swarms/{swarmId}/nodes")
public class NodeController {

    private final NodeCli nodeCli;

    @Autowired
    public NodeController(NodeCli nodeCli) {
        this.nodeCli = nodeCli;
    }

    @PreAuthorize(RoleAuthorities.IS_VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<NodeSummary> nodeLs(@PathVariable String swarmId) {
        List<NodeSummary> nodes = nodeCli.ls(swarmId);
        nodes.sort(Comparator.comparing(NodeSummary::getHostname));
        return nodes;
    }

    @PreAuthorize(RoleAuthorities.IS_ADMIN)
    @RequestMapping(method = RequestMethod.PUT, value = "{nodeId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void nodeUpdate(@PathVariable String swarmId, @PathVariable String nodeId, @RequestBody Node node) {
        nodeCli.update(swarmId, nodeId, node);
    }

    @PreAuthorize(RoleAuthorities.IS_VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "{nodeId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Node nodeInspect(@PathVariable String swarmId, @PathVariable String nodeId) {
        return nodeCli.inspect(swarmId, nodeId);
    }

    @PreAuthorize(RoleAuthorities.IS_ADMIN)
    @RequestMapping(method = RequestMethod.DELETE, value = "{nodeId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void nodeRm(@PathVariable String swarmId, @PathVariable String nodeId, @QueryParam("force") boolean force) {
        nodeCli.rm(swarmId, nodeId, force);
    }

    @PreAuthorize(RoleAuthorities.IS_VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "{nodeId}/ps", produces = {MediaType.APPLICATION_JSON_VALUE})
    public State nodePs(@PathVariable String swarmId, @PathVariable String nodeId) {
        return nodeCli.ps(swarmId, nodeId);
    }

}

package co.uk.swarmbit.api;

import co.uk.swarmbit.auth.RoleAuthorities;
import co.uk.swarmbit.docker.cli.NetworkCli;
import co.uk.swarmbit.docker.cli.model.Network;
import co.uk.swarmbit.docker.cli.model.NetworkSummary;
import co.uk.swarmbit.docker.cli.model.NodeSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/swarms/{swarmId}/networks")
public class NetworkController {

    private final NetworkCli networkCli;

    @Autowired
    public NetworkController(NetworkCli networkCli) {
        this.networkCli = networkCli;
    }

    @PreAuthorize(RoleAuthorities.IS_VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<NetworkSummary> networkLs(@PathVariable String swarmId) {
        List<NetworkSummary> networkSummaryList = networkCli.ls(swarmId);
        networkSummaryList.sort(Comparator.comparing(NetworkSummary::getName));
        return networkSummaryList;
    }

    @PreAuthorize(RoleAuthorities.IS_ADMIN)
    @RequestMapping(method = RequestMethod.POST, value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Network networkCreate(@PathVariable String swarmId, @RequestBody Network network) {
        return networkCli.create(swarmId, network);
    }

    @PreAuthorize(RoleAuthorities.IS_VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "{networkId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Network networkInspect(@PathVariable String swarmId, @PathVariable String networkId) {
        return networkCli.inspect(swarmId, networkId);
    }

    @PreAuthorize(RoleAuthorities.IS_USER)
    @RequestMapping(method = RequestMethod.DELETE, value = "{networkId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void networkRm(@PathVariable String swarmId, @PathVariable String networkId) {
        networkCli.rm(swarmId, networkId);
    }

}

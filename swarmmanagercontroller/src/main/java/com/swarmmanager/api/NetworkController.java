package com.swarmmanager.api;

import com.swarmmanager.auth.Role;
import com.swarmmanager.docker.cli.NetworkCli;
import com.swarmmanager.docker.cli.model.Network;
import com.swarmmanager.docker.cli.model.NetworkSummary;
import com.swarmmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/swarms/{swarmId}/networks")
public class NetworkController {

    @Autowired
    private NetworkCli networkCli;

    @PreAuthorize(Role.IS_VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<NetworkSummary> networkLs(@PathVariable String swarmId) {
        return networkCli.ls(swarmId);
    }

    @PreAuthorize(Role.IS_ADMIN)
    @RequestMapping(method = RequestMethod.POST, value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Network networkCreate(@PathVariable String swarmId, @RequestBody Network network) {
        return networkCli.create(swarmId, network);
    }

    @PreAuthorize(Role.IS_VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "{networkId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Network networkInspect(@PathVariable String swarmId, @PathVariable String networkId) {
        return networkCli.inspect(swarmId, networkId);
    }

    @PreAuthorize(Role.IS_ADMIN)
    @RequestMapping(method = RequestMethod.DELETE, value = "{networkId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void networkRm(@PathVariable String swarmId, @PathVariable String networkId) {
        networkCli.rm(swarmId, networkId);
    }

}

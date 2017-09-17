package com.swarmmanager.api;

import com.swarmmanager.auth.Role;
import com.swarmmanager.docker.cli.NetworkCli;
import com.swarmmanager.docker.cli.model.Network;
import com.swarmmanager.docker.cli.model.NetworkSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/network")
public class NetworkController {

    @Autowired
    private NetworkCli networkCli;

    @Secured(Role.VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "ls", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<NetworkSummary> networkLs() {
        return networkCli.ls();
    }

    @Secured(Role.USER)
    @RequestMapping(method = RequestMethod.POST, value = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Network networkCreate(@RequestBody Network network) {
        return networkCli.create(network);
    }

    @Secured(Role.VISITOR)
    @RequestMapping(method = RequestMethod.GET, value = "{networkId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Network networkInspect(@PathVariable String networkId) {
        return networkCli.inspect(networkId);
    }

    @Secured(Role.USER)
    @RequestMapping(method = RequestMethod.DELETE, value = "{networkId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void networkRm(@PathVariable String networkId) {
        networkCli.rm(networkId);
    }

}

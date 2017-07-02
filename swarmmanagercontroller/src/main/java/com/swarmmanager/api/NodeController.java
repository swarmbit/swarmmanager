package com.swarmmanager.api;

import com.swarmmanager.docker.api.nodes.NodesApi;
import com.swarmmanager.docker.api.nodes.parameters.NodeMembership;
import com.swarmmanager.docker.api.nodes.parameters.NodesFilters;
import com.swarmmanager.docker.api.nodes.parameters.NodesListParameters;
import com.swarmmanager.docker.api.common.json.NodeJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/nodes")
public class NodeController {

    @Autowired
    private NodesApi nodesApi;

    @RequestMapping(method = RequestMethod.GET, value = "list", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<NodeJson> listNodes() {
        return nodesApi.listNodes(new NodesListParameters()
                .setFilters(new NodesFilters()
                .setMembership(NodeMembership.ACCEPTED)));
    }
}

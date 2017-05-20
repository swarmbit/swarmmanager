package com.swarmmanager.api;

import com.swarmmanager.docker.api.common.json.SwarmJson;
import com.swarmmanager.docker.api.swarm.SwarmApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/swarm")
public class SwarmController {

    @Autowired
    private SwarmApi swarmApi;

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public SwarmJson base() {
        return swarmApi.inspectSwarm();
    }
}

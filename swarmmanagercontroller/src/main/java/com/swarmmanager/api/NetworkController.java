package com.swarmmanager.api;

import com.swarmmanager.docker.api.common.json.NetworkJson;
import com.swarmmanager.docker.api.networks.NetworksApi;
import com.swarmmanager.docker.api.networks.parameters.NetworkListParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/networks")
public class NetworkController {

    @Autowired
    private NetworksApi networksApi;

    @RequestMapping(method = RequestMethod.GET, value = "list", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<NetworkJson> listNetworks() {
        return networksApi.listNetworks(new NetworkListParameters());
    }

}

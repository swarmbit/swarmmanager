package com.swarmmanager.api;

import com.swarmmanager.auth.Role;
import com.swarmmanager.docker.config.DockerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/docker")
public class DockerApiController {

    @Autowired
    private DockerConfig dockerConfig;

    @Secured(Role.VISITOR)
    @RequestMapping(method = RequestMethod.GET, path = "version", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String version() {
        return dockerConfig.getApiVersion();
    }

}

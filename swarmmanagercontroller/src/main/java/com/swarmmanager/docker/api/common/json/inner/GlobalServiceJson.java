package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.swarmmanager.docker.api.common.json.DockerRemoteApiJson;
import com.swarmmanager.docker.api.common.annotation.DockerRemoteApiVersions;

@DockerRemoteApiVersions(versions = "v1.28")
@JsonIgnoreProperties(ignoreUnknown = true)
public class GlobalServiceJson implements DockerRemoteApiJson {
}

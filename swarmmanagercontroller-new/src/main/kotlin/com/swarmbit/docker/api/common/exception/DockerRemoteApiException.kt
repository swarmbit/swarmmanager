package com.swarmbit.docker.api.common.exception

class DockerRemoteApiException(message: String?, val status: Int) : RuntimeException(message)

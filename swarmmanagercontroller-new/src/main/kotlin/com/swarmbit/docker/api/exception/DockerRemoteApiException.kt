package com.swarmbit.docker.api.exception

class DockerRemoteApiException(message: String?, val status: Int) : RuntimeException(message)

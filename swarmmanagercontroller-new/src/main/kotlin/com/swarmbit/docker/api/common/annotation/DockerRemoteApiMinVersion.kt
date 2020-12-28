package com.swarmbit.docker.api.common.annotation

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION,
    AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
internal annotation class DockerRemoteApiMinVersion(val value: String)
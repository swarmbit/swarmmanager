package co.uk.swarmbit.docker.api.common.annotation;

public @interface DockerRemoteApiMinVersion {
    String value() default "";
    String version() default "";
    String comment() default "";
}
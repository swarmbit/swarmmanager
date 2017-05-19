package com.swarmmanager.docker.api.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DockerFilterParam {
    String name() default "";
}
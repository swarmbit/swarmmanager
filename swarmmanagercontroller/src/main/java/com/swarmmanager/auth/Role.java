package com.swarmmanager.auth;

/**
 * This class is not a enum because spring security @Secured() does not support enums
 */
public class   Role {
    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";
    public static final String VISITOR = "VISITOR";
}

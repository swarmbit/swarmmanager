package com.swarmmanager.auth;

/**
 * This class is not a enum because spring security @Secured() does not support enums
 */
public class   Role {
    public static final String IS_ADMIN = "hasAuthority('ADMIN')";
    public static final String IS_USER = "hasAuthority('USER')";
    public static final String IS_VISITOR = "hasAuthority('VISITOR')";
}

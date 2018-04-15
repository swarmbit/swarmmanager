package co.uk.swarmbit.auth.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Role {
    ADMIN,
    USER,
    VISITOR,
    NONE;

    public static List<String> getRoles(String role) {
        role = StringUtils.upperCase(role);
        List<String> roles = new ArrayList<>();
        switch (role) {
            case "NONE":
                roles.add("NONE");
                break;
            case "VISITOR":
                roles.add("NONE");
                roles.add("VISITOR");
                break;
            case "USER":
                roles.add("NONE");
                roles.add("VISITOR");
                roles.add("USER");
                break;
            case "ADMIN":
                roles.add("NONE");
                roles.add("VISITOR");
                roles.add("USER");
                roles.add("ADMIN");
                break;
        }
        return roles;
    }

    public static String getRole(String[] roles) {
        List<String> rolesList = Arrays.asList(roles);
        if (rolesList.contains("ADMIN")) {
            return "ADMIN";
        }
        if (rolesList.contains("USER")) {
            return "USER";
        }
        if (rolesList.contains("VISITOR")) {
            return "VISITOR";
        }
        if (rolesList.contains("NONE")) {
            return "NONE";
        }
        return null;
    }
}

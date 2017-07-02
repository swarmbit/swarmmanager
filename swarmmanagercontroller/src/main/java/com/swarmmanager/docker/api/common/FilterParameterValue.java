package com.swarmmanager.docker.api.common;

public class FilterParameterValue {

    private String value;

    private boolean bool;

    public FilterParameterValue(String value) {
        this.value = value;
    }

    public FilterParameterValue(String value, boolean bool) {
        this.value = value;
        this.bool = bool;
    }

    public String getValue() {
        return value;
    }

    public boolean isBool() {
        return bool;
    }

}

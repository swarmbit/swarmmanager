package com.swarmmanager.docker.api.networks.parameters;

public enum NetworkType {
    CUSTOM,
    BUILTIN;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

}

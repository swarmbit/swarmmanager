package com.swarmmanager.docker.api.nodes.parameters;

public enum  NodeRole {
    MANAGER,
    WORKER;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}

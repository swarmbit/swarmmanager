package com.swarmmanager.docker.api.nodes.parameters;

public class NodesFilters {

    private String id;

    private String label;

    private NodeMembership membership;

    private String name;

    private NodeRole role;

    public String getId() {
        return id;
    }

    public NodesFilters setId(String id) {
        this.id = id;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public NodesFilters setLabel(String label) {
        this.label = label;
        return this;
    }

    public NodeMembership getMembership() {
        return membership;
    }

    public NodesFilters setMembership(NodeMembership membership) {
        this.membership = membership;
        return this;
    }

    public String getName() {
        return name;
    }

    public NodesFilters setName(String name) {
        this.name = name;
        return this;
    }

    public NodeRole getRole() {
        return role;
    }

    public NodesFilters setRole(NodeRole role) {
        this.role = role;
        return this;
    }
}

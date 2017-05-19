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

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public NodeMembership getMembership() {
        return membership;
    }

    public void setMembership(NodeMembership membership) {
        this.membership = membership;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NodeRole getRole() {
        return role;
    }

    public void setRole(NodeRole role) {
        this.role = role;
    }
}

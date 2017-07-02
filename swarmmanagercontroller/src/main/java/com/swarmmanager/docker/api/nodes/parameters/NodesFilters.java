package com.swarmmanager.docker.api.nodes.parameters;

import com.swarmmanager.docker.api.common.AbstractFilters;
import com.swarmmanager.docker.api.common.Filters;

public class NodesFilters extends AbstractFilters implements Filters {

    public NodesFilters setId(String id) {
        addFilter("id", id);
        return this;
    }

    public NodesFilters setLabel(String label) {
        addFilter("label", label);
        return this;
    }

    public NodesFilters setMembership(NodeMembership membership) {
        addFilter("membership", membership.toString());
        return this;
    }

    public NodesFilters setName(String name) {
        addFilter("name", name);
        return this;
    }

    public NodesFilters setRole(NodeRole role) {
        addFilter("role", role.toString());
        return this;
    }

}

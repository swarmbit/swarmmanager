package com.swarmmanager.docker.api.nodes.parameters;

import com.swarmmanager.docker.api.common.FilterParameter;
import com.swarmmanager.docker.api.common.FilterParameterValue;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

public class NodesFilters {

    private FilterParameter id;

    private FilterParameter label;

    private FilterParameter membership;

    private FilterParameter name;

    private FilterParameter role;

    public NodesFilters setId(String id) {
        this.id = new FilterParameter("id").addValue(new FilterParameterValue(id));
        return this;
    }

    public NodesFilters setLabel(String label) {
        this.label = new FilterParameter("label").addValue(new FilterParameterValue(label));
        return this;
    }

    public NodesFilters setMembership(NodeMembership membership) {
        this.membership = new FilterParameter("membership").addValue(new FilterParameterValue(membership.toString()));
        return this;
    }

    public NodesFilters setName(String name) {
        this.name = new FilterParameter("name").addValue(new FilterParameterValue(name));
        return this;
    }

    public NodesFilters setRole(NodeRole role) {
        this.role = new FilterParameter("role").addValue(new FilterParameterValue(role.toString()));
        return this;
    }

    public String getJson() {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        if (id != null) {
            id.addFilterJson(jsonObjectBuilder);
        }
        if (label != null) {
            label.addFilterJson(jsonObjectBuilder);
        }
        if (membership != null) {
            membership.addFilterJson(jsonObjectBuilder);
        }
        if (name != null) {
            name.addFilterJson(jsonObjectBuilder);
        }
        if (role != null) {
            role.addFilterJson(jsonObjectBuilder);
        }
        return jsonObjectBuilder.build().toString();
    }
}

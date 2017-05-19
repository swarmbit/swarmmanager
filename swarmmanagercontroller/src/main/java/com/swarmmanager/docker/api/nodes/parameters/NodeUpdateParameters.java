package com.swarmmanager.docker.api.nodes.parameters;

import com.swarmmanager.docker.api.common.json.NodeSpecJson;
import com.swarmmanager.rest.QueryParam;

public class NodeUpdateParameters {

    private static final String VERSION_NAME = "version";

    private String id;

    private QueryParam versionQueryParam;

    private NodeSpecJson node;

    public NodeUpdateParameters() {
        node = new NodeSpecJson();
        versionQueryParam = new QueryParam(VERSION_NAME, 0L);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public QueryParam getVersionQueryParam() {
        return versionQueryParam;
    }

    public void setVersionQueryParam(long versionValue) {
        this.versionQueryParam = new QueryParam(VERSION_NAME, versionValue);
    }

    public NodeSpecJson getNode() {
        return node;
    }

    public void setNode(NodeSpecJson node) {
        this.node = node;
    }
}

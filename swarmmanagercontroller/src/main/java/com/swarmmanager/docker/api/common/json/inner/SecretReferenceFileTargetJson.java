package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SecretReferenceFileTargetJson {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("UID")
    private String uid;

    @JsonProperty("GID")
    private String gid;

    @JsonProperty("Mode")
    private String mode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SecretReferenceFileTargetJson{");
        sb.append("name='").append(name).append('\'');
        sb.append(", uid='").append(uid).append('\'');
        sb.append(", gid='").append(gid).append('\'');
        sb.append(", mode='").append(mode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

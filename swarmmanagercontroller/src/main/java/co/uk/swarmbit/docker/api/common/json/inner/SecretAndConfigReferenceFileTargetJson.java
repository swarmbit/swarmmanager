package co.uk.swarmbit.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SecretAndConfigReferenceFileTargetJson {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("UID")
    private String uid;

    @JsonProperty("GID")
    private String gid;

    @JsonProperty("Mode")
    private Integer mode;

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

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SecretAndConfigReferenceFileTargetJson{");
        sb.append("name='").append(name).append('\'');
        sb.append(", uid='").append(uid).append('\'');
        sb.append(", gid='").append(gid).append('\'');
        sb.append(", mode='").append(mode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SecretReferenceJson {

    @JsonProperty("File")
    private SecretReferenceFileTargetJson file;

    @JsonProperty("SecretID")
    private String secretID;

    @JsonProperty("SecretName")
    private String secretName;

    public SecretReferenceFileTargetJson getFile() {
        return file;
    }

    public void setFile(SecretReferenceFileTargetJson file) {
        this.file = file;
    }

    public String getSecretID() {
        return secretID;
    }

    public void setSecretID(String secretID) {
        this.secretID = secretID;
    }

    public String getSecretName() {
        return secretName;
    }

    public void setSecretName(String secretName) {
        this.secretName = secretName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SecretReferenceJson{");
        sb.append("file=").append(file);
        sb.append(", secretID='").append(secretID).append('\'');
        sb.append(", secretName='").append(secretName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TLSInfoJson {

    @JsonProperty("TrustRoot")
    private String trustRoot;

    @JsonProperty("CertIssuerSubject")
    private String certIssuerSubject;

    @JsonProperty("CertIssuerPublicKey")
    private String certIssuerPublicKey;

    public String getTrustRoot() {
        return trustRoot;
    }

    public void setTrustRoot(String trustRoot) {
        this.trustRoot = trustRoot;
    }

    public String getCertIssuerSubject() {
        return certIssuerSubject;
    }

    public void setCertIssuerSubject(String certIssuerSubject) {
        this.certIssuerSubject = certIssuerSubject;
    }

    public String getCertIssuerPublicKey() {
        return certIssuerPublicKey;
    }

    public void setCertIssuerPublicKey(String certIssuerPublicKey) {
        this.certIssuerPublicKey = certIssuerPublicKey;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TLSInfoJson{");
        sb.append("trustRoot='").append(trustRoot).append('\'');
        sb.append(", certIssuerSubject='").append(certIssuerSubject).append('\'');
        sb.append(", certIssuerPublicKey='").append(certIssuerPublicKey).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

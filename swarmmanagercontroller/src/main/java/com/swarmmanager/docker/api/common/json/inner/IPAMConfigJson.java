package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IPAMConfigJson {

    @JsonProperty("Subnet")
    private String subnet;

    @JsonProperty("IPRange")
    private String ipRange;

    @JsonProperty("Gateway")
    private String gateway;

    @JsonProperty("AuxiliaryAddresses")
    private Map<String, String> auxAddress;

    public String getSubnet() {
        return subnet;
    }

    public void setSubnet(String subnet) {
        this.subnet = subnet;
    }

    public String getIpRange() {
        return ipRange;
    }

    public void setIpRange(String ipRange) {
        this.ipRange = ipRange;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public Map<String, String> getAuxAddress() {
        return auxAddress;
    }

    public void setAuxAddress(Map<String, String> auxAddress) {
        this.auxAddress = auxAddress;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IPAMConfigJson{");
        sb.append("subnet='").append(subnet).append('\'');
        sb.append(", ipRange='").append(ipRange).append('\'');
        sb.append(", gateway='").append(gateway).append('\'');
        sb.append(", auxAddress=").append(auxAddress);
        sb.append('}');
        return sb.toString();
    }
}

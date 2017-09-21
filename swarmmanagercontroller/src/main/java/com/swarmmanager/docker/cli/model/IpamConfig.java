package com.swarmmanager.docker.cli.model;

import java.util.Map;

public class IpamConfig {

    private String subnet;

    private String ipRange;

    private String gateway;

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
}

package com.swarmmanager.docker.command.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Port {

    public static enum Protocol {
        TCP, UDP;

        public static Protocol getProtocol(String protocolName) {
            if (protocolName != null) {
                switch (protocolName.toLowerCase()) {
                    case "UDP":
                        return Protocol.UDP;
                }
            }
            return Protocol.TCP;
        }
    }

    private String protocol;

    private int published;

    private int target;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol.toString().toLowerCase();
    }

    public int getPublished() {
        return published;
    }

    public void setPublished(int published) {
        this.published = published;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }
}

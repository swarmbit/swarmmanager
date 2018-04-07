package co.uk.swarmbit.docker.cli.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Port {

    public static enum Protocol {
        TCP, UDP;

        public static Protocol getProtocol(String protocolName) {
            if (protocolName != null) {
                switch (protocolName.toUpperCase()) {
                    case "UDP":
                        return Protocol.UDP;
                    case "TCP":
                        return Protocol.TCP;
                }
            }
            return null;
        }
    }

    private String protocol;

    private Integer published;

    private Integer target;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        if (protocol != null) {
            this.protocol = protocol.toString().toLowerCase();
        }
    }

    public void setProtocol(String protocolStr) {
        if (protocolStr != null) {
            Protocol protocol = Protocol.getProtocol(protocolStr);
            if (protocol != null) {
                this.protocol = protocol.toString().toLowerCase();
            }
        }
    }

    public Integer getPublished() {
        return published;
    }

    public void setPublished(Integer published) {
        this.published = published;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }
}

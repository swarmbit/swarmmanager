package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swarmmanager.docker.api.common.annotation.DockerRemoteApiMinVersion;

import java.util.Arrays;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ContainerSpecJson {

    @JsonProperty("Image")
    private String image;

    @JsonProperty("Labels")
    private Map<String, String> labels;

    @JsonProperty("Command")
    private String[] command;

    @JsonProperty("Args")
    private String[] args;

    @JsonProperty("Hostname")
    private String hostname;

    @JsonProperty("Env")
    private String[] env;

    @JsonProperty("Dir")
    private String dir;

    @JsonProperty("User")
    private String user;

    @JsonProperty("Groups")
    private String[] groups;

    @DockerRemoteApiMinVersion("v1.25")
    @JsonProperty("TTY")
    private boolean tty;

    @JsonProperty("OpenStdin")
    private boolean openStdin;

    @JsonProperty("Mounts")
    private MountJson[] mounts;

    @JsonProperty("StopGracePeriod")
    private String stopGracePeriod;

    @JsonProperty("Healthcheck")
    private HealthConfigJson healthConfig;

    @JsonProperty("Hosts")
    private String[] hosts;

    @DockerRemoteApiMinVersion("v1.25")
    @JsonProperty("DNSConfig")
    private DNSConfigJson dnsConfig;

    @JsonProperty("Secrets")
    private SecretReferenceJson secrets;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }

    public String[] getCommand() {
        return command;
    }

    public void setCommand(String[] command) {
        this.command = command;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String[] getEnv() {
        return env;
    }

    public void setEnv(String[] env) {
        this.env = env;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String[] getGroups() {
        return groups;
    }

    public void setGroups(String[] groups) {
        this.groups = groups;
    }

    public boolean isTty() {
        return tty;
    }

    public void setTty(boolean tty) {
        this.tty = tty;
    }

    public boolean isOpenStdin() {
        return openStdin;
    }

    public void setOpenStdin(boolean openStdin) {
        this.openStdin = openStdin;
    }

    public MountJson[] getMounts() {
        return mounts;
    }

    public void setMounts(MountJson[] mounts) {
        this.mounts = mounts;
    }

    public String getStopGracePeriod() {
        return stopGracePeriod;
    }

    public void setStopGracePeriod(String stopGracePeriod) {
        this.stopGracePeriod = stopGracePeriod;
    }

    public HealthConfigJson getHealthConfig() {
        return healthConfig;
    }

    public void setHealthConfig(HealthConfigJson healthConfig) {
        this.healthConfig = healthConfig;
    }

    public String[] getHosts() {
        return hosts;
    }

    public void setHosts(String[] hosts) {
        this.hosts = hosts;
    }

    public DNSConfigJson getDnsConfig() {
        return dnsConfig;
    }

    public void setDnsConfig(DNSConfigJson dnsConfig) {
        this.dnsConfig = dnsConfig;
    }

    public SecretReferenceJson getSecrets() {
        return secrets;
    }

    public void setSecrets(SecretReferenceJson secrets) {
        this.secrets = secrets;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ContainerSpecJson{");
        sb.append("image='").append(image).append('\'');
        sb.append(", labels=").append(labels);
        sb.append(", cli=").append(Arrays.toString(command));
        sb.append(", args=").append(Arrays.toString(args));
        sb.append(", hostname='").append(hostname).append('\'');
        sb.append(", env=").append(Arrays.toString(env));
        sb.append(", dir='").append(dir).append('\'');
        sb.append(", user='").append(user).append('\'');
        sb.append(", groups=").append(Arrays.toString(groups));
        sb.append(", tty=").append(tty);
        sb.append(", openStdin=").append(openStdin);
        sb.append(", mounts=").append(Arrays.toString(mounts));
        sb.append(", stopGracePeriod=").append(stopGracePeriod);
        sb.append(", healthConfig=").append(healthConfig);
        sb.append(", hosts=").append(Arrays.toString(hosts));
        sb.append(", dnsConfig=").append(dnsConfig);
        sb.append(", secrets=").append(secrets);
        sb.append('}');
        return sb.toString();
    }
}

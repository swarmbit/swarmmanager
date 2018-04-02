package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swarmmanager.docker.api.common.annotation.DockerRemoteApiMinVersion;
import com.swarmmanager.docker.api.common.json.ConfigSpecJson;

import java.util.Arrays;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    @JsonProperty("Registry")
    private String user;

    @JsonProperty("Groups")
    private String[] groups;

    @DockerRemoteApiMinVersion("v1.25")
    @JsonProperty("TTY")
    private Boolean tty;

    @JsonProperty("OpenStdin")
    private Boolean openStdin;

    @JsonProperty("Mounts")
    private MountJson[] mounts;

    @JsonProperty("StopGracePeriod")
    private Long stopGracePeriod;

    @DockerRemoteApiMinVersion("v1.28")
    @JsonProperty("StopSignal")
    private String stopSignal;

    @JsonProperty("Healthcheck")
    private HealthConfigJson healthConfig;

    @JsonProperty("Hosts")
    private String[] hosts;

    @DockerRemoteApiMinVersion("v1.25")
    @JsonProperty("DNSConfig")
    private DNSConfigJson dnsConfig;

    @JsonProperty("Secrets")
    private SecretReferenceJson[] secrets;

    @JsonProperty("Configs")
    private ConfigReferenceJson[] configs;

    @DockerRemoteApiMinVersion("v1.28")
    @JsonProperty("ReadOnly")
    private Boolean readOnly;

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

    public Boolean isTty() {
        return tty;
    }

    public void setTty(Boolean tty) {
        this.tty = tty;
    }

    public Boolean isOpenStdin() {
        return openStdin;
    }

    public void setOpenStdin(Boolean openStdin) {
        this.openStdin = openStdin;
    }

    public MountJson[] getMounts() {
        return mounts;
    }

    public void setMounts(MountJson[] mounts) {
        this.mounts = mounts;
    }

    public Long getStopGracePeriod() {
        return stopGracePeriod;
    }

    public void setStopGracePeriod(Long stopGracePeriod) {
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

    public SecretReferenceJson[] getSecrets() {
        return secrets;
    }

    public void setSecrets(SecretReferenceJson[] secrets) {
        this.secrets = secrets;
    }

    public ConfigReferenceJson[] getConfigs() {
        return configs;
    }

    public void setConfigs(ConfigReferenceJson[] configs) {
        this.configs = configs;
    }

    public Boolean getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public String getStopSignal() {
        return stopSignal;
    }

    public void setStopSignal(String stopSignal) {
        this.stopSignal = stopSignal;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ContainerSpecJson{");
        sb.append("image='").append(image).append('\'');
        sb.append(", labels=").append(labels);
        sb.append(", command=").append(Arrays.toString(command));
        sb.append(", args=").append(Arrays.toString(args));
        sb.append(", hostname='").append(hostname).append('\'');
        sb.append(", env=").append(Arrays.toString(env));
        sb.append(", dir='").append(dir).append('\'');
        sb.append(", user='").append(user).append('\'');
        sb.append(", groups=").append(Arrays.toString(groups));
        sb.append(", tty=").append(tty);
        sb.append(", openStdin=").append(openStdin);
        sb.append(", mounts=").append(Arrays.toString(mounts));
        sb.append(", stopGracePeriod='").append(stopGracePeriod).append('\'');
        sb.append(", stopSignal='").append(stopSignal).append('\'');
        sb.append(", healthConfig=").append(healthConfig);
        sb.append(", hosts=").append(Arrays.toString(hosts));
        sb.append(", dnsConfig=").append(dnsConfig);
        sb.append(", secrets=").append(Arrays.toString(secrets));
        sb.append(", configs=").append(Arrays.toString(configs));
        sb.append(", readOnly=").append(readOnly);
        sb.append('}');
        return sb.toString();
    }
}

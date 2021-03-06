package co.uk.swarmbit.docker.config;

public class DockerSwarmConfig {

    private String id;

    private String name;

    private String apiVersion;

    private String description;

    private DockerClientConfig client;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DockerClientConfig getClient() {
        return client;
    }

    public void setClient(DockerClientConfig client) {
        this.client = client;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DockerSwarmConfig{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", apiVersion='").append(apiVersion).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", client=").append(client);
        sb.append('}');
        return sb.toString();
    }
}

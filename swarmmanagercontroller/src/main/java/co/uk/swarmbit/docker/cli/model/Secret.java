package co.uk.swarmbit.docker.cli.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Secret {

    private String id;

    private Long createdAt;

    private Long updatedAt;

    private String name;

    private Map<String, String> labels;

    private String data;

    private String templatingName;

    private Map<String, String> templatingOptions;

    private String driverName;

    private Map<String, String> driverOptions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTemplatingName() {
        return templatingName;
    }

    public void setTemplatingName(String templatingName) {
        this.templatingName = templatingName;
    }

    public Map<String, String> getTemplatingOptions() {
        return templatingOptions;
    }

    public void setTemplatingOptions(Map<String, String> templatingOptions) {
        this.templatingOptions = templatingOptions;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Map<String, String> getDriverOptions() {
        return driverOptions;
    }

    public void setDriverOptions(Map<String, String> driverOptions) {
        this.driverOptions = driverOptions;
    }
}

package co.uk.swarmbit.docker.cli.model;

import java.util.Map;

public class VolumeMountOptions {

    private Boolean noCopy;

    private Map<String, String> labels;

    private String driver;

    private Map<String, String> driverOptions;

    public Boolean isNoCopy() {
        return noCopy;
    }

    public void setNoCopy(Boolean noCopy) {
        this.noCopy = noCopy;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public Map<String, String> getDriverOptions() {
        return driverOptions;
    }

    public void setDriverOptions(Map<String, String> driverOptions) {
        this.driverOptions = driverOptions;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VolumeMountOptions{");
        sb.append("noCopy=").append(noCopy);
        sb.append(", labels=").append(labels);
        sb.append(", driver='").append(driver).append('\'');
        sb.append(", driverOptions=").append(driverOptions);
        sb.append('}');
        return sb.toString();
    }

}

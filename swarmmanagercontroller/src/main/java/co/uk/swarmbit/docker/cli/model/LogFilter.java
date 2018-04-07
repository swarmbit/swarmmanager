package co.uk.swarmbit.docker.cli.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class LogFilter {

    private String taskId;

    private Long replica;

    private String serviceId;

    private String nodeId;

    public LogFilter(String taskId, Long replica, String serviceId, String nodeId) {
        this.taskId = taskId;
        this.replica = replica;
        this.serviceId = serviceId;
        this.nodeId = nodeId;
    }

    public String getTaskId() {
        return taskId;
    }

    public Long getReplica() {
        return replica;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getNodeId() {
        return nodeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        LogFilter logFilter = (LogFilter) o;

        return new EqualsBuilder()
                .append(replica, logFilter.replica)
                .append(taskId, logFilter.taskId)
                .append(serviceId, logFilter.serviceId)
                .append(nodeId, logFilter.nodeId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(taskId)
                .append(replica)
                .append(serviceId)
                .append(nodeId)
                .toHashCode();
    }
}

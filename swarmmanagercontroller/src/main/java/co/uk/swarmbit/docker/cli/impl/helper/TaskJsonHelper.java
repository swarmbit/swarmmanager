package co.uk.swarmbit.docker.cli.impl.helper;

import co.uk.swarmbit.docker.api.common.json.inner.PortConfigJson;
import co.uk.swarmbit.docker.api.common.json.inner.TaskStatusJson;
import co.uk.swarmbit.docker.api.common.json.TaskJson;
import co.uk.swarmbit.docker.api.common.json.inner.PortStatusJson;
import co.uk.swarmbit.docker.cli.model.Port;
import co.uk.swarmbit.docker.cli.model.Task;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static co.uk.swarmbit.docker.api.common.util.DockerDateFormatter.fromDateStringToDuration;

public class TaskJsonHelper {

    public static List<Task> getTasks(List<TaskJson> tasksList) {
        List<Task> tasks = new ArrayList<>();
        for (TaskJson taskJson : tasksList) {
            Task task = new Task();
            task.setId(taskJson.getId());
            if (taskJson.getSlot() != null) {
                task.setReplica(taskJson.getSlot());
            }
            task.setDesiredState(taskJson.getDesiredState());
            task.setServiceId(taskJson.getServiceId());
            task.setImage(taskJson.getSpec().getContainerSpec().getImage());
            TaskStatusJson status = taskJson.getStatus();
            if (status != null) {
                String err = status.getErr();
                if (err != null) {
                    task.setErrorMessage(status.getErr());
                } else {
                    task.setErrorMessage("");
                }
                task.setState(status.getTaskState());

                String timestamp = status.getTimestamp();
                task.setLastStateChange(fromDateStringToDuration(timestamp));

                PortStatusJson portStatus = status.getPortStatus();
                if (portStatus != null) {
                    PortConfigJson[] portConfigs = portStatus.getPorts();
                    if (portConfigs != null) {
                        List<Port> ports = new ArrayList<>();
                        for (PortConfigJson portConfig : portConfigs) {
                            Port port = new Port();
                            port.setPublished(portConfig.getPublishedPort());
                            port.setProtocol(Port.Protocol.getProtocol(portConfig.getProtocol()));
                            port.setTarget(portConfig.getTargetPort());
                            ports.add(port);
                        }
                        task.setPorts(ports);
                    } else {
                        task.setPorts(new ArrayList<>());
                    }
                } else {
                    task.setPorts(new ArrayList<>());
                }
            }

            String nodeId = taskJson.getNodeId();
            task.setNodeId(nodeId);
            tasks.add(task);
        }
        return tasks;
    }

    public static void sortTasks(List<Task> tasks) {
        tasks.sort((task1, task2) -> {
            if (!StringUtils.equals(task1.getServiceName(), task2.getServiceName())) {
                return task1.getServiceName().compareTo(task2.getServiceName());
            }
            if (task1.getReplica() != null && task2.getReplica() != null) {
                Long result = task1.getReplica() - task2.getReplica();
                if (result != 0) {
                    return result.intValue();
                }
            }
            return task1.getLastStateChange().compareTo(task2.getLastStateChange());
        });
    }

}

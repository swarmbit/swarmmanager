package co.uk.swarmbit.docker.api.tasks;

import co.uk.swarmbit.docker.api.services.parameters.ServiceLogsParameters;
import co.uk.swarmbit.docker.api.tasks.parameters.TasksListParameters;
import co.uk.swarmbit.docker.api.common.json.TaskJson;

import java.util.List;

public interface TasksApi {

    List<TaskJson> listTasks(String swarmId, TasksListParameters parameters);

    TaskJson inspectTask(String swarmId, String id);

    byte[] getTaskLogs(String swarmId, String id, ServiceLogsParameters parameters);

}

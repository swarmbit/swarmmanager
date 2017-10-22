package com.swarmmanager.docker.api.tasks;

import com.swarmmanager.docker.api.services.parameters.ServiceLogsParameters;
import com.swarmmanager.docker.api.tasks.parameters.TasksListParameters;
import com.swarmmanager.docker.api.common.json.TaskJson;

import java.util.List;

public interface TasksApi {

    List<TaskJson> listTasks(String swarmId, TasksListParameters parameters);

    TaskJson inspectTask(String swarmId, String id);

    byte[] getTaskLogs(String swarmId, String id, ServiceLogsParameters parameters);

}

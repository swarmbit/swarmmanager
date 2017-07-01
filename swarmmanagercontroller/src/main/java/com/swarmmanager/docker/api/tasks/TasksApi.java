package com.swarmmanager.docker.api.tasks;

import com.swarmmanager.docker.api.tasks.parameters.TasksFiltersParameters;
import com.swarmmanager.docker.api.common.json.TaskJson;

import java.util.List;

public interface TasksApi {

    List<TaskJson> listTasks(TasksFiltersParameters parameters);

    TaskJson inspectTask(String id);
}

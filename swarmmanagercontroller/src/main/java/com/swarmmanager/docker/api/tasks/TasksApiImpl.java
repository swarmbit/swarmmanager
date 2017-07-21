package com.swarmmanager.docker.api.tasks;

import com.swarmmanager.docker.api.common.AbstractApiImpl;
import com.swarmmanager.docker.api.services.parameters.ServiceLogsParameters;
import com.swarmmanager.docker.api.tasks.parameters.TasksListParameters;
import com.swarmmanager.docker.api.common.json.TaskJson;
import com.swarmmanager.rest.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TasksApiImpl extends AbstractApiImpl implements TasksApi {

    private static final String TASKS_PATH = "/tasks";

    private static final String LOGS_PATH = "/logs";

    @Override
    public List<TaskJson> listTasks(TasksListParameters parameters) {
        return listObjects(TASKS_PATH, new RestResponseType<List<TaskJson>>() {}, parameters);
    }

    @Override
    public TaskJson inspectTask(String id) {
       return inspectObject(TASKS_PATH, new RestResponseType<TaskJson>() {}, id);
    }

    @Override
    public byte[] getTaskLogs(String id, ServiceLogsParameters parameters) {
        return this.getObjectLogs(TASKS_PATH + "/" + id + LOGS_PATH, parameters);
    }

}

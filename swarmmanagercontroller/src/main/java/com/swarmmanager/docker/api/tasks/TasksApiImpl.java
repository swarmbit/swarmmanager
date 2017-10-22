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
    public List<TaskJson> listTasks(String swarmId, TasksListParameters parameters) {
        return listObjects(TASKS_PATH, swarmId, new RestResponseType<List<TaskJson>>() {}, parameters);
    }

    @Override
    public TaskJson inspectTask(String swarmId, String id) {
       return inspectObject(TASKS_PATH, swarmId, new RestResponseType<TaskJson>() {}, id);
    }

    @Override
    public byte[] getTaskLogs(String swarmId, String id, ServiceLogsParameters parameters) {
        return this.getObjectLogs(TASKS_PATH + "/" + id + LOGS_PATH, swarmId, parameters);
    }

}

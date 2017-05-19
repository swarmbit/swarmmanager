package com.swarmmanager.docker.api.tasks;

import com.swarmmanager.docker.api.tasks.parameters.TaskInspectParameters;
import com.swarmmanager.docker.api.tasks.parameters.TasksListParameters;
import com.swarmmanager.docker.api.common.client.DockerWebClient;
import com.swarmmanager.docker.api.common.json.TaskJson;
import com.swarmmanager.rest.RestExecutorFactory;
import com.swarmmanager.rest.RestMethod;
import com.swarmmanager.rest.RestParameters;
import com.swarmmanager.rest.RestResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TasksApiImpl implements TasksApi {

    private static final String TASKS_PATH = "/tasks";

    @Autowired
    private DockerWebClient dockerWebClient;

    @Override
    public List<TaskJson> listTasks(TasksListParameters parameters) {
        if (parameters != null) {
            RestParameters restParameters = new RestParameters(dockerWebClient.getBaseResource())
                    .setPath(TASKS_PATH);
            if (parameters.getFilters().isPresent()) {
                restParameters.addQueryParam(parameters.getFilters().get());
            }
            return RestExecutorFactory.createRestExecutor(RestMethod.GET).execute(restParameters, new RestResponseType<List<TaskJson>>() {});
        }
        return new ArrayList<>();
    }

    @Override
    public TaskJson inspectTask(TaskInspectParameters parameters) {
        RestParameters restParameters = new RestParameters(dockerWebClient.getBaseResource())
                .setPath(TASKS_PATH + "/" + parameters.getId());
       return RestExecutorFactory.createRestExecutor(RestMethod.GET).execute(restParameters, new RestResponseType<TaskJson>() {});
    }
}

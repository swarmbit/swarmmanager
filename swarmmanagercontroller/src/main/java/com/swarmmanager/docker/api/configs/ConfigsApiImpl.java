package com.swarmmanager.docker.api.configs;

import com.swarmmanager.docker.api.common.json.ConfigCreateResponseJson;
import com.swarmmanager.docker.api.common.json.ConfigJson;
import com.swarmmanager.docker.api.configs.parameters.ConfigsCreateParameters;
import com.swarmmanager.docker.api.configs.parameters.ConfigsUpdateParameters;
import com.swarmmanager.docker.api.configs.parameters.ConfigsListParameters;
import com.swarmmanager.docker.api.common.AbstractApiImpl;
import com.swarmmanager.rest.RestResponseType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConfigsApiImpl extends AbstractApiImpl implements ConfigsApi {

    private static final String CONFIGS_PATH = "/configs";

    private static final String CREATE_PATH = "/create";

    private static final String UPDATE_PATH = "/update";

    @Override
    public List<ConfigJson> listConfigs(String swarmId, ConfigsListParameters parameters) {
        return listObjects(CONFIGS_PATH, swarmId, new RestResponseType<List<ConfigJson>>() {}, parameters);
    }

    @Override
    public ConfigJson inspectConfig(String swarmId, String id) {
        return inspectObject(CONFIGS_PATH, swarmId, new RestResponseType<ConfigJson>() {}, id);
    }

    @Override
    public ConfigCreateResponseJson createConfig(String swarmId, ConfigsCreateParameters parameters) {
        return createObject(CONFIGS_PATH + CREATE_PATH, swarmId, new RestResponseType<ConfigCreateResponseJson>() {}, parameters);
    }

    @Override
    public void deleteConfig(String swarmId, String id) {
        deleteObject(CONFIGS_PATH + "/" + id, swarmId, new RestResponseType<Void>() {});
    }

    @Override
    public void updateConfig(String swarmId, String id, ConfigsUpdateParameters parameters) {
        updateObject(CONFIGS_PATH + "/" + id + UPDATE_PATH, swarmId, new RestResponseType<Void>() {}, parameters, parameters);
    }
}

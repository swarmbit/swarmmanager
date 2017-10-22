package com.swarmmanager.docker.api.configs;

import com.swarmmanager.docker.api.common.json.ConfigCreateResponseJson;
import com.swarmmanager.docker.api.common.json.ConfigJson;
import com.swarmmanager.docker.api.configs.parameters.ConfigsCreateParameters;
import com.swarmmanager.docker.api.configs.parameters.ConfigsUpdateParameters;
import com.swarmmanager.docker.api.configs.parameters.ConfigsListParameters;

import java.util.List;

public interface ConfigsApi {

    List<ConfigJson> listConfigs(String swarmId, ConfigsListParameters parameters);

    ConfigJson inspectConfig(String swarmId, String id);

    ConfigCreateResponseJson createConfig(String swarmId, ConfigsCreateParameters parameters);

    void deleteConfig(String swarmId, String id);

    void updateConfig(String swarmId, String id, ConfigsUpdateParameters parameters);
}

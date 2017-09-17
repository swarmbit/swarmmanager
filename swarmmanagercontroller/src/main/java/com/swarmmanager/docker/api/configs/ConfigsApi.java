package com.swarmmanager.docker.api.configs;

import com.swarmmanager.docker.api.common.json.ConfigCreateResponseJson;
import com.swarmmanager.docker.api.common.json.ConfigJson;
import com.swarmmanager.docker.api.configs.parameters.ConfigsCreateParameters;
import com.swarmmanager.docker.api.configs.parameters.ConfigsUpdateParameters;
import com.swarmmanager.docker.api.configs.parameters.ConfigsListParameters;

import java.util.List;

public interface ConfigsApi {

    List<ConfigJson> listConfigs(ConfigsListParameters parameters);

    ConfigJson inspectConfig(String id);

    ConfigCreateResponseJson createConfig(ConfigsCreateParameters parameters);

    void deleteConfig(String id);

    void updateConfig(String id, ConfigsUpdateParameters parameters);
}

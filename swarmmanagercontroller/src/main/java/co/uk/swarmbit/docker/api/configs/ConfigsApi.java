package co.uk.swarmbit.docker.api.configs;

import co.uk.swarmbit.docker.api.configs.parameters.ConfigsListParameters;
import co.uk.swarmbit.docker.api.common.json.ConfigCreateResponseJson;
import co.uk.swarmbit.docker.api.common.json.ConfigJson;
import co.uk.swarmbit.docker.api.configs.parameters.ConfigsCreateParameters;
import co.uk.swarmbit.docker.api.configs.parameters.ConfigsUpdateParameters;

import java.util.List;

public interface ConfigsApi {

    List<ConfigJson> listConfigs(String swarmId, ConfigsListParameters parameters);

    ConfigJson inspectConfig(String swarmId, String id);

    ConfigCreateResponseJson createConfig(String swarmId, ConfigsCreateParameters parameters);

    void deleteConfig(String swarmId, String id);

    void updateConfig(String swarmId, String id, ConfigsUpdateParameters parameters);
}

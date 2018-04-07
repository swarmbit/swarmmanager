package co.uk.swarmbit.docker.api.configs.parameters;

import co.uk.swarmbit.docker.api.common.json.ConfigSpecJson;
import co.uk.swarmbit.docker.api.common.parameters.RequestBodyParameter;

public class ConfigsCreateParameters implements RequestBodyParameter {

    private ConfigSpecJson config;

    public ConfigsCreateParameters() {
        config = new ConfigSpecJson();
    }

    public ConfigsCreateParameters setConfig(ConfigSpecJson config) {
        this.config = config;
        return this;
    }

    @Override
    public Object getRequestBody() {
        return config;
    }
}

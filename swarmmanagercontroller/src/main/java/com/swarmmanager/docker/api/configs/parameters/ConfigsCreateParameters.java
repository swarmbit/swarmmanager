package com.swarmmanager.docker.api.configs.parameters;

import com.swarmmanager.docker.api.common.json.ConfigSpecJson;
import com.swarmmanager.docker.api.common.parameters.RequestBodyParameter;

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

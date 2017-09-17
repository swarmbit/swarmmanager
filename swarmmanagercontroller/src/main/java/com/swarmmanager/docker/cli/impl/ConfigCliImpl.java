package com.swarmmanager.docker.cli.impl;

import com.swarmmanager.docker.api.common.json.*;
import com.swarmmanager.docker.api.common.json.inner.VersionJson;
import com.swarmmanager.docker.api.common.util.DockerDateFormatter;
import com.swarmmanager.docker.api.configs.ConfigsApi;
import com.swarmmanager.docker.api.configs.parameters.ConfigsCreateParameters;
import com.swarmmanager.docker.api.configs.parameters.ConfigsListParameters;
import com.swarmmanager.docker.api.configs.parameters.ConfigsUpdateParameters;
import com.swarmmanager.docker.cli.ConfigCli;
import com.swarmmanager.docker.cli.model.Config;
import com.swarmmanager.util.EncoderDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ConfigCliImpl implements ConfigCli {

    @Autowired
    private ConfigsApi configsApi;

    @Override
    public Config create(Config config) {
        ConfigSpecJson configSpecJson = new ConfigSpecJson();
        configSpecJson.setName(config.getName());
        configSpecJson.setLabels(config.getLabels());
        configSpecJson.setData(EncoderDecoder.base64URLEncode(config.getData()));
        ConfigCreateResponseJson response = configsApi.createConfig(new ConfigsCreateParameters()
                .setConfig(configSpecJson));
        config.setId(response.getId());
        return config;
    }

    @Override
    public void rm(String configId) {
        configsApi.deleteConfig(configId);
    }

    @Override
    public List<Config> ls() {
        List<Config> configs = new ArrayList<>();
        List<ConfigJson> configsJson = configsApi.listConfigs(new ConfigsListParameters());
        configsJson.forEach(configJson -> configs.add(fromConfigJson(configJson)));
        return configs;
    }

    @Override
    public Config inspect(String configId) {
        ConfigJson configJson = configsApi.inspectConfig(configId);
        return fromConfigJson(configJson);
    }

    @Override
    public void update(String configId, Config config) {
        ConfigJson configJson = configsApi.inspectConfig(configId);
        VersionJson versionJson = configJson.getVersion();
        ConfigsUpdateParameters parameters = new ConfigsUpdateParameters();
        parameters.setVersionQueryParam(versionJson.getIndex());
        if (config.getLabels() != null) {
            configJson.getSpec().setLabels(config.getLabels());
        }
        if (config.getData() != null) {
            configJson.getSpec().setData(config.getData());
        }
        parameters.setConfig(configJson.getSpec());
        configsApi.updateConfig(configId, parameters);
    }

    private Config fromConfigJson(ConfigJson configJson) {
        Config config = new Config();
        config.setId(configJson.getId());
        ZonedDateTime createdAt = DockerDateFormatter.fromDateStringToZonedDateTime(configJson.getCreatedAt());
        ZonedDateTime updatedAt = DockerDateFormatter.fromDateStringToZonedDateTime(configJson.getUpdatedAt());
        config.setCreatedAt(createdAt.toInstant().toEpochMilli());
        config.setUpdatedAt(updatedAt.toInstant().toEpochMilli());
        config.setName(configJson.getSpec().getName());
        config.setLabels(configJson.getSpec().getLabels());
        config.setData(EncoderDecoder.base64URLDecode(configJson.getSpec().getData()));
        return config;
    }
}

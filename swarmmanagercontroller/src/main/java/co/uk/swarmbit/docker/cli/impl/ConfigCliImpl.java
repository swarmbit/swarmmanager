package co.uk.swarmbit.docker.cli.impl;

import co.uk.swarmbit.docker.api.common.json.ConfigCreateResponseJson;
import co.uk.swarmbit.docker.api.common.json.ConfigJson;
import co.uk.swarmbit.docker.api.common.json.ConfigSpecJson;
import co.uk.swarmbit.docker.api.common.json.inner.DriverJson;
import co.uk.swarmbit.docker.api.common.json.inner.VersionJson;
import co.uk.swarmbit.docker.api.common.util.DockerDateFormatter;
import co.uk.swarmbit.docker.api.configs.ConfigsApi;
import co.uk.swarmbit.docker.api.configs.parameters.ConfigsCreateParameters;
import co.uk.swarmbit.docker.api.configs.parameters.ConfigsListParameters;
import co.uk.swarmbit.docker.api.configs.parameters.ConfigsUpdateParameters;
import co.uk.swarmbit.docker.cli.ConfigCli;
import co.uk.swarmbit.docker.cli.model.Config;
import co.uk.swarmbit.util.EncoderDecoder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ConfigCliImpl implements ConfigCli {

    private final ConfigsApi configsApi;

    @Autowired
    public ConfigCliImpl(ConfigsApi configsApi) {
        this.configsApi = configsApi;
    }

    @Override
    public Config create(String swarmId, Config config) {
        ConfigSpecJson configSpecJson = new ConfigSpecJson();
        configSpecJson.setName(config.getName());
        configSpecJson.setLabels(config.getLabels());
        configSpecJson.setData(EncoderDecoder.base64Encode(config.getData()));
        if (StringUtils.isNotEmpty(config.getTemplatingName())) {
            DriverJson templating = new DriverJson();
            templating.setName(config.getTemplatingName());
            templating.setOptions(config.getTemplatingOptions());
            configSpecJson.setTemplating(templating);
        }
        ConfigCreateResponseJson response = configsApi.createConfig(swarmId, new ConfigsCreateParameters()
                .setConfig(configSpecJson));
        config.setId(response.getId());
        return config;
    }

    @Override
    public void rm(String swarmId, String configId) {
        configsApi.deleteConfig(swarmId, configId);
    }

    @Override
    public List<Config> ls(String swarmId) {
        List<Config> configs = new ArrayList<>();
        List<ConfigJson> configsJson = configsApi.listConfigs(swarmId, new ConfigsListParameters());
        configsJson.forEach(configJson -> configs.add(fromConfigJson(configJson, false)));
        return configs;
    }

    @Override
    public Config inspect(String swarmId, String configId) {
        ConfigJson configJson = configsApi.inspectConfig(swarmId, configId);
        return fromConfigJson(configJson, true);
    }

    @Override
    public void update(String swarmId, String configId, Config config) {
        ConfigJson configJson = configsApi.inspectConfig(swarmId, configId);
        VersionJson versionJson = configJson.getVersion();
        ConfigsUpdateParameters parameters = new ConfigsUpdateParameters();
        parameters.setVersionQueryParam(versionJson.getIndex());
        if (config.getLabels() != null) {
            configJson.getSpec().setLabels(config.getLabels());
        }
        parameters.setConfig(configJson.getSpec());
        configsApi.updateConfig(swarmId, configId, parameters);
    }

    private Config fromConfigJson(ConfigJson configJson, boolean includeData) {
        Config config = new Config();
        config.setId(configJson.getId());
        ZonedDateTime createdAt = DockerDateFormatter.fromDateStringToZonedDateTime(configJson.getCreatedAt());
        ZonedDateTime updatedAt = DockerDateFormatter.fromDateStringToZonedDateTime(configJson.getUpdatedAt());
        if (createdAt != null) {
            config.setCreatedAt(createdAt.toInstant().toEpochMilli());
        }
        if (updatedAt != null) {
            config.setUpdatedAt(updatedAt.toInstant().toEpochMilli());
        }
        config.setName(configJson.getSpec().getName());
        config.setLabels(configJson.getSpec().getLabels());
        if (includeData) {
            config.setData(EncoderDecoder.base64URLDecode(configJson.getSpec().getData()));
        }
        DriverJson templating = configJson.getSpec().getTemplating();
        if (templating != null) {
            config.setTemplatingName(templating.getName());
            config.setTemplatingOptions(templating.getOptions());
        }
        return config;
    }
}

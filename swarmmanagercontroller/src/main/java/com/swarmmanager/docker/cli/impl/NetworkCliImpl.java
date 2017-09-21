package com.swarmmanager.docker.cli.impl;

import com.swarmmanager.docker.api.common.json.NetworkCreateResponseJson;
import com.swarmmanager.docker.api.common.json.NetworkJson;
import com.swarmmanager.docker.api.common.json.inner.IPAMConfigJson;
import com.swarmmanager.docker.api.common.json.inner.IPAMJson;
import com.swarmmanager.docker.api.networks.NetworksApi;
import com.swarmmanager.docker.api.networks.parameters.NetworkCreateParameters;
import com.swarmmanager.docker.api.networks.parameters.NetworkFilters;
import com.swarmmanager.docker.api.networks.parameters.NetworkListParameters;
import com.swarmmanager.docker.cli.NetworkCli;
import com.swarmmanager.docker.cli.model.IpamConfig;
import com.swarmmanager.docker.cli.model.Network;
import com.swarmmanager.docker.cli.model.NetworkSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.swarmmanager.docker.api.common.util.DockerDateFormatter.*;

@Component
public class NetworkCliImpl implements NetworkCli {

    @Autowired
    private NetworksApi networksApi;

    @Override
    public Network create(Network network) {
        network.setDriver("overlay");
        NetworkJson networkJson = new NetworkJson();
        networkJson.setDriver(network.getDriver());
        networkJson.setName(network.getName());
        networkJson.setLabels(network.getLabels());
        networkJson.setOptions(network.getOptions());
        networkJson.setInternal(network.getInternal());
        networkJson.setAttachable(network.getAttachable());
        networkJson.setEnableIPv6(network.getIpv6());
        IPAMJson ipamJson = new IPAMJson();
        ipamJson.setDriver(network.getIpamDriver());
        ipamJson.setOptions(network.getIpamOptions());
        if (network.getIpamConfig() != null) {
            IPAMConfigJson[] ipamConfigJsons = new IPAMConfigJson[network.getIpamConfig().size()];
            for (int i = 0; i < ipamConfigJsons.length; i++) {
                IpamConfig ipamConfig = network.getIpamConfig().get(i);
                IPAMConfigJson ipamConfigJson = new IPAMConfigJson();
                ipamConfigJson.setIpRange(ipamConfig.getIpRange());
                ipamConfigJson.setAuxAddress(ipamConfig.getAuxAddress());
                ipamConfigJson.setGateway(ipamConfig.getGateway());
                ipamConfigJson.setSubnet(ipamConfig.getSubnet());
                ipamConfigJsons[i] = ipamConfigJson;
            }
            ipamJson.setConfig(ipamConfigJsons);
        }
        networkJson.setIpam(ipamJson);
        NetworkCreateParameters parameters = new NetworkCreateParameters();
        parameters.setNetwork(networkJson);
        NetworkCreateResponseJson networkCreateResponseJson = networksApi.createNetwork(parameters);
        network.setId(networkCreateResponseJson.getId());
        return network;
    }

    @Override
    public List<NetworkSummary> ls() {
        NetworkFilters filters = new NetworkFilters();
        NetworkListParameters parameters = new NetworkListParameters().setFilters(filters);
        List<NetworkJson> networkJsonList = networksApi.listNetworks(parameters);
        List<NetworkSummary> networkSummaryList = new ArrayList<>();
        networkJsonList.forEach(networkJson -> {
            NetworkSummary networkSummary = new NetworkSummary();
            networkSummary.setId(networkJson.getId());
            networkSummary.setName(networkJson.getName());
            networkSummary.setDriver(networkJson.getDriver());
            networkSummary.setScope(networkJson.getScope());
            networkSummaryList.add(networkSummary);
        });
        return networkSummaryList;
    }

    @Override
    public Network inspect(String networkId) {
        NetworkJson networkJson = networksApi.inspectNetwork(networkId);
        Network network = new Network();
        network.setId(networkJson.getId());
        network.setName(networkJson.getName());
        network.setDriver(networkJson.getDriver());
        network.setCreated(fromDateStringToZonedDateTime(networkJson.getCreated()).toInstant().toEpochMilli());
        network.setLabels(networkJson.getLabels());
        network.setOptions(networkJson.getOptions());
        network.setAttachable(networkJson.isAttachable());
        network.setIpv6(networkJson.isEnableIPv6());
        network.setInternal(networkJson.isInternal());
        IPAMJson ipamJson = networkJson.getIpam();
        if (ipamJson != null) {
            network.setIpamDriver(ipamJson.getDriver());
            network.setIpamOptions(ipamJson.getOptions());
            IPAMConfigJson[] ipamConfigJsons = ipamJson.getConfig();
            List<IpamConfig> ipamConfigList = new ArrayList<>();
            if (ipamConfigJsons != null) {
                for (IPAMConfigJson configJson : ipamConfigJsons) {
                    IpamConfig ipamConfig = new IpamConfig();
                    ipamConfig.setSubnet(configJson.getSubnet());
                    ipamConfig.setGateway(configJson.getGateway());
                    ipamConfig.setIpRange(configJson.getIpRange());
                    ipamConfig.setAuxAddress(configJson.getAuxAddress());
                    ipamConfigList.add(ipamConfig);
                }
            }
            network.setIpamConfig(ipamConfigList);
        }
        return network;
    }

    @Override
    public void rm(String networkId) {
        networksApi.deleteNetwork(networkId);
    }

}

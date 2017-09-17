package com.swarmmanager.docker.cli.impl;
import com.swarmmanager.docker.api.common.json.SwarmJson;
import com.swarmmanager.docker.api.common.json.UnlockKeyJson;
import com.swarmmanager.docker.api.common.json.inner.ExternalCAJson;
import com.swarmmanager.docker.api.common.util.DockerDateFormatter;
import com.swarmmanager.docker.api.swarm.SwarmApi;
import com.swarmmanager.docker.api.swarm.parameters.SwarmUpdateParameters;
import com.swarmmanager.docker.cli.SwarmCli;
import com.swarmmanager.docker.cli.model.ExternalCA;
import com.swarmmanager.docker.cli.model.Swarm;
import com.swarmmanager.docker.cli.model.Unlock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class SwarmCliImpl implements SwarmCli {

    @Autowired
    private SwarmApi swarmApi;

    @Override
    public void update(Swarm swarm) {
        SwarmJson swarmJson = swarmApi.inspectSwarm();
        if (swarm.getAutolock() != null) {
            swarmJson.getSpec().getEncryptionConfig().setAutoLockManagers(swarm.getAutolock());
        }
        if (swarm.getCertExpiry() != null) {
            swarmJson.getSpec().getCaConfig().setNodeCertExpiry(swarm.getCertExpiry());
        }
        if (swarm.getDispatcherHeartBeat() != null) {
            swarmJson.getSpec().getDispatcher().setHeartbeatPeriod(swarm.getDispatcherHeartBeat());
        }
        if (swarm.getMaxSnapshots() != null) {
            swarmJson.getSpec().getRaft().setKeepOldSnapshots(swarm.getMaxSnapshots());
        }
        if (swarm.getSnapshotInterval() != null) {
            swarmJson.getSpec().getRaft().setSnapshotInterval(swarm.getSnapshotInterval());
        }
        if (swarm.getTaskHistoryLimit() != null) {
            swarmJson.getSpec().getOrchestration().setTaskHistoryRetentionLimit(swarm.getTaskHistoryLimit());
        }
        if (swarm.getExternalCAs() != null) {
            ExternalCAJson[] externalCAs = new ExternalCAJson[swarm.getExternalCAs().size()];
            for (int i = 0; i < externalCAs.length; i++) {
                ExternalCA externalCA = swarm.getExternalCAs().get(i);
                ExternalCAJson externalCAJson = new ExternalCAJson();
                externalCAJson.setOptions(externalCA.getOptions());
                externalCAJson.setProtocol(externalCA.getProtocol());
                externalCAJson.setUrl(externalCA.getUrl());
                externalCAs[i] = externalCAJson;
            }
            swarmJson.getSpec().getCaConfig().setExternalCAs(externalCAs);
        }
        SwarmUpdateParameters parameters = new SwarmUpdateParameters();
        parameters.setSpec(swarmJson.getSpec());
        swarmApi.updateSwarm(parameters);
    }

    @Override
    public Swarm inspect() {
        SwarmJson swarmJson = swarmApi.inspectSwarm();
        ZonedDateTime createdAt = DockerDateFormatter.fromDateStringToZonedDateTime(swarmJson.getCreatedAt());
        ZonedDateTime updatedAt = DockerDateFormatter.fromDateStringToZonedDateTime(swarmJson.getUpdatedAt());
        Swarm swarm = new Swarm();
        swarm.setCreatedAt(createdAt.toInstant().toEpochMilli());
        swarm.setUpdatedAt(updatedAt.toInstant().toEpochMilli());
        swarm.setAutolock(swarmJson.getSpec().getEncryptionConfig().isAutoLockManagers());
        swarm.setCertExpiry(swarmJson.getSpec().getCaConfig().getNodeCertExpiry());
        swarm.setDispatcherHeartBeat(swarmJson.getSpec().getDispatcher().getHeartbeatPeriod());
        swarm.setWorkerToken(swarmJson.getJoinTokens().getWorker());
        swarm.setManagerToken(swarmJson.getJoinTokens().getManager());
        swarm.setMaxSnapshots(swarmJson.getSpec().getRaft().getKeepOldSnapshots());
        swarm.setSnapshotInterval(swarmJson.getSpec().getRaft().getSnapshotInterval());
        swarm.setTaskHistoryLimit(swarmJson.getSpec().getOrchestration().getTaskHistoryRetentionLimit());
        swarm.setRotateInProgress(swarmJson.isRootRotationInProgress());
        List<ExternalCA> extCAs = new ArrayList<>();
        ExternalCAJson[] externalCAs = swarmJson.getSpec().getCaConfig().getExternalCAs();
        if (externalCAs != null) {
            for (ExternalCAJson externalCA : externalCAs) {
                ExternalCA extCa = new ExternalCA();
                extCa.setUrl(externalCA.getUrl());
                extCa.setOptions(externalCA.getOptions());
                extCAs.add(extCa);
            }
        }
        swarm.setExternalCAs(extCAs);
        return swarm;
    }

    @Override
    public Unlock unlock() {
        Unlock unlock = new Unlock();
        UnlockKeyJson unlockKeyJson = swarmApi.unlock();
        unlock.setUnlockKey(unlockKeyJson.getUnlockKey());
        return unlock;
    }

    @Override
    public void rotate() {
        SwarmJson swarmJson = swarmApi.inspectSwarm();
        SwarmUpdateParameters parameters = new SwarmUpdateParameters();
        parameters.setRotateManagerTokenQueryParam(true);
        parameters.setRotateManagerUnlockKeyQueryParam(true);
        parameters.setRotateWorkerTokenQueryParam(true);
        parameters.setVersionQueryParam(swarmJson.getVersion().getIndex());
        parameters.setSpec(swarmJson.getSpec());
        swarmApi.updateSwarm(parameters);
    }

}

package co.uk.swarmbit.docker.cli.impl;

import co.uk.swarmbit.docker.api.common.json.inner.ExternalCAJson;
import co.uk.swarmbit.docker.api.swarm.parameters.SwarmUpdateParameters;
import co.uk.swarmbit.docker.cli.SwarmCli;
import co.uk.swarmbit.docker.cli.model.ExternalCA;
import co.uk.swarmbit.docker.api.common.json.SwarmJson;
import co.uk.swarmbit.docker.api.common.json.UnlockKeyJson;
import co.uk.swarmbit.docker.api.common.util.DockerDateFormatter;
import co.uk.swarmbit.docker.api.swarm.SwarmApi;
import co.uk.swarmbit.docker.cli.model.Swarm;
import co.uk.swarmbit.docker.cli.model.Unlock;
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
    public void update(String swarmId, Swarm swarm) {
        SwarmJson swarmJson = swarmApi.inspectSwarm(swarmId);
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
        swarmApi.updateSwarm(swarmId, parameters);
    }

    @Override
    public Swarm inspect(String swarmId) {
        SwarmJson swarmJson = swarmApi.inspectSwarm(swarmId);
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
    public Unlock unlock(String swarmId) {
        Unlock unlock = new Unlock();
        UnlockKeyJson unlockKeyJson = swarmApi.unlock(swarmId);
        unlock.setUnlockKey(unlockKeyJson.getUnlockKey());
        return unlock;
    }

    @Override
    public void rotate(String swarmId) {
        SwarmJson swarmJson = swarmApi.inspectSwarm(swarmId);
        SwarmUpdateParameters parameters = new SwarmUpdateParameters();
        parameters.setRotateManagerTokenQueryParam(true);
        parameters.setRotateManagerUnlockKeyQueryParam(true);
        parameters.setRotateWorkerTokenQueryParam(true);
        parameters.setVersionQueryParam(swarmJson.getVersion().getIndex());
        parameters.setSpec(swarmJson.getSpec());
        swarmApi.updateSwarm(swarmId, parameters);
    }

}

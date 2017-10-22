package com.swarmmanager.docker.cli;

import com.swarmmanager.docker.cli.model.*;

import java.util.List;

public interface ServiceCli {

    Service inspectService(String swarmId, String serviceId);

    List<ServiceSummary> serviceLs(String swarmId);

    State servicePs(String swarmId, String serviceId);

    Service serviceCreate(String swarmId, Service service);

    void serviceUpdate(String swarmId, String serviceId, Service service);

    void serviceRm(String swarmId, String serviceId);

    Logs serviceLogs(String swarmId, String serviceId);

}

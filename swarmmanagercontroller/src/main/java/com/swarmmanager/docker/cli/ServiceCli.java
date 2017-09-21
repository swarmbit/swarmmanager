package com.swarmmanager.docker.cli;

import com.swarmmanager.docker.cli.model.*;

import java.util.List;

public interface ServiceCli {

    Service inspectService(String serviceId);

    List<ServiceSummary> serviceLs();

    State servicePs(String serviceId);

    Service serviceCreate(Service service);

    void serviceUpdate(String serviceId, Service service);

    void serviceRm(String serviceId);

    Logs serviceLogs(String serviceId);

}

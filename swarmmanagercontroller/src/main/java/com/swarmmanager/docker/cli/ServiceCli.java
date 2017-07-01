package com.swarmmanager.docker.cli;

import com.swarmmanager.docker.cli.model.LogLine;
import com.swarmmanager.docker.cli.model.Service;
import com.swarmmanager.docker.cli.model.ServiceState;
import com.swarmmanager.docker.cli.model.ServiceSummary;

import java.util.List;

public interface ServiceCli {

    Service inspectService(String serviceId);

    List<ServiceSummary> serviceLs();

    ServiceState servicePs(String serviceId);

    Service serviceCreate(Service service);

    void serviceUpdate(String serviceId, Service service);

    void serviceRm(String serviceId);

    List<LogLine> serviceLogs(String serviceId);

}

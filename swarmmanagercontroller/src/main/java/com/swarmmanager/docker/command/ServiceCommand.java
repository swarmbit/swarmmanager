package com.swarmmanager.docker.command;

import com.swarmmanager.docker.command.model.LogLine;
import com.swarmmanager.docker.command.model.Service;
import com.swarmmanager.docker.command.model.ServiceState;
import com.swarmmanager.docker.command.model.ServiceSummary;

import java.util.List;

public interface ServiceCommand {

    Service inspectService(String serviceId);

    List<ServiceSummary> serviceLs();

    ServiceState servicePs(String serviceId);

    Service serviceCreate(Service service);

    void serviceUpdate(String serviceId, Service service);

    void serviceRm(String serviceId);

    List<LogLine> serviceLogs(String serviceId);

}

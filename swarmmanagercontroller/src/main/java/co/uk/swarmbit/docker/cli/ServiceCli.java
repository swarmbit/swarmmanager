package co.uk.swarmbit.docker.cli;

import co.uk.swarmbit.docker.cli.model.Logs;
import co.uk.swarmbit.docker.cli.model.Service;
import co.uk.swarmbit.docker.cli.model.ServiceSummary;
import co.uk.swarmbit.docker.cli.model.State;

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

package com.swarmmanager.docker.cli.model;

import java.util.List;
import java.util.Set;

public class Logs {

    Set<LogFilter> logFilters;

    List<LogLine> logLines;

    public Set<LogFilter> getLogFilters() {
        return logFilters;
    }

    public void setLogFilters(Set<LogFilter> logFilters) {
        this.logFilters = logFilters;
    }

    public List<LogLine> getLogLines() {
        return logLines;
    }

    public void setLogLines(List<LogLine> logLines) {
        this.logLines = logLines;
    }
}

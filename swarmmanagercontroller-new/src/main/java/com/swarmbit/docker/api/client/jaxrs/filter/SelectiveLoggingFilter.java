package com.swarmbit.docker.api.client.jaxrs.filter;

import org.slf4j.Logger;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SelectiveLoggingFilter extends LoggingFilter {

    // Immutable'ish
    private static final Set<String> SKIPPED_CONTENT;
    static {
        Set<String> s = new HashSet<String>();
        s.add(MediaType.APPLICATION_OCTET_STREAM);
        s.add("application/tar");
        SKIPPED_CONTENT = Collections.unmodifiableSet(s);
    }

    public SelectiveLoggingFilter(Logger logger, Boolean b) {
        super(logger, b);
    }

    @Override
    public void filter(ClientRequestContext context) throws IOException {
        // Unless the content type is in the list of those we want to ellide, then just have
        // our super-class handle things.
        Object contentType = context.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);
        if (contentType == null || !SKIPPED_CONTENT.contains(contentType.toString())) {
            super.filter(context);
        }
    }

}

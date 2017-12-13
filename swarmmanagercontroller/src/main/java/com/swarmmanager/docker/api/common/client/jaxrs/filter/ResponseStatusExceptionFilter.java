package com.swarmmanager.docker.api.common.client.jaxrs.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swarmmanager.docker.api.common.client.jaxrs.exception.DockerRemoteApiException;
import org.apache.commons.io.IOUtils;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.MediaType;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class ResponseStatusExceptionFilter implements ClientResponseFilter {

    @Override
    public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
        int status = responseContext.getStatus();
        // status code used by the frontend to identify docker api errors
        int dockerErrStatus = 100001;
        if (status >= 300) {
            if (status == 503) {
                throw new DockerRemoteApiException("Configured node is not part of a swarm.", dockerErrStatus);
            }
            throw new DockerRemoteApiException(getBodyAsMessage(responseContext), dockerErrStatus);
        }
    }

    private String getBodyAsMessage(ClientResponseContext responseContext) throws IOException {
        if (responseContext.hasEntity()) {
            int contentLength = responseContext.getLength();
            if (contentLength != -1) {
                byte[] buffer = new byte[contentLength];
                try {
                    InputStream entityStream = responseContext.getEntityStream();
                    IOUtils.readFully(entityStream, buffer);
                    entityStream.close();
                } catch (EOFException e) {
                    return null;
                }
                Charset charset = null;
                MediaType mediaType = responseContext.getMediaType();
                if (mediaType != null) {
                    String charsetName = mediaType.getParameters().get("charset");
                    if (charsetName != null) {
                        try {
                            charset = Charset.forName(charsetName);
                        } catch (Exception e) {
                            // Do noting...
                        }
                    }
                }
                if (charset == null) {
                    charset = Charset.defaultCharset();
                }
                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = mapper.readTree(new String(buffer, charset));
                String message = node.get("message").asText();
                if (message != null) {
                    String[] parts = message.split(" ");
                    if (parts.length > 0 && parts[0] != null && parts[0].length() > 0) {
                        parts[0] = parts[0].substring(0, 1).toUpperCase() + parts[0].substring(1);
                        message = String.join(" ", parts);
                    }
                }
                return message;
            }
        }
        return null;
    }
}

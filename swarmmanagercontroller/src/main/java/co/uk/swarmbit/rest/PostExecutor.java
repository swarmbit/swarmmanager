package co.uk.swarmbit.rest;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class PostExecutor implements RestExecutor {

    @Override
    public <E> E execute(RestParameters parameters, RestResponseType<E> restResponseType) {
        WebTarget webTarget = parameters.getBaseRequest();
        if (webTarget != null) {
            String path = parameters.getPath();
            if (path != null) {
                webTarget = webTarget.path(parameters.getPath());
            }
            for (QueryParam queryParam : parameters.getQueryParams()) {
                webTarget = webTarget.queryParam(queryParam.getName(), queryParam.getValue());
            }
            Invocation.Builder requestBuilder = webTarget.request();
            for (HeaderParam headerParam : parameters.getHeaderParams()) {
                requestBuilder.header(headerParam.getName(), headerParam.getValue());
            }
            Response response = requestBuilder
                    .accept(MediaType.APPLICATION_JSON)
                    .buildPost(Entity.entity(parameters.getRequestBody(), MediaType.APPLICATION_JSON))
                    .invoke();
            E entity = null;
            if(response.hasEntity()) {
                entity = response.readEntity(restResponseType);
            }
            return entity;
        }
        return null;
    }
}

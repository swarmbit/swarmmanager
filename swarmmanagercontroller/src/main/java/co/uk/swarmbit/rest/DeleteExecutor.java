package co.uk.swarmbit.rest;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

public class DeleteExecutor implements RestExecutor {

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
            requestBuilder.buildDelete().invoke();
        }
        return null;
    }
}

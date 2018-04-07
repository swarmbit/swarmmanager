package co.uk.swarmbit.rest;

public class RestExecutorFactory {

    public static RestExecutor createRestExecutor(RestMethod method) {
        switch (method) {
            case POST:
                return new PostExecutor();
            case DELETE:
                return new DeleteExecutor();
            default:
                return new GetExecutor();
        }
    }
}

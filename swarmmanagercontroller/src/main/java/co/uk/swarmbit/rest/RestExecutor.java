package co.uk.swarmbit.rest;

public interface RestExecutor {

   <E> E execute(RestParameters parameters, RestResponseType<E> restResponseType);

}

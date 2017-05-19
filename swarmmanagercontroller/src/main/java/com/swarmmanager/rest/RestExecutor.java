package com.swarmmanager.rest;

public interface RestExecutor {

   <E> E execute(RestParameters parameters, RestResponseType<E> restResponseType);

}

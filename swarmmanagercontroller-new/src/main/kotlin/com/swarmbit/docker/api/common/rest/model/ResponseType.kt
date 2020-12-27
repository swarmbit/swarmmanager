package com.swarmbit.docker.api.common.rest.model

import javax.ws.rs.core.GenericType

abstract class ResponseType<E> : GenericType<E>()
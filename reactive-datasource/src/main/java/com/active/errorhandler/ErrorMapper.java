package com.active.errorhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ErrorMapper implements ExceptionMapper<Exception> {

    @Inject
    ObjectMapper objectMapper;

    @Override
    public Response toResponse(Exception e) {
        int code = 500;
        if (e instanceof WebApplicationException) {
            code = ((WebApplicationException) e).getResponse().getStatus();
        }
        ObjectNode exceptionJSON = objectMapper.createObjectNode();
        exceptionJSON.put("exceptionType", e.getClass().getName());
        exceptionJSON.put("code", code);

        if (e.getMessage() != null) {
            exceptionJSON.put("error", e.getMessage());
        }
        return Response.status(code).entity(exceptionJSON).build();


    }
}

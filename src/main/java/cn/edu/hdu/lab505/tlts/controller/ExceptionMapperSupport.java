package cn.edu.hdu.lab505.tlts.controller;

import cn.edu.hdu.lab505.tlts.common.AppException;
import org.apache.log4j.Logger;
import org.springframework.dao.DuplicateKeyException;

import javax.security.auth.login.LoginException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionMapperSupport implements ExceptionMapper<Exception> {

    private static final Logger LOGGER = Logger.getLogger(ExceptionMapperSupport.class);
    ;

    public Response toResponse(Exception exception) {
        String message = "Server Error";
        Status statusCode = Status.INTERNAL_SERVER_ERROR;
        if (exception instanceof AppException) {
            statusCode = Status.BAD_REQUEST;
            message = exception.getMessage();
        } else if (exception instanceof DuplicateKeyException) {
            statusCode = Status.CONFLICT;
            message = exception.getMessage();
        } else if (exception instanceof NotFoundException) {
            statusCode = Status.NOT_FOUND;
            message = exception.getMessage();
        } else if (exception instanceof WebApplicationException) {
            WebApplicationException we = (WebApplicationException) exception;
            LOGGER.error(we.getMessage(), exception);
            return we.getResponse();
        }
        LOGGER.error(message, exception);
        return Response.ok(message, MediaType.TEXT_PLAIN).status(statusCode).build();
    }
}

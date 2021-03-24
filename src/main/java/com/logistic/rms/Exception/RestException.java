package com.logistic.rms.Exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@Slf4j
@ControllerAdvice
public class RestException
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { IDNotFoundExcpetion.class })
    protected ResponseEntity<Error> handleIDNotFound(Exception ex, WebRequest request) {
        log.error("Failure in operation due to {}",ex.getLocalizedMessage());
        Error error = getError(ex,request,HttpStatus.NOT_FOUND);
        return new ResponseEntity<Error>(error,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    protected ResponseEntity<Error> badParameter(Exception ex, WebRequest request) {
        log.error("Failure in operation due to {}",ex.getLocalizedMessage());
        Error error = getError(ex,request,HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Error>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Error> error(Exception ex, WebRequest request) {
        log.error("Failure in operation due to {}",ex.getLocalizedMessage());
        Error error = getError(ex,request,HttpStatus.BAD_REQUEST);
        error.setMessage("Internal server error. Please contact admin");
        return new ResponseEntity<Error>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    protected ResponseEntity<Error> accessDeniedException(Exception ex, WebRequest request) {
        log.error("Failure in operation due to {}",ex.getLocalizedMessage());
        Error error = getError(ex,request,HttpStatus.UNAUTHORIZED);
        error.setMessage("You don't have enough privilege to access this operation");
        return new ResponseEntity<Error>(error,HttpStatus.UNAUTHORIZED);
    }


    private Error getError(Exception ex,WebRequest request,HttpStatus status){
        Error error = new Error();
        error.setMessage(ex.getLocalizedMessage());
        error.setPath(request.getContextPath());
        error.setStatus(status);
        error.setTimestamp(new Date());
        return error;
    }
}
package com.scheduler.demo.api.exceptions;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class RestControllerErrorAdvice {
    private static final String UNEXPECTED_ERROR_MESSAGE = "Unexpected error. Please, contact a developer";

    @ExceptionHandler({DtoValidationException.class})
    public void catchBadRequest(Exception e, HttpServletResponse response) throws IOException {
        log.info("Bad Request: ", e);
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler({UserNotFoundException.class})
    public void catchNotFound(Exception e, HttpServletResponse response) throws IOException {
        log.info("Not found: ", e);
        response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler({IOException.class, Exception.class})
    public void catchOtherUnprocessedExceptions(Exception e, HttpServletResponse response) throws IOException {
        log.error("Unexpected application error: ", e);
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), UNEXPECTED_ERROR_MESSAGE);
    }
}

package com.umbrella.insurance.endpoints;

import com.umbrella.insurance.core.models.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

@ControllerAdvice
public class ExceptionController {
    @ResponseBody
    @ExceptionHandler({NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    DefaultExceptionResponse notFoundExceptionHandler(HttpServletRequest request,
                                                      Exception ex) {
        DefaultExceptionResponse defaultExceptionResponse = new DefaultExceptionResponse();
        defaultExceptionResponse.setError(HttpStatus.NOT_FOUND.getReasonPhrase());
        defaultExceptionResponse.setStatus(HttpStatus.NOT_FOUND.value());
        defaultExceptionResponse.setMessage(ex.getMessage());
        defaultExceptionResponse.setTimestamp(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("Z"))));
        defaultExceptionResponse.setPath(request.getRequestURI());
        defaultExceptionResponse.setMethod(request.getMethod().toString());
        request.setAttribute("responseBody", defaultExceptionResponse);
        return defaultExceptionResponse;
    }

    @ResponseBody
    @ExceptionHandler({NotImplementedException.class})
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    DefaultExceptionResponse notImplementedExceptionHandler(HttpServletRequest request,
                                                      Exception ex) {
        DefaultExceptionResponse defaultExceptionResponse = new DefaultExceptionResponse();
        defaultExceptionResponse.setError(HttpStatus.NOT_IMPLEMENTED.getReasonPhrase());
        defaultExceptionResponse.setStatus(HttpStatus.NOT_IMPLEMENTED.value());
        defaultExceptionResponse.setMessage(ex.getMessage());
        defaultExceptionResponse.setTimestamp(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("Z"))));
        defaultExceptionResponse.setPath(request.getRequestURI());
        defaultExceptionResponse.setMethod(request.getMethod().toString());
        request.setAttribute("responseBody", defaultExceptionResponse);
        return defaultExceptionResponse;
    }

    @ResponseBody
    @ExceptionHandler({ConflictException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    DefaultExceptionResponse conflictExceptionHandler(HttpServletRequest request,
                                                      Exception ex) {
        DefaultExceptionResponse defaultExceptionResponse = new DefaultExceptionResponse();
        defaultExceptionResponse.setError(HttpStatus.CONFLICT.getReasonPhrase());
        defaultExceptionResponse.setStatus(HttpStatus.CONFLICT.value());
        defaultExceptionResponse.setMessage(ex.getMessage());
        defaultExceptionResponse.setTimestamp(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("Z"))));
        defaultExceptionResponse.setPath(request.getRequestURI());
        defaultExceptionResponse.setMethod(request.getMethod().toString());
        request.setAttribute("responseBody", defaultExceptionResponse);
        return defaultExceptionResponse;
    }

    @ResponseBody
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    DefaultExceptionResponse exceptionHandler(HttpServletRequest request, Exception ex) {
        DefaultExceptionResponse defaultExceptionResponse = new DefaultExceptionResponse();
        defaultExceptionResponse.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        defaultExceptionResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        defaultExceptionResponse.setMessage(ex.getMessage());
        defaultExceptionResponse.setTimestamp(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("Z"))));
        defaultExceptionResponse.setPath(request.getRequestURI());
        defaultExceptionResponse.setMethod(request.getMethod().toString());
        request.setAttribute("responseBody", defaultExceptionResponse);
        return defaultExceptionResponse;
    }

    @ResponseBody
    @ExceptionHandler({UnauthorizedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    DefaultExceptionResponse forbiddenExceptionHandler(HttpServletRequest request, Exception ex) {
        DefaultExceptionResponse defaultExceptionResponse = new DefaultExceptionResponse();
        defaultExceptionResponse.setError(HttpStatus.FORBIDDEN.getReasonPhrase());
        defaultExceptionResponse.setStatus(HttpStatus.FORBIDDEN.value());
        defaultExceptionResponse.setMessage(ex.getMessage());
        defaultExceptionResponse.setTimestamp(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("Z"))));
        defaultExceptionResponse.setPath(request.getRequestURI());
        defaultExceptionResponse.setMethod(request.getMethod().toString());
        request.setAttribute("responseBody", defaultExceptionResponse);
        return defaultExceptionResponse;
    }
}

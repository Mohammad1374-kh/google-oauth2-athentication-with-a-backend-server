package com.khosravi.googleoauth2athentication.exceptions;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;
    public static final String TRACE = "trace";

    @Value("${reflectoring.trace:false}")
    private boolean printStackTrace;

    public ControllerAdvisor(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(FORBIDDEN)
    protected ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex,
                                                                 WebRequest request,
                                                                 Locale locale) {
        return buildErrorResponse(ex, FORBIDDEN, request, locale);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(BAD_REQUEST)
    protected ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException ex,
                                                                      WebRequest request,
                                                                      Locale locale) {
        return buildErrorResponse(ex, BAD_REQUEST, request, locale);
    }

    @ExceptionHandler(InvalidGoogleIdTokenException.class)
    @ResponseStatus(BAD_REQUEST)
    protected ResponseEntity<Object> handleInvalidGoogleIdTokenException(InvalidGoogleIdTokenException ex,
                                                                         WebRequest request,
                                                                         Locale locale) {
        return buildErrorResponse(ex, BAD_REQUEST, request, locale);
    }

    @ExceptionHandler(InvalidGoogleBearerTokenException.class)
    @ResponseStatus(BAD_REQUEST)
    protected ResponseEntity<Object> handleInvalidGoogleAuthorizationBearerFormatException(InvalidGoogleBearerTokenException ex,
                                                                                           WebRequest request,
                                                                                           Locale locale) {
        return buildErrorResponse(ex, BAD_REQUEST, request, locale);
    }

    @ExceptionHandler(GoogleIdTokenVerifierException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    protected ResponseEntity<Object> handleGoogleIdTokenVerifierException(GoogleIdTokenVerifierException ex,
                                                                          WebRequest request,
                                                                          Locale locale) {
        return buildErrorResponse(ex, INTERNAL_SERVER_ERROR, request, locale);
    }


    private ResponseEntity<Object> buildErrorResponse(Exception exception,
                                                      HttpStatus httpStatus,
                                                      WebRequest request,
                                                      Locale locale) {
        String message = messageSource.getMessage(exception.getMessage(), null, locale);
        return buildErrorResponse(exception, message, httpStatus, request);
    }

    private ResponseEntity<Object> buildErrorResponse(Exception exception,
                                                      String message,
                                                      HttpStatus httpStatus,
                                                      WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), message);
        if (printStackTrace && isTraceOn(request)) {
            errorResponse.setStackTrace(ExceptionUtils.getStackTrace(exception));
        }
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    private boolean isTraceOn(WebRequest request) {
        String[] value = request.getParameterValues(TRACE);
        return Objects.nonNull(value)
                && value.length > 0
                && value[0].contentEquals("true");
    }

/*    @Override
    public ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        ex.printStackTrace();
        return buildErrorResponse(ex, status, request, Locale.ENGLISH);
    }*/

}

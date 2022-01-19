package com.relay.iot.relayiotconsumer.config.exception;


import com.relay.iot.relayiotconsumer.data.ApiCode;
import com.relay.iot.relayiotconsumer.exception.DataNotFoundException;
import com.relay.iot.relayiotconsumer.exception.InvalidInputException;
import com.relay.iot.relayiotconsumer.exception.UserException;
import com.relay.iot.relayiotconsumer.model.exception.ErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Kunal
 */
@ControllerAdvice
@Slf4j
public class ExceptionProcessor {


    public static final String INTERNAL_ERROR_MESSAGE = "A non-recoverable error occurred; please contact Kunal at kunalmalhotra9322@gmail.com with message.";


    @ExceptionHandler(InvalidInputException.class)
    @ResponseBody
    public ResponseEntity<ErrorType> handleEnvironmentError(HttpServletRequest request, HttpServletResponse response, InvalidInputException ex)
            throws IOException {

        long timestamp = System.currentTimeMillis();

        switch (ex.getCode()) {
            case INVALID_INPUT:
                return getResponseEntity(request, HttpStatus.BAD_REQUEST, ApiCode.INVALID_PARAMETER.getValue(), ex.getMessage(), timestamp);
            default:
                // Should never happened, all environment exceptions are listed in the switch
                return getResponseEntity(request, HttpStatus.INTERNAL_SERVER_ERROR, ApiCode.INTERNAL_ERROR.getValue(), INTERNAL_ERROR_MESSAGE, timestamp);
        }
    }


    @ExceptionHandler(UserException.class)
    @ResponseBody
    public ResponseEntity<ErrorType> handleUserError(HttpServletRequest request, HttpServletResponse response, UserException ex)
            throws IOException {

        long timestamp = System.currentTimeMillis();

        // log.error
        switch (ex.getCode()) {
            case USER_NOT_AUTHORIZED:

                String userMessage;
                String notAuthorizedErrorCode;
                if (ex.getCause() instanceof InvalidInputException) {
                    InvalidInputException invalidInputException = (InvalidInputException) ex.getCause();
                    notAuthorizedErrorCode = invalidInputException.getCode().getValue();
                    userMessage = invalidInputException.getUserMessage();
                } else {
                    notAuthorizedErrorCode = ApiCode.UNAUTHORIZED.getValue();
                    userMessage = ex.getUserMessage();
                }

                return getResponseEntity(request, HttpStatus.UNAUTHORIZED, notAuthorizedErrorCode, userMessage, timestamp);
            default:
                // Should never happen, all user exceptions are listed in the switch
                return getResponseEntity(request, HttpStatus.BAD_REQUEST, ApiCode.INVALID_PARAMETER.getValue(), ex.getUserMessage(), timestamp);
        }
    }

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ErrorType> handleEnvironmentError(HttpServletRequest request, HttpServletResponse response, DataNotFoundException ex)
            throws IOException {

        long timestamp = System.currentTimeMillis();

        //log error

        switch (ex.getCode()) {
            case EMPTY_DATA:
                return getResponseEntity(request, HttpStatus.NOT_FOUND, ApiCode.DATA_NOT_FOUND_IN_DB.getValue(), ex.getMessage(), timestamp);
            default:
                // Should never happened, all environment exceptions are listed in the switch
                return getResponseEntity(request, HttpStatus.INTERNAL_SERVER_ERROR, ApiCode.INTERNAL_ERROR.getValue(), INTERNAL_ERROR_MESSAGE, timestamp);
        }
    }


    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity<ErrorType> handleUnexpectedError(HttpServletRequest request, Throwable ex) throws IOException {
        long timestamp = System.currentTimeMillis();
        //log error
        return getResponseEntity(request, HttpStatus.INTERNAL_SERVER_ERROR, ApiCode.INTERNAL_ERROR.getValue(), INTERNAL_ERROR_MESSAGE, timestamp);
    }

    /**
     * Build error output.
     */
    private ResponseEntity<ErrorType> getResponseEntity(HttpServletRequest request, HttpStatus httpStatus, String code, String message, long timestamp) throws IOException {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
        boolean returnJson = false;
        final String acceptHeader = request.getHeader("Accept");
        if ((acceptHeader == null) || acceptHeader.equals("*/*") || (acceptHeader.contains("json"))) {
            returnJson = true;
        }

        if (returnJson) {
            ErrorType errorType = new ErrorType(timestamp, httpStatus.value(), code, message, request.getRequestURI());
            return new ResponseEntity<>(errorType, responseHeaders, httpStatus);
        } else {
            return new ResponseEntity<>((ErrorType) null, httpStatus);
        }

    }
}

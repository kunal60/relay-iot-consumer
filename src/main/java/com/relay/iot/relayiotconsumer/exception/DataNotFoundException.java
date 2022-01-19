package com.relay.iot.relayiotconsumer.exception;

import lombok.Getter;

/**
 * @author Kunal Malhotra
 */

public class DataNotFoundException extends RuntimeException {

    @Getter
    private DataNotFoundException.Code code;

    public enum Code {
        EMPTY_DATA("data is Empty");


        String value;

        private Code() {

        }

        private Code(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public DataNotFoundException(DataNotFoundException.Code code, String message) {
        super(message);
        this.code = code;
    }

    public DataNotFoundException(DataNotFoundException.Code code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getUserMessage() {
        switch (code) {
            case EMPTY_DATA:
                return "The signature of the access token is invalid or the signer is unknown, please refer to documentation.";
            default:
                return "The access token is invalid, please refer to documentation.";
        }
    }
}
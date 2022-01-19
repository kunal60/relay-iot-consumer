package com.relay.iot.relayiotconsumer.exception;

import lombok.Getter;

public class InvalidInputException extends RuntimeException {

    @Getter
    private Code code;

    public enum Code {
        INVALID_INPUT("invalid_input");

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

    public InvalidInputException(Code code, String message) {
        super(message);
        this.code = code;
    }


    public String getUserMessage() {
        switch (code) {
            case INVALID_INPUT:
                return "User Input is Invalid.";
            default:
                return "";
        }
    }
}

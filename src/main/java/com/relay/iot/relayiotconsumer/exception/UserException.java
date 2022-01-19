package com.relay.iot.relayiotconsumer.exception;

import lombok.Getter;

public class UserException extends RuntimeException {

    private static final long serialVersionUID = -3612569004965744411L;

    @Getter
    private Code code;

    public enum Code {

        USER_NOT_AUTHORIZED;


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

    /**
     * Message sent to user with HTTP response
     */
    @Getter
    private String userMessage;

    /**
     * Message with context for internal usage (logs)
     */
    @Getter
    private String internalMessage;



    public UserException(Code code, String userMessage) {
        super(userMessage);
        this.code = code;
        this.userMessage = userMessage;
    }




}

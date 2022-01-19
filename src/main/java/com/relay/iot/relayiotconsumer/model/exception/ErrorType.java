package com.relay.iot.relayiotconsumer.model.exception;

import lombok.Value;

@Value
public class ErrorType {

    protected final long timestamp;
    protected final int status;
    protected final String error;
    protected final String message;
    protected final String path;


    public ErrorType(long timestamp, int status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}

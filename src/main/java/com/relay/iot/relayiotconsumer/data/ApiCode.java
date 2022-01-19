package com.relay.iot.relayiotconsumer.data;

public enum ApiCode {
    INVALID_PARAMETER("invalid_parameter"),
    UNAUTHORIZED("unauthorized"),
    INTERNAL_ERROR("internal_error"),
    DATA_NOT_FOUND_IN_DB("data_not_found_in_db");

    private String value;

    ApiCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
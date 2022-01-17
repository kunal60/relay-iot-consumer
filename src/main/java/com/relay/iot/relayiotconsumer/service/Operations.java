package com.relay.iot.relayiotconsumer.service;

public enum Operations {

    AVERAGE, MEDIAN, MAX, MIN;

    public static boolean contains(String operation) {

        for (Operations c : Operations.values()) {
            if (c.name().equals(operation)) {
                return true;
            }
        }

        return false;
    }
}

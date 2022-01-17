package com.relay.iot.relayiotconsumer.util;

import com.relay.iot.relayiotconsumer.service.Operations;

import java.time.OffsetDateTime;
import java.util.Optional;

public class RequestValidator {

    public static boolean isValidRequest(String operation,
                                         Optional<String> from,
                                         Optional<String> to) {
        if (!from.isPresent() || !to.isPresent() || !Operations.contains(operation.toUpperCase())) {
            return false;
        }
        OffsetDateTime startDateTime = OffsetDateTime.parse(from.get());
        OffsetDateTime endDateTime = OffsetDateTime.parse(to.get());
        return endDateTime.compareTo(startDateTime) < 0 ? false : true;

    }


}

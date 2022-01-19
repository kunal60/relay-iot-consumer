package com.relay.iot.relayiotconsumer.service;

import com.relay.iot.relayiotconsumer.exception.InvalidInputException;
import com.relay.iot.relayiotconsumer.repository.IotRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;

@Component
public class IotOperationService {

    @Autowired
    IotRepositoryCustom iotDataRepositoryCustomImpl;

    public BigDecimal execute(String operationType, Map<String, String> mapParameters) {

        OffsetDateTime fromDate = OffsetDateTime.parse(mapParameters.get("from"));
        OffsetDateTime toDate = OffsetDateTime.parse(mapParameters.get("to"));
        String clusterId = mapParameters.get("clusterId");
        String eventType = mapParameters.get("eventType");

        switch (operationType.toUpperCase()) {
            case "AVERAGE":
                return iotDataRepositoryCustomImpl.findAvgValueByClusterIdAndTypeAndTimestampBetween(clusterId, eventType, fromDate, toDate);
            case "MAX":
                return iotDataRepositoryCustomImpl.findMaxValueByClusterIdAndTypeAndTimestampBetween(clusterId, eventType, fromDate, toDate);
            case "MIN":
                return iotDataRepositoryCustomImpl.findMinValueByClusterIdAndTypeAndTimestampBetween(clusterId, eventType, fromDate, toDate);
            case "MEDIAN":
                return iotDataRepositoryCustomImpl.findMedianValueByClusterIdAndTypeAndTimestampBetween(clusterId, eventType, fromDate, toDate);
            default:
                throw new InvalidInputException(InvalidInputException.Code.INVALID_INPUT, "get request is invalid");
        }

    }
}

package com.relay.iot.relayiotconsumer.repository;

import com.relay.iot.relayiotconsumer.exception.DataNotFoundException;

import java.math.BigDecimal;
import java.time.OffsetDateTime;


public interface IotRepositoryCustom {
    BigDecimal findMinValueByClusterIdAndTypeAndTimestampBetween(String clusterId, String eventType, OffsetDateTime start, OffsetDateTime end) throws DataNotFoundException;

    BigDecimal findMaxValueByClusterIdAndTypeAndTimestampBetween(String clusterId, String eventType, OffsetDateTime start, OffsetDateTime end) throws DataNotFoundException;

    BigDecimal findAvgValueByClusterIdAndTypeAndTimestampBetween(String clusterId, String eventType, OffsetDateTime start, OffsetDateTime end) throws DataNotFoundException;

    BigDecimal findMedianValueByClusterIdAndTypeAndTimestampBetween(String clusterId, String eventType, OffsetDateTime start, OffsetDateTime end) throws DataNotFoundException;

}

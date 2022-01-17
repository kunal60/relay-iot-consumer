package com.relay.iot.relayiotconsumer.mapper;

import com.relay.iot.relayiotconsumer.entity.IotEntity;
import com.relay.iot.relayiotconsumer.model.IotData;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

/**
 * @author Kunal Malhotra
 */
@Component
public class IotDataMapper {

    /**
     * This method converts iot data to mongo DB supported data
     * @param sensorData
     * @return
     */
    public IotEntity eventToEntityConvertor(IotData sensorData) {
        return IotEntity.builder()
                .name(sensorData.getName())
                .clusterId(sensorData.getClusterId())
                .value(sensorData.getValue())
                .timestamp(OffsetDateTime.parse(sensorData.getTimestamp()))
                .type(sensorData.getType())
                .id(sensorData.getId())
                .initialized(sensorData.isInitialized())
                .build();
    }
}

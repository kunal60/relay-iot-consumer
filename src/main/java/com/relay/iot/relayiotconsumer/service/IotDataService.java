package com.relay.iot.relayiotconsumer.service;


import com.relay.iot.relayiotconsumer.entity.IotEntity;
import com.relay.iot.relayiotconsumer.mapper.IotDataMapper;
import com.relay.iot.relayiotconsumer.model.IotData;
import com.relay.iot.relayiotconsumer.repository.IotDataRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class IotDataService {

    private IotDataMapper iotDataMapper;

    private IotDataRepository iotDataRepository;


    public void save(IotData sensorData) {
        IotEntity sensorDataEntity = iotDataMapper.eventToEntityConvertor(sensorData);
        iotDataRepository.save(sensorDataEntity);
        log.info("Iot data saved successfully");
    }
}

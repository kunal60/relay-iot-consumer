package com.relay.iot.relayiotconsumer.consumer;

import com.google.gson.Gson;
import com.relay.iot.relayiotconsumer.mapper.IotDataMapper;
import com.relay.iot.relayiotconsumer.model.IotData;
import com.relay.iot.relayiotconsumer.service.IotDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @Autowired
    IotDataMapper iotDataMapper;

    @Autowired
    IotDataService iotDataService;

    @KafkaListener(topics = "${spring.kafka.topics}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeFromTopic(String message) {
        Gson g = new Gson();
        IotData p = g.fromJson(message, IotData.class);
        System.out.println("inside consumeFromTopic");
        System.out.println("Message from topic" + p);
        iotDataService.save(p);
    }


}
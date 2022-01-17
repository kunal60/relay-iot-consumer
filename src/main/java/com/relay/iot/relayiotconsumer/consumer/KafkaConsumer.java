package com.relay.iot.relayiotconsumer.consumer;

import com.google.gson.Gson;
import com.relay.iot.relayiotconsumer.mapper.IotDataMapper;
import com.relay.iot.relayiotconsumer.model.IotData;
import com.relay.iot.relayiotconsumer.service.IotDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * This class is reponsible for reading messages from kafka topics
 *
 * @author Kunal Malhotra
 */
@Component
public class KafkaConsumer {

    @Autowired
    IotDataMapper iotDataMapper;

    @Autowired
    IotDataService iotDataService;

    private Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    /**
     * Reads message from the kafka topic
     *
     * @param message
     */
    @KafkaListener(topics = "${spring.kafka.topics}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeFromTopic(String message) {
        Gson g = new Gson();
        IotData p = g.fromJson(message, IotData.class);
        iotDataService.save(p);
        logger.info("Message saved to Mongo successfully");
    }


}
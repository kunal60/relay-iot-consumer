package com.relay.iot.relayiotconsumer.repository.impl;

import com.relay.iot.relayiotconsumer.data.Constants;
import com.relay.iot.relayiotconsumer.entity.IotEntity;
import com.relay.iot.relayiotconsumer.repository.IotDataRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IotDataRepositoryCustomImplTest {


    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private IotDataRepository iotDataRepository;

    @Autowired
    private IotDataRepositoryCustomImpl iotDataRepositoryCustom;

    @Before
    public void initDataBase() {
        initIotData();
    }


    @After
    public void deleteDataBase() {
        iotDataRepository.deleteAll();
    }


    @Test
    public void checkMax() {
        BigDecimal result = iotDataRepositoryCustom.findMaxValueByClusterIdAndTypeAndTimestampBetween(String.valueOf(1),
                "TEMPERATURE", OffsetDateTime.parse("2021-12-23T18:18:55.479998Z"),
                OffsetDateTime.parse("2022-01-23T18:18:59.479998Z"));
        assertEquals(result.toString(), "4");

    }


    @Test
    public void checkAvg() {
        BigDecimal result = iotDataRepositoryCustom.findAvgValueByClusterIdAndTypeAndTimestampBetween(String.valueOf(1),
                "TEMPERATURE", OffsetDateTime.parse("2021-12-23T18:18:55.479998Z"),
                OffsetDateTime.parse("2022-01-23T18:18:59.479998Z"));
        assertEquals(result.toString(), "2.5000");

    }

    @Test
    public void checkMedian() {
        BigDecimal result = iotDataRepositoryCustom.findMedianValueByClusterIdAndTypeAndTimestampBetween(String.valueOf(1),
                "TEMPERATURE", OffsetDateTime.parse("2021-12-23T18:18:55.479998Z"),
                OffsetDateTime.parse("2022-01-23T18:18:59.479998Z"));
        assertEquals(result.toString(), "2.5000");

    }


    private List<IotEntity> initIotData() {
        List<IotEntity> entities = new ArrayList<>();
        IotEntity entity1 = IotEntity.builder().id(1l).clusterId(1l).type("TEMPERATURE")
                .name("Living Room Temp")
                .initialized(false).value(new BigDecimal("1"))
                .timestamp(OffsetDateTime.parse("2022-01-23T18:18:55.479998Z")).build();

        IotEntity entity2 = IotEntity.builder().id(1l).clusterId(1l).type("TEMPERATURE")
                .name("Living Room Temp")
                .initialized(false).value(new BigDecimal("2"))
                .timestamp(OffsetDateTime.parse("2022-01-23T18:18:57.479998Z")).build();

        IotEntity entity3 = IotEntity.builder().id(1l).clusterId(1l).type("TEMPERATURE")
                .name("Living Room Temp")
                .initialized(false).value(new BigDecimal("3"))
                .timestamp(OffsetDateTime.parse("2022-01-23T18:18:58.479998Z")).build();

        IotEntity entity4 = IotEntity.builder().id(1l).clusterId(1l).type("TEMPERATURE")
                .name("Living Room Temp")
                .initialized(false).value(new BigDecimal("4"))
                .timestamp(OffsetDateTime.parse("2022-01-23T18:18:59.479998Z")).build();
        entities.add(entity1);
        entities.add(entity2);
        entities.add(entity3);
        entities.add(entity4);
        mongoTemplate.save(entity1, Constants.IOT_TABLE_NAME);
        mongoTemplate.save(entity2, Constants.IOT_TABLE_NAME);
        mongoTemplate.save(entity3, Constants.IOT_TABLE_NAME);
        mongoTemplate.save(entity4, Constants.IOT_TABLE_NAME);
        return entities;
    }
}
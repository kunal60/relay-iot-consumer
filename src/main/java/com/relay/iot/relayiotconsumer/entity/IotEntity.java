package com.relay.iot.relayiotconsumer.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;


import com.relay.iot.relayiotconsumer.data.Constants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import lombok.Builder;
import lombok.Data;

/**
 * @author Kunal
 */
@Document(collection = Constants.IOT_TABLE_NAME)
@Data
@Builder
public class IotEntity {

    @Id
    @Builder.Default
    private String uuid = UUID.randomUUID().toString();
    
    private Long id;

    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal value;
    
    private OffsetDateTime timestamp;
    private String type;
    private String name;
    private Long clusterId;
    private boolean initialized;
}
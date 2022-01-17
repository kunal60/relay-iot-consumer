package com.relay.iot.relayiotconsumer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IotData {

    private Long id;

    private String type;


    private String name;

    @Nullable
    private Long clusterId;

    private String timestamp;

    private BigDecimal value;


    private boolean initialized;
}


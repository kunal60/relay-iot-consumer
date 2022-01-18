package com.relay.iot.relayiotconsumer.util;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class RequestValidatorTest {

    @Test
    public void isValidRequest() {
        Assert.assertTrue(RequestValidator.isValidRequest("MAX", Optional.of("2022-01-17T17:09:38.706056800Z"), Optional.of("2022-01-17T17:10:17.880046Z")));
        Assert.assertFalse(RequestValidator.isValidRequest("TEST", Optional.of("2022-01-17T17:09:38.706056800Z"), Optional.of("2022-01-17T17:10:17.880046Z")));
        Assert.assertFalse(RequestValidator.isValidRequest(null, Optional.of("2022-01-17T17:09:38.706056800Z"), Optional.of("2022-01-17T17:10:17.880046Z")));
        Assert.assertFalse(RequestValidator.isValidRequest("MAX", Optional.of(""), Optional.of("2022-01-17T17:10:17.880046Z")));
    }

}
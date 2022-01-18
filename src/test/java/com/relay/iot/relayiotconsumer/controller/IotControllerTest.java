package com.relay.iot.relayiotconsumer.controller;


import com.relay.iot.relayiotconsumer.repository.IotDataRepository;
import com.relay.iot.relayiotconsumer.service.IotOperationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class IotControllerTest {

    @MockBean
    private IotDataRepository iotDataRepository;

    @Autowired
    private ApplicationContext applicationContext;

    @MockBean
    IotOperationService iotOperationService;

    @Autowired
    private MockMvc mvc;

    private LinkedMultiValueMap<String, String> validList;
    private LinkedMultiValueMap<String, String> inValidList;

    @Before
    public void initContainers() {
        validList = validRequestParameters();
        inValidList = inValidRequestParameters();
        when(iotOperationService.execute(eq("min"),
                anyMap())).thenReturn(BigDecimal.ONE);
    }


    @Test
    public void testOperationSuccess() throws Exception {
        MvcResult minResult = mvc
                .perform(MockMvcRequestBuilders.get("/iotData/operation/min").params(validList))
                .andReturn();
        assertTrue(minResult.getResponse().getContentAsString().contains("1"));
    }

    @Test
    public void testMandatoryParameters() throws Exception {
        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.get("/iotData/operation/min").params(inValidList))
                .andReturn();
        assertEquals(400, result.getResponse().getStatus());
    }


    @Test
    public void testOperationName() throws Exception {
        MvcResult result = mvc
                .perform(MockMvcRequestBuilders.get("/iotData/operation/invalidOperation").params(validList))
                .andReturn();
        assertEquals(400, result.getResponse().getStatus());
    }


    private LinkedMultiValueMap<String, String> validRequestParameters() {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("from", "2022-01-17T17:09:38.706056800Z");
        requestParams.add("to", "2022-01-17T17:10:17.880046Z");
        requestParams.add("clusterId", "1");
        requestParams.add("eventType", "TEMPERATURE");
        return requestParams;
    }


    private LinkedMultiValueMap<String, String> inValidRequestParameters() {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("from", "");
        requestParams.add("to", "2022-01-17T17:10:17.880046Z");
        requestParams.add("clusterId", "1");
        requestParams.add("eventType", "TEMPERATURE");
        return requestParams;
    }

}
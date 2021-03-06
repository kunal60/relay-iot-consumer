package com.relay.iot.relayiotconsumer.controller;

import com.relay.iot.relayiotconsumer.data.Constants;
import com.relay.iot.relayiotconsumer.exception.InvalidInputException;
import com.relay.iot.relayiotconsumer.service.IotOperationService;
import com.relay.iot.relayiotconsumer.util.RequestValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/iotData")
public class IotController {
    private final IotOperationService iotOperationService;

    @Autowired
    public IotController(IotOperationService iotOperationService) {
        this.iotOperationService = iotOperationService;
    }

    @Operation(summary = "Gives output of the operation provided")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Operation result"),
            @ApiResponse(responseCode = "400", description = "Please provide from and to Dates and valid input")
    })
    @GetMapping(value = "/operation/{operation}")
    public ResponseEntity<BigDecimal> getOperation(@PathVariable("operation") String operation,
                                                   @RequestParam(value = "from") Optional<String> from,
                                                   @RequestParam(value = "to") Optional<String> to,
                                                   @RequestParam("eventType") Optional<String> eventType,
                                                   @RequestParam("clusterId") Optional<String> clusterId) {

        if (!RequestValidator.isValidRequest(operation, from, to)) {
            throw new InvalidInputException(InvalidInputException.Code.INVALID_INPUT, "Invalid request. from & to are mandatory params. And operation names are AVERAGE, MEDIAN, MAX, MIN in either upper orlower case");
        }
        Map<String, String> map = new HashMap<>();
        map.put(Constants.START_DATE, from.isPresent() ? from.get() : null);
        map.put(Constants.END_DATE, to.isPresent() ? to.get() : null);
        map.put(Constants.EVENT_TYPE, eventType.isPresent() ? eventType.get() : null);
        map.put(Constants.CLUSTER_ID, clusterId.isPresent() ? clusterId.get() : null);


        return new ResponseEntity<>(iotOperationService.execute(operation, map), HttpStatus.CREATED);

    }


}

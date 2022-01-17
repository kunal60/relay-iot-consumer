package com.relay.iot.relayiotconsumer.repository.impl;

import com.relay.iot.relayiotconsumer.data.Constants;
import com.relay.iot.relayiotconsumer.entity.IotEntity;
import com.relay.iot.relayiotconsumer.exception.DataNotFoundException;
import com.relay.iot.relayiotconsumer.repository.IotRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class IotDataRepositoryCustomImpl implements IotRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    private static final String TIME_STAMP= "timestamp";

    private static final String VALUE= "value";

    @Autowired
    public IotDataRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public BigDecimal findMinValueByClusterIdAndTypeAndTimestampBetween(String clusterId, String eventType,
                                                                        OffsetDateTime start,
                                                                        OffsetDateTime end) throws DataNotFoundException {
        BigDecimal result;

        AggregationOperation match1 = Aggregation.match(Criteria.where(TIME_STAMP).gte(start));
        AggregationOperation match2 = Aggregation.match(Criteria.where(TIME_STAMP).lte(end));
        AggregationOperation unwind = Aggregation.unwind(VALUE);
        AggregationOperation sort = Aggregation.sort(Sort.Direction.ASC, VALUE);
        AggregationOperation limit = Aggregation.limit(1);
        List<AggregationOperation> operationsList = new ArrayList<>();
        if (clusterId != null ) {
            operationsList.add(Aggregation.match(Criteria.where("clusterId").is(clusterId)));
        }
        if (StringUtils.hasText(eventType)) {
            operationsList.add(Aggregation.match(Criteria.where("type").is(eventType)));
        }
        operationsList.add(match1);
        operationsList.add(match2);
        operationsList.add(unwind);
        operationsList.add(sort);
        operationsList.add(limit);

        Aggregation aggregation = Aggregation.newAggregation(operationsList);
        AggregationResults<IotEntity> aggregationResults = mongoTemplate.aggregate(aggregation,
                Constants.IOT_TABLE_NAME, IotEntity.class);
        if (aggregationResults.getMappedResults().isEmpty()) {
            throw new DataNotFoundException("Data not found");
        } else {
            result = aggregationResults.getMappedResults().get(0).getValue();
        }
        return result;
    }

    @Override
    public BigDecimal findMaxValueByClusterIdAndTypeAndTimestampBetween(String clusterId, String eventType,
                                                                        OffsetDateTime start,
                                                                        OffsetDateTime end) throws DataNotFoundException {
        BigDecimal result;

        List<AggregationOperation> operationsList = createBaseAggregateOperations(clusterId, eventType, start, end);

        AggregationOperation sort = Aggregation.sort(Sort.Direction.DESC, VALUE);
        AggregationOperation limit = Aggregation.limit(1);
        operationsList.add(sort);
        operationsList.add(limit);

        Aggregation aggregation = Aggregation.newAggregation(operationsList);
        AggregationResults<IotEntity> aggregationResults = mongoTemplate.aggregate(aggregation,
                Constants.IOT_TABLE_NAME, IotEntity.class);
        if (aggregationResults.getMappedResults().isEmpty()) {
            throw new DataNotFoundException("Data not found");
        } else {
            result = aggregationResults.getMappedResults().get(0).getValue();
        }
        return result;
    }

    private List<AggregationOperation> createBaseAggregateOperations(String clusterId, String eventType,
                                                                     OffsetDateTime start,
                                                                     OffsetDateTime end) {
        AggregationOperation match1 = Aggregation.match(Criteria.where(TIME_STAMP).gte(start));
        AggregationOperation match2 = Aggregation.match(Criteria.where(TIME_STAMP).lte(end));
        AggregationOperation unwind = Aggregation.unwind(VALUE);
        List<AggregationOperation> operationsList = new ArrayList<>();
        if (clusterId != null ) {
            operationsList.add(Aggregation.match(Criteria.where("clusterId").is(Long.valueOf(clusterId))));
        }
        if (StringUtils.hasText(eventType)) {
            operationsList.add(Aggregation.match(Criteria.where("type").is(eventType)));
        }
        operationsList.add(match1);
        operationsList.add(match2);
        operationsList.add(unwind);
        return operationsList;
    }

    @Override
    public BigDecimal findAvgValueByClusterIdAndTypeAndTimestampBetween(String clusterId, String eventType,
                                                                        OffsetDateTime start,
                                                                        OffsetDateTime end) throws DataNotFoundException {
        BigDecimal result;
        List<AggregationOperation> operationsList = createBaseAggregateOperations(clusterId, eventType, start, end);
        Aggregation aggregation = Aggregation.newAggregation(operationsList);
        AggregationResults<IotEntity> aggregationResults = mongoTemplate.aggregate(aggregation,
                Constants.IOT_TABLE_NAME, IotEntity.class);
        if (aggregationResults.getMappedResults().isEmpty()) {
            throw new DataNotFoundException("Result not found");
        } else {
            BigDecimal sum = aggregationResults.getMappedResults().stream().map(data -> data.getValue())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            Integer resultsSize = aggregationResults.getMappedResults().size();
            result = sum.divide(new BigDecimal(resultsSize), 4, RoundingMode.HALF_EVEN);
        }
        return result;
    }

    @Override
    public BigDecimal findMedianValueByClusterIdAndTypeAndTimestampBetween(String clusterId, String eventType,
                                                                           OffsetDateTime start, OffsetDateTime end) throws DataNotFoundException {
        BigDecimal result;
        List<AggregationOperation> operationsList = createBaseAggregateOperations(clusterId, eventType, start, end);

        AggregationOperation sort = Aggregation.sort(Sort.Direction.ASC, VALUE);
        operationsList.add(sort);
        Aggregation aggregation = Aggregation.newAggregation(operationsList);
        AggregationResults<IotEntity> aggregationResults = mongoTemplate.aggregate(aggregation,
                Constants.IOT_TABLE_NAME, IotEntity.class);
        if (aggregationResults.getMappedResults().isEmpty()) {
            throw new DataNotFoundException("Result not found");
        } else {
            int mapSize = aggregationResults.getMappedResults().size();
            int middleElement = Math.floorDiv(mapSize, 2);
            if (mapSize == 1) {
                result = aggregationResults.getMappedResults().get(0).getValue();
            } else if (Math.floorMod(middleElement, 2) != 0) {
                // odd number of elements
                result = aggregationResults.getMappedResults().get(middleElement).getValue();
            } else {
                // even number of elements
                result = aggregationResults.getMappedResults().get(middleElement - 1).getValue();
                result = result.add(aggregationResults.getMappedResults().get(middleElement).getValue());
                result = result.divide(new BigDecimal(2), 4, RoundingMode.UNNECESSARY);
            }
        }
        return result;
    }
}
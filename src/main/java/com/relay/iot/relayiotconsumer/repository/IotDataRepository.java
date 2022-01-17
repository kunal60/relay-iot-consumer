package com.relay.iot.relayiotconsumer.repository;

import com.relay.iot.relayiotconsumer.entity.IotEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IotDataRepository extends MongoRepository<IotEntity, String>, IotRepositoryCustom {


}

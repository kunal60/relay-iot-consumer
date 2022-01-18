package com.relay.iot.relayiotconsumer.repository;

import com.relay.iot.relayiotconsumer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserName(String username);
}
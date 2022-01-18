package com.relay.iot.relayiotconsumer;

import com.relay.iot.relayiotconsumer.entity.User;
import com.relay.iot.relayiotconsumer.repository.UserRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "IOT Consumer API", version = "2.0", description = "Iot Information"))
public class RelayIotConsumerApplication {


    @Autowired
    private UserRepository repository;

    //On Application startup, these users will be saved in db
    @PostConstruct
    public void initUsers() {
        List<User> users = Stream.of(
                new User(101, "kunal", "admin", "kunalmalhotra9322@gmail.com"),
                new User(102, "user2", "pwd2", "user2@gmail.com"),
                new User(103, "user3", "pwd3", "user3@gmail.com")
        ).collect(Collectors.toList());
        repository.saveAll(users);
    }

    public static void main(String[] args) {
        SpringApplication.run(RelayIotConsumerApplication.class, args);
    }

}

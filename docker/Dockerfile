FROM openjdk:11-jdk-slim as builder
MAINTAINER "Kunal Malhotra"
WORKDIR /usr/app

ARG JAR_FILE=./target/relay-iot-consumer.jar
COPY ${JAR_FILE} relay-iot-consumer.jar
ENTRYPOINT ["java", "-jar", "/usr/app/relay-iot-consumer.jar"]
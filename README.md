# relay-iot-consumer

## Prerequisite

You'll need:
 * Java 11
 * Maven 3.5.x
 * a environment variable JAVA_HOME pointing to your JAVA 11 installation

## Quickstart

Do the following commands in a shell:

    $ mvn clean
    $ mvn install
    $ docker-compose up
    
    
````curlrc
curl --location --request POST 'http://localhost:8081/authenticate' \
--header 'Content-Type: application/json' \
--data-raw '{
    "userName": "kunal",
    "password": "admin"
}'
````    

````curlrc
curl --location --request GET 'http://localhost:8081/iotData/operation/max?from=2022-01-17T17:09:38.706056800Z&to=2022-01-17T17:10:17.880046Z' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrdW5hbCIsImV4cCI6MTY0MjU3NDQwMCwiaWF0IjoxNjQyNTM4NDAwfQ.QHfdCad3CkNOGsW5zOcX1Z012i_GWmtQiOGMz9RE5kQ'
````   
    
#Some Useful Docker commands 
#stop a container
docker stop 8872fe163331   
#Remove a stopped container
docker container rm 8872fe163331

docker image rm 788b222dc8f7
docker kill $(docker ps -q)
docker system prune -a
docker container ls
docker image ls

For Max
db.getCollection('iot_data').find({timestamp: { $gte: '2022-01-17T17:09:38.706056800Z', $lte: "2022-01-17T17:10:17.880046Z" }}).sort({"value" : -1}).limit(1)


http://localhost:8081/iotData/operation/max?from=2022-01-17T17:09:38.706056800Z&to=2022-01-17T17:10:17.880046Z
For Min
db.getCollection('iot_data').find({timestamp: { $gte: '2022-01-17T17:09:38.706056800Z', $lte: "2022-01-17T17:10:17.880046Z" }}).sort({"value" : 1}).limit(1)

For Average
http://localhost:8081/iotData/operation/average?from=2022-01-17T17:09:38.706056800Z&to=2022-01-17T17:10:17.880046Z


For median
http://localhost:8081/iotData/operation/median?from=2022-01-17T17:09:38.706056800Z&to=2022-01-17T17:10:17.880046Z
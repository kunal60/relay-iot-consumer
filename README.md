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
    
## Architecture
![Data Published](img/Image.jpg)
    
## CURL Requests 
````curlrc
Configured users and pswd:
User1 -> "kunal", "admin"
User2 -> "user2", "pwd2"
User2 -> "user3", "pwd3"
````   
   
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
   
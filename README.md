# relay-iot-consumer

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
docker build --tag kafka-consumer-service:latest gatewayService/. 
docker tag kafka-consumer-service:latest jubox70/kafka-consumer-service:latest
docker push jubox70/kafka-consumer-service:latest
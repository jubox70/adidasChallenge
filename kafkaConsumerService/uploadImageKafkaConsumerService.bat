docker build --tag kafka-consumer-service:latest . 
docker tag kafka-consumer-service:latest jubox70/kafka-consumer-service:latest
docker push jubox70/kafka-consumer-service:latest
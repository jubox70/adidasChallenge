docker build --tag gateway-service:latest . 
docker tag gateway-service:latest jubox70/gateway-service:latest
docker push jubox70/gateway-service:latest
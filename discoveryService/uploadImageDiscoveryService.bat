docker build --tag discovery-service:latest . 
docker tag discovery-service:latest jubox70/discovery-service:latest
docker push jubox70/discovery-service:latest
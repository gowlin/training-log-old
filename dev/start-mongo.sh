#!/bin/bash

docker rm -f training-mongo-dev
docker run --name training-mongo-dev -d -p 8001:27017 mongo:3.2 

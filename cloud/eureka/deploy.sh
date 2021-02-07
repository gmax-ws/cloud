#!/bin/bash

source ../../global.sh

name=$EUREKA_NAME
from=$EUREKA_FROM

jarfile="$name-0.0.1-SNAPSHOT.jar"
docker=true

# build jar
echo "***** Deploying $name *****"
mvn clean install

# deploy
if $docker; then
  # create docker file
  eval "dockerFile Dockerfile $from $jarfile"
  docker rm -f eureka
  docker-compose up -d eureka
else
  eval "java -jar target/$jarfile"
fi

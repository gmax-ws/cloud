#!/bin/bash

source ../../../global.sh

name=$ORDERS_NAME
from=$ORDERS_FROM

jarfile="$name-0.0.1-SNAPSHOT.jar"
docker=true

# build jar
echo "***** Deploying $name *****"
mvn clean install

# deploy
if $docker; then
  # create docker file
  eval "dockerFile Dockerfile $from $jarfile"
  docker rm -f "$name"
  docker-compose up -d "$name"
else
  eval "java -jar target/$jarfile"
fi

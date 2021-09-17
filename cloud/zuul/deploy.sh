#!/bin/bash

source ../../global.sh

name=$ZUUL_NAME
from=$ZUUL_FROM

jarfile="$name-0.0.1-SNAPSHOT.jar"
docker=true

# build jar
echo "***** Deploying $name *****"
mvn clean install

# deploy
if $docker; then
  # create docker image
  eval "dockerFile Dockerfile $from $jarfile"
  docker rm -f "$name"
  docker-compose up -d "$name"
else
  eval "java -jar target/$jarfile"
fi

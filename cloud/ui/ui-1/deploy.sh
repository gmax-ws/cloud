#!/bin/bash

source ../../../global.sh

name=$UI1_NAME
from=$UI1_FROM

jarfile="$name-0.0.1-SNAPSHOT.jar"
docker=true

# build jar
echo "***** Deploying $name *****"
mvn clean install

if $docker; then
  # create docker file
  eval "dockerFile Dockerfile $from $jarfile"
  docker rm -f "$name"
  docker-compose up -d --build "$name"
else
  eval "java -jar target/$jarfile"
fi

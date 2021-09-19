#!/bin/bash

source ../../../global.sh

name=$TEMPLATE_NAME
from=$TEMPLATE_FROM

jarfile="$name-0.0.1-SNAPSHOT.jar"
docker=true

# build jar
echo "***** Deploying $name *****"
mvn clean install

# deploy
if $docker; then
  # create docker image
  eval "dockerFile Dockerfile $from $jarfile"
  # shellcheck disable=SC2086
  docker rm -f "$name"
  docker-compose up -d --build "$name"
else
  eval "java -jar target/$jarfile"
fi

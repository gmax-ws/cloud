#!/bin/bash

source ../../global.sh

from=$TURBINE_FROM
name=$TURBINE_NAME

jarfile="$name-0.0.1-SNAPSHOT.jar"

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

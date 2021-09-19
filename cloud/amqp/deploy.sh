#!/bin/bash

source ../../global.sh

name=$BUS_NAME

echo "***** Deploying $name *****"
docker rm -f "$name"
docker-compose up -d --build "$name"
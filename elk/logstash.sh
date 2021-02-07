#!/bin/bash

source ../global.sh

name=$ELK_LOGSTASH_NAME

echo "***** Deploying $name *****"
docker rm -f "$name"
docker-compose up -d "$name"
#!/bin/bash

source ../global.sh

name=$ELK_SEARCH_NAME

echo "***** Deploying $name *****"
docker rm -f "$name"
docker-compose up -d "$name"
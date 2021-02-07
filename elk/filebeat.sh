#!/bin/bash

source ../global.sh

name=$ELK_FILEBEAT_NAME
from=$ELK_FILEBEAT_FROM

function makeDockerfile() {
  echo "FROM $from" >"$1"
  {
    echo "ENV ELASTICSEARCH=$ELK_SEARCH_IP:$ELK_SEARCH_PORT"
    echo "ENV LOGSTASH=$ELK_LOGSTASH_IP:$ELK_LOGSTASH_PORT"
    echo "COPY filebeat.yml /usr/share/filebeat/filebeat.yml"
    echo "USER root"
    echo "RUN mkdir /var/log/integration"
    echo "RUN chown -R root /usr/share/filebeat/"
    echo "RUN chmod -R go-w /usr/share/filebeat/"
  } >>"$1"
}

echo "***** Deploying $name *****"
eval "makeDockerfile ./Dockerfile"
docker rm -f filebeat
docker-compose up -d --build filebeat
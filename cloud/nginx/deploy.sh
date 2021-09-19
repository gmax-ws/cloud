#!/bin/bash

source ../../global.sh

name=$NGINX_NAME
from=$NGINX_FROM
http=$NGINX_HTTP
https=$NGINX_HTTPS

conf="default.conf"
file="Dockerfile"

function makeDefault() {
  echo "server {" >"$1"
  {
    echo "    listen       $http;"
    echo "    location / {"
    echo "        proxy_pass $SERVICE_GATEWAY;"
    echo "    }"
    echo "}"
  } >>"$1"
}

function makeDockerFile() {
  echo "FROM $from" >"$1"
  {
    echo "EXPOSE $http/tcp"
    echo "EXPOSE $https/tcp"
    echo "COPY $conf /etc/nginx/conf.d"
    echo "WORKDIR /usr/share/nginx/html"
    echo 'CMD ["/bin/sh", "-c", "exec nginx -g '\''daemon off;'\'';"]'
  } >>"$1"
}

echo "***** Deploying $name *****"
eval "makeDefault $conf"
eval "makeDockerFile $file"
docker rm -f "$name"
docker-compose up -d --build "$name"
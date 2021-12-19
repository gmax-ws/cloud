#!/usr/bin/env bash

function dockerFile() {
  echo "FROM $2" >"$1"
  {
    echo "COPY target/$3 /$3" >>"$1"
    echo "ENTRYPOINT java -Xms512M -Xmx1024M -jar $3" >>"$1"
  } >>"$1"
}

# -- global
export GENERIC_DOMAIN=example.com
export CLUSTER_NAME=integration
export DOCKER_IMAGE=openjdk:8-jre-alpine
export ENCRYPT_KEY=8N6pVhqW1Fpx8g7o

# network
export NETWORK_NAME=cloud-net
export NETWORK_SUBNET=172.18.0.0/16
export NETWORK_GATEWAY=172.18.0.1

# logging
export SPRING_MAIN_BANNER_MODE=off
export LOG_PATH=/var/log
export LOG_PATH_MOUNT=$LOG_PATH/$CLUSTER_NAME
export LOGGER_VOLUME=$LOG_PATH_MOUNT:$LOG_PATH

# HTTP-Basic AUTH
export AUTH_USERNAME=marius
export AUTH_PASSWORD='{bcrypt}$2a$10$5R8LwY81mP.GZeSPjuejVebMUk3OioYsX13frG16ZESaC0FXRJDhW'

# OAuth2 client
export CLIENT_ID=template
export CLIENT_SECRET='{cipher}79c3371fd5c53eec4cb7cb4e09013ee1204011ae9be40ab480388140cfdd4bd71800846701c9d3a7443d98a1f32180052e305afd807c63437aaac2841ad24af8'
export CLIENT_SCOPE=rest

# Identity management
export KEYCLOAK_NAME=keycloak
export KEYCLOAK_FROM=$DOCKER_IMAGE
export KEYCLOAK_IMAGE=keycloak:1.0
export KEYCLOAK_VERSION=16.0.0
export KEYCLOAK_IP=172.18.10.1
export KEYCLOAK_HTTP_PORT=8090
export KEYCLOAK_HTTPS_PORT=8453
export KEYCLOAK_REALM=integration
export KEYCLOAK_URI=http://$KEYCLOAK_IP:$KEYCLOAK_HTTP_PORT/auth
#export KEYCLOAK_URI=https://$KEYCLOAK_IP:$KEYCLOAK_HTTPS_PORT/auth
export KEYCLOAK_URL=${KEYCLOAK_URI}/realms/${KEYCLOAK_REALM}/protocol/openid-connect

# Service BUS
export BUS_NAME=amqp
export BUS_IMAGE=rabbitmq:3.8.9
export BUS_CONTAINER=rabbitmq
export BUS_VOLUME=/var/log/integration:/var/log/rabbitmq
export BUS_IP=172.18.0.5
export BUS_PORT=5672

# Config
export CONFIG_NAME=config
export CONFIG_FROM=$DOCKER_IMAGE
export CONFIG_ENABLED=true
export CONFIG_IMAGE=config:1.0
export CONFIG_CONTAINER=config
export CONFIG_NETWORK=$NETWORK_NAME
export CONFIG_IP=172.18.0.4
export CONFIG_PORT=8888
export CONFIG_SERVER=http://$CONFIG_IP:$CONFIG_PORT
export CONFIG_REPO_MAP=$HOME/config-repo
export CONFIG_REPO=/tmp/config-repo
export CONFIG_PROFILE=poc

export EUREKA_NAME=eureka
export EUREKA_FROM=$DOCKER_IMAGE
export EUREKA_CONTAINER=eureka
export EUREKA_IMAGE=eureka:1.0
export EUREKA_IP=172.18.0.10
export EUREKA_PORT=8761
export EUREKA_URI=http://$EUREKA_IP:$EUREKA_PORT/eureka
export EUREKA_DC=romania

export ZUUL_NAME=zuul
export ZUUL_FROM=$DOCKER_IMAGE
export ZUUL_CONTAINER=zuul
export ZUUL_IMAGE=zuul:1.0
export ZUUL_IP=172.18.0.2
export ZUUL_PORT=8765
export SERVICE_GATEWAY=http://$ZUUL_IP:$ZUUL_PORT

export NGINX_NAME=nginx
export NGINX_FROM=nginx:stable-apline
export NGINX_IMAGE=nginx:1.0
export NGINX_CONTAINER=nginx
export NGINX_NETWORK=$NETWORK_NAME
export NGINX_IP=172.18.0.100
export NGINX_HTTP=80
export NGINX_HTTPS=443

export ORDERS_NAME=orders
export ORDERS_FROM=$DOCKER_IMAGE
export ORDERS_IMAGE=orders:1.0
export ORDERS_CONTAINER=orders
export ORDERS_NETWORK=$NETWORK_NAME
export ORDERS_IP=172.18.2.1
export ORDERS_PORT=9010
export ORDERS_REPLICAS=2

export TEMPLATE_NAME=template
export TEMPLATE_FROM=$DOCKER_IMAGE
export TEMPLATE_IMAGE=template:1.0
export TEMPLATE_CONTAINER=template
export TEMPLATE_NETWORK=$NETWORK_NAME
export TEMPLATE_IP=172.18.2.2
export TEMPLATE_PORT=9000
export TEMPLATE_REPLICAS=1

export TURBINE_NAME=turbine
export TURBINE_FROM=$DOCKER_IMAGE
export TURBINE_CONTAINER=turbine
export TURBINE_IMAGE=turbine:1.0
export TURBINE_NETWORK=$NETWORK_NAME
export TURBINE_IP=172.18.0.3
export TURBINE_PORT=8844

export OAUTH_NAME=oauth2
export OAUTH_FROM=$DOCKER_IMAGE
export OAUTH_PROVIDER=keycloak
export OAUTH_IMAGE=oauth2:1.0
export OAUTH_CONTAINER=oauth2
export OAUTH_NETWORK=$NETWORK_NAME
export OAUTH_IP=172.18.1.0
export OAUTH_PORT=8080

export UI1_NAME=ui-1
export UI1_FROM=$DOCKER_IMAGE
export UI1_IMAGE=ui-1:1.0
export UI1_CONTAINER=ui-1
export UI1_NETWORK=$NETWORK_NAME
export UI1_IP=172.18.1.1
export UI1_PORT=8081

export UI2_NAME=ui-2
export UI2_FROM=$DOCKER_IMAGE
export UI2_IMAGE=ui-2:1.0
export UI2_CONTAINER=ui-2
export UI2_NETWORK=$NETWORK_NAME
export UI2_IP=172.18.1.2
export UI2_PORT=8082

export UI3_NAME=ui-3
export UI3_FROM=$DOCKER_IMAGE
export UI3_IMAGE=ui-3:1.0
export UI3_CONTAINER=ui-3
export UI3_NETWORK=$NETWORK_NAME
export UI3_IP=172.18.1.3
export UI3_PORT=8083

# ELK stack ip=172.18.20.xxx
export ELK_VERSION=7.10.1
export ELK_SINGLE_NODE="single-node"
# elasticsearch
export ELK_SEARCH_NAME=elasticsearch
export ELK_SEARCH_IMAGE=docker.elastic.co/elasticsearch/elasticsearch:$ELK_VERSION
export ELK_SEARCH_IP=172.18.20.1
export ELK_SEARCH_PORT=9200
export ELK_SEARCH_SERVER="http://$ELK_SEARCH_IP:$ELK_SEARCH_PORT"
# logstash
export ELK_LOGSTASH_NAME=logstash
export ELK_LOGSTASH_IMAGE=docker.elastic.co/logstash/logstash:$ELK_VERSION
export ELK_LOGSTASH_IP=172.18.20.2
export ELK_LOGSTASH_PORT=5044
export ELK_LOGSTASH_PORTH=9600
export ELK_LOGSTASH_SERVER="http://$ELK_LOGSTASH_IP:$ELK_LOGSTASH_PORTH"
# kibana
export ELK_KIBANA_NAME=kibana
export ELK_KIBANA_IMAGE=docker.elastic.co/kibana/kibana:$ELK_VERSION
export ELK_KIBANA_IP=172.18.20.3
export ELK_KIBANA_PORT=5601
export ELK_KIBANA_SERVER="http://$ELK_KIBANA_IP:$ELK_KIBANA_PORT"
# filebeat
export ELK_FILEBEAT_FROM=docker.elastic.co/beats/filebeat:$ELK_VERSION
export ELK_FILEBEAT_NAME=filebeat
export ELK_FILEBEAT_IP=172.18.20.4
export ELK_FILEBEAT_IMAGE="filebeat:1.0"

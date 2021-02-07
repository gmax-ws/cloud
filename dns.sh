#!/usr/bin/env bash

source ./global.sh

domain=$GENERIC_DOMAIN

rm ./hosts
{
  echo "$ZUUL_IP zuul.$domain"
  echo "$EUREKA_IP eureka.$domain"
  echo "$KEYCLOAK_IP idam.$domain"
  echo "$UI1_IP ui1.$domain"
  echo "$UI2_IP ui2.$domain"
  echo "$UI3_IP ui3.$domain"
  echo "$ORDERS_IP orders.$domain"
  echo "$TEMPLATE_IP template.$domain"
  echo "$BUS_IP bus.$domain"
  echo "$CONFIG_IP config.$domain"
  echo "$TURBINE_IP turbine.$domain"
  echo "$OAUTH_IP oauth.$domain"
  echo "$ELK_KIBANA_IP kibana.$domain"
} >> ./hosts

#export HOSTALIASES=~/.hosts

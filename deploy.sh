#!/bin/bash

source ./global.sh

wdir="$(pwd)"

function docker_ps() {
  docker ps -a
}

function docker_rm() {
  eval "docker rm -f \$(docker ps -aq)"
}

function docker_rmi() {
  eval "docker rmi \$(docker images -aq)"
}

function idam() {
  cd ./idam && ./deploy.sh
  eval "cd $wdir"
}

function eureka() {
  cd ./cloud/eureka && ./deploy.sh
  eval "cd $wdir"
}

function zuul() {
  cd ./cloud/zuul && ./deploy.sh
  eval "cd $wdir"
}

function orders() {
  cd ./cloud/services/orders && ./deploy.sh
  eval "cd $wdir"
}

function template() {
  cd ./cloud/services/template && ./deploy.sh
  eval "cd $wdir"
}

function ui1() {
  cd ./cloud/ui/ui-1 && ./deploy.sh
  eval "cd $wdir"
}

function ui2() {
  cd ./cloud/ui/ui-2 && ./deploy.sh
  eval "cd $wdir"
}

function ui3() {
  cd ./cloud/ui/ui-3 && ./deploy.sh
  eval "cd $wdir"
}

function oauth2() {
  cd ./cloud/ui/oauth2 && ./deploy.sh
  eval "cd $wdir"
}

function turbine() {
  cd ./cloud/turbine && ./deploy.sh
  eval "cd $wdir"
}

function bus() {
  cd ./cloud/amqp && ./deploy.sh
  eval "cd $wdir"
}

function config() {
  cd ./cloud/config && ./deploy.sh
  eval "cd $wdir"
}

function nginx() {
  cd ./cloud/nginx && ./deploy.sh
  eval "cd $wdir"
}

function elk() {
  cd ./elk && ./deploy.sh
  eval "cd $wdir"
}

function deployAll() {
  docker_rm
  bus
  config
  idam
  eureka
  # deploy service
  template
  orders
  # deploy others
  zuul
  turbine
  oauth2
  ui1
  ui2
  ui3
  nginx
}

function minimal() {
#  docker_rm
#  bus
#  config
  idam
  eureka
  # deploy service
  orders
  # deploy others
  zuul
  ui2
}

while true; do
  PS3='Which application do you want to deploy: '
  options=("Quit" "Minimal" "All" "IDAM" "Eureka" "Zuul" "Template" "Orders"
    "UI-1" "UI-2" "UI-3" "OAuth2" "Turbine" "Nginx" "Config" "Bus" "RM" "RMI" "PS" "ELK" "Environment")
  select opt in "${options[@]}"; do
    case $opt in
    "Quit")
      break 2
      ;;
    "Minimal")
      minimal
      break
      ;;
    "All")
      deployAll
      break
      ;;
    "IDAM")
      idam
      break
      ;;
    "Eureka")
      eureka
      break
      ;;
    "Zuul")
      zuul
      break
      ;;
    "Template")
      template
      break
      ;;
    "Orders")
      orders
      break
      ;;
    "UI-1")
      ui1
      break
      ;;
    "UI-2")
      ui2
      break
      ;;
    "UI-3")
      ui3
      break
      ;;
    "OAuth2")
      oauth2
      break
      ;;
    "Turbine")
      turbine
      break
      ;;
    "Nginx")
      nginx
      break
      ;;
    "Config")
      config
      break
      ;;
    "Bus")
      bus
      break
      ;;
    "RM")
      docker_rm
      break
      ;;
    "RMI")
      docker_rmi
      break
      ;;
    "PS")
      docker_ps
      break
      ;;
    "ELK")
      elk
      break
      ;;
    "Environment")
      printenv
      break
      ;;
    *) echo "invalid option $REPLY" ;;
    esac
  done
done

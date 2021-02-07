#!/bin/bash

read -r -p "Initialize (y/n)? " choice
case "$choice" in
  y|Y )
  sudo sysctl -w vm.max_map_count=262144

  sudo rm -r /tmp/elasticsearch
  sudo mkdir /tmp/elasticsearch
  sudo mkdir /tmp/elasticsearch/data
  sudo chmod -R 0777 /tmp/elasticsearch
  ;;
esac

read -r -p "Pull images (y/n)? " pull
case "$pull" in
  y|Y )
  docker pull "$ELK_SEARCH_IMAGE"
  docker pull "$ELK_LOGSTASH_IMAGE"
  docker pull "$ELK_KIBANA_IMAGE"
  docker pull "$ELK_FILEBEAT_IMAGE"
  ;;
esac

read -r -p "Deploy (y/n)? " deploy
case "$deploy" in
  y|Y )
  ./elasticsearch.sh
  ./logstash.sh
  ./kibana.sh
  ./filebeat.sh
  ;;
esac

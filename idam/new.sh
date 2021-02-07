#!/bin/bash
  
# https://stackoverflow.com/a/47181236/1098564
wget -c https://downloads.jboss.org/keycloak/11.0.0/keycloak-11.0.0.zip
 
#https://askubuntu.com/a/994965/109301
unzip -n keycloak-11.0.0.zip
cd keycloak-11.0.0/bin
./add-user-keycloak.sh -r master -u admin -p admin
./standalone.sh -b 0.0.0.0

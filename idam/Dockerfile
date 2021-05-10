FROM openjdk:8-jre-alpine
COPY ./realm-export.json /tmp/realm-export.json
RUN wget -c https://github.com/keycloak/keycloak/releases/download/13.0.0/keycloak-13.0.0.zip
RUN unzip -n keycloak-13.0.0.zip -d /opt
RUN mv /opt/keycloak-13.0.0 /opt/keycloak
ENTRYPOINT /opt/keycloak/bin/standalone.sh -Djboss.socket.binding.port-offset=10 -Dkeycloak.migration.action=import -Dkeycloak.migration.provider=singleFile -Dkeycloak.migration.file=/tmp/realm-export.json -Dkeycloak.migration.strategy=OVERWRITE_EXISTING -b 0.0.0.0

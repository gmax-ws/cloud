FROM openjdk:8-jre-alpine
COPY target/config-0.0.1-SNAPSHOT.jar /config-0.0.1-SNAPSHOT.jar
ENTRYPOINT java -Xms512M -Xmx1024M -jar config-0.0.1-SNAPSHOT.jar

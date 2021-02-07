FROM docker.elastic.co/beats/filebeat:7.10.1
ENV ELASTICSEARCH=172.18.20.1:9200
ENV LOGSTASH=172.18.20.2:5044
COPY filebeat.yml /usr/share/filebeat/filebeat.yml
USER root
RUN mkdir /var/log/integration
RUN chown -R root /usr/share/filebeat/
RUN chmod -R go-w /usr/share/filebeat/

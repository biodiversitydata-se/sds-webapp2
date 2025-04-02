FROM tomcat:9.0-jdk11-temurin

ENV TZ=Europe/Stockholm

RUN mkdir -p /data/sds/config \
  /data/sds/data \
  /data/lucene/namematching

COPY sbdi/data/sensitivity-categories.xml /data/sds/sensitivity-categories.xml
COPY sbdi/data/sensitivity-zones.xml /data/sds/sensitivity-zones.xml
COPY sbdi/data/legacy-sds-config.properties /data/sds/sds-config.properties
COPY sbdi/data/config/sds-config.properties /data/sds/config/sds-config.properties

COPY build/libs/sds-webapp2-[0-9].[0-9].[0-9].war $CATALINA_HOME/webapps/ROOT.war

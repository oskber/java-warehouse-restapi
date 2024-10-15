FROM quay.io/wildfly/wildfly:latest
EXPOSE 8080
LABEL authors="Oskar"
ADD target/java-warehouse-restapi-1.0-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/
ENTRYPOINT ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]
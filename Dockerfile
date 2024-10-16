# Base stage
FROM maven:3.9.9-sapmachine-21 AS base
WORKDIR /app
COPY . .

# Build stage
FROM base AS build
RUN mvn clean package

# Test stage
FROM build AS test
RUN mvn test

# Final stage
FROM quay.io/wildfly/wildfly:latest AS final
EXPOSE 8080
LABEL authors="Oskar"
COPY --from=build /app/target/java-warehouse-restapi-1.0-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/
ENTRYPOINT ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0"]
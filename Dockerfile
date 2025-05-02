# Build stage
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src ./src
RUN mvn -B clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app

ARG APP_VERSION=1.0.0
ARG PROFILE=dev

COPY --from=build /build/target/vehicle_tracker-1.0.0.jar /app/
EXPOSE 8900


ENV JAR_VERSION=${APP_VERSION}
ENV ACTIVE_PROFILE=${PROFILE}

ENV DB_URL=jdbc:postgresql://postgres-sql:5432/vehicle_tracker
ENV DB_USERNAME=username
ENV DB_PASSWORD=password

CMD java -jar -DskipTests \
    -Dspring.datasource.url=${DB_URL} \
    -Dspring.datasource.username=${DB_USERNAME} \
    -Dspring.datasource.password=${DB_PASSWORD} \
    -Dspring.profiles.active=${ACTIVE_PROFILE} \
    /app/vehicle_tracker-1.0.0.jar

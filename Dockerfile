# Build stage
# Build stage with enhanced Maven configuration
FROM openjdk AS build
WORKDIR /build

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

# Build with retry and skip tests
RUN mvn  clean package -DskipTests
# Runtime stage
FROM amazoncorretto:21
WORKDIR /app
COPY --from=build /build/target/vehicle_tracker-*.jar .
EXPOSE 8090

ENV APP_VERSION=1.0.0
ENV APP_PROFILE=dev
ENV DB_URL=jdbc:postgresql://postgres-sql:5432/vehicle_tracker

CMD ["java", "-jar", "-Dspring.profiles.active=${APP_PROFILE}", "-Dspring.datasource.url=${DB_URL}", "vehicle_tracker-${APP_VERSION}.jar"]
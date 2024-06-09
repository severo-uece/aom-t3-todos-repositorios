FROM maven:3.9.6-eclipse-temurin-17-alpine AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM maven:3.8.5-openjdk-17-slim

WORKDIR /app

COPY --from=build /app/target/*.jar cloud-config-server.jar

CMD ["java", "-jar", "cloud-config-server.jar"]
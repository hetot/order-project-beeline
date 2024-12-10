FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY build/libs/order-acceptor-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.config.location=file:/app/application-docker.properties", "-jar", "app.jar"]
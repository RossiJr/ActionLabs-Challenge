FROM openjdk:17-jdk-slim

WORKDIR /app

# Directory for the application
COPY build/libs/carboncalc-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8085

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]

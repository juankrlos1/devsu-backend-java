# Use a base image with JDK 17
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built jar file into the container
COPY target/client-service-0.0.1-SNAPSHOT.jar client-service.jar

# Expose the port on which the service will run
EXPOSE 8080

# Command to run the jar file
ENTRYPOINT ["java", "-jar", "client-service.jar"]

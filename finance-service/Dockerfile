# Use a base image with JDK 17
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built jar file into the container
COPY target/finance-service-0.0.1-SNAPSHOT.jar finance-service.jar

# Expose the port on which the service will run
EXPOSE 8090

# Command to run the jar file
ENTRYPOINT ["java", "-jar", "finance-service.jar"]

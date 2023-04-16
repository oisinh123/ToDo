# Use the official maven/Java 11 base image
FROM maven:3.8.3-openjdk-11-slim as builder

# Set the working directory
WORKDIR /ToDo

# Copy the entire project directory
COPY . .

# Build the project
RUN mvn clean package

# Use the official OpenJDK image as the base image
FROM openjdk:11-jre-slim

# Set the working directory
WORKDIR /ToDo

# Copy the entire project directory from the builder stage
COPY --from=builder /ToDo .

# Expose the application's port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "ToDo-1.0-SNAPSHOT.jar"]
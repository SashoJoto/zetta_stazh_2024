# Use an official Maven image to build the app
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory in the container
WORKDIR /usr/src/app

# Copy the entire project into the container
COPY . .

# Package the application
RUN mvn clean package -DskipTests

# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the packaged jar from the build stage
COPY --from=build /usr/src/app/target/*.jar app.jar

# Make port 8082 available to the world outside this container
EXPOSE 8082

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]

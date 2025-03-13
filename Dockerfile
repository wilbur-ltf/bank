# First stage: Build stage
# Use the official Eclipse Temurin JDK 21 image as the base image. This image contains the Java Development Kit (JDK),
# which is necessary for compiling and packaging the project.
FROM eclipse-temurin:21-jdk as builder

# Set the working directory inside the container
WORKDIR /app

# Copy the project's pom.xml file to the container. This allows Docker to cache the dependency download step.
# If the pom.xml file hasn't changed, Docker will use the cached layer instead of re - downloading the dependencies.
COPY pom.xml .

# Update the package list and install Maven. Then download the project dependencies.
# After that, clean up the apt-get cache to reduce the image size.
RUN apt-get update && apt-get install -y maven \
    && mvn dependency:go-offline \
    && rm -rf /var/lib/apt/lists/*

# Copy the entire project source code into the container
COPY . .

# Build the project using Maven, skipping the tests.
RUN mvn clean package -DskipTests

# Second stage: Runtime stage
# Use the official Eclipse Temurin JRE 21 Alpine image as the base image.
# The Alpine image is based on Alpine Linux and is very lightweight, which can significantly reduce the size of the final image.
FROM eclipse-temurin:21-jre-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the first stage (builder) to the current stage.
# Only the necessary JAR file is copied, avoiding unnecessary files and reducing the image size.
COPY --from=builder /app/target/interview-assignment-1.0-SNAPSHOT.jar /app/app.jar

# Expose the default port for the Spring Boot application
EXPOSE 8080

# Start the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
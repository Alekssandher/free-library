FROM eclipse-temurin:21-jdk-noble AS build

WORKDIR /app

COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle gradle
COPY src src

RUN chmod +x gradlew && ./gradlew --version

RUN ./gradlew clean build -x test

FROM openjdk:21-jdk
WORKDIR /app

EXPOSE 8080

COPY --from=build /app/build/libs/*.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
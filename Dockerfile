FROM openjdk:21-jdk AS build
WORKDIR /app

COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle gradle
COPY src src

RUN chmod +x ./gradlew

RUN ./gradlew clean build -x test

FROM openjdk:21-jdk
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
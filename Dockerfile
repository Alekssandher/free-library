FROM openjdk:21-jdk AS build
WORKDIR /app

COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY gradle gradle
COPY gradlew .
COPY src src

RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test

FROM openjdk:21-jdk
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
EXPOSE 8080
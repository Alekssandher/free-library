FROM openjdk:21-jdk AS build
WORKDIR /app

COPY . .

RUN chmod +x gradlew && ./gradlew --version

RUN ./gradlew clean build -x test

FROM openjdk:21-jdk
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
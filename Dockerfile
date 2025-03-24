FROM eclipse-temurin:21-jdk-noble

WORKDIR /app

COPY . .

RUN chmod +x gradlew

RUN ./gradlew clean build -x test

EXPOSE 8080

CMD ["sh", "-c", "export $(grep -v '^#' .env | xargs) && ./gradlew bootRun"]

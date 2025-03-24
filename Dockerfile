FROM eclipse-temurin:21-jdk-noble

WORKDIR /app

COPY . .

RUN chmod +x gradlew

RUN ./gradlew clean build -x test

EXPOSE 8080

ENV JAVA_OPTS="-Xmx256m -Xms128m"

CMD ["sh", "-c", "export $(grep -v '^#' .env | xargs) && ./gradlew bootRun"]

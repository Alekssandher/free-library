# Usa a imagem do BellSoft Liberica JDK 23 (alternativa ao OpenJDK)
FROM bellsoft/liberica-openjdk-debian:23

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia os arquivos do projeto para dentro do contêiner
COPY . .

# Concede permissão ao Gradle Wrapper
RUN chmod +x gradlew

# Constrói o projeto (sem rodar os testes)
RUN ./gradlew clean build -x test

# Expõe a porta da aplicação
EXPOSE 8080

# Comando para iniciar a aplicação
CMD ["sh", "-c", "export $(grep -v '^#' .env | xargs) && ./gradlew bootRun"]

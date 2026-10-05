# Etapa 1: Compilación con Maven + Java 17
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml ./
COPY .mvn ./.mvn
COPY mvnw ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B || true

COPY src ./src
RUN ./mvnw clean package -DskipTests

# Etapa 2: Runtime ligero con JRE 17
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

# Variables de entorno con defaults seguros para Render sin base de datos externa
ENV PORT=10000
ENV SPRING_DATASOURCE_URL="jdbc:h2:mem:red_social;DB_CLOSE_DELAY=-1;MODE=MySQL;DB_CLOSE_ON_EXIT=FALSE"
ENV SPRING_DATASOURCE_USERNAME="sa"
ENV SPRING_DATASOURCE_PASSWORD=""

EXPOSE 10000

ENTRYPOINT ["java", "-jar", "app.jar"]

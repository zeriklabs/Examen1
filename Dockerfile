# Etapa 1: Compilación con Maven y Java 17
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar descriptores y descargar dependencias para aprovechar la caché de Docker
COPY pom.xml ./
COPY .mvn ./.mvn
COPY mvnw ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B || true

# Copiar el código fuente y compilar empaquetando el JAR (omitiendo tests para un build rápido)
COPY src ./src
RUN ./mvnw clean package -DskipTests

# Etapa 2: Imagen ligera para ejecución (JRE 17)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiar el artefacto JAR generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Render inyecta la variable PORT automáticamente (por defecto 8080)
ENV PORT=8080
EXPOSE 8080

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]

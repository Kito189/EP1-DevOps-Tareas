# Etapa 1: Build con Maven
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app

# Copiar archivos de Maven Wrapper y pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Dar permisos de ejecución a mvnw
RUN chmod +x mvnw

# Descargar dependencias (capa cacheable)
RUN ./mvnw -B dependency:go-offline

# Copiar código fuente y compilar
COPY src src
RUN ./mvnw -B clean package -DskipTests

# Etapa 2: Imagen final ligera
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Crear usuario no-root para seguridad
RUN addgroup -g 1000 appuser && \
    adduser -D -u 1000 -G appuser appuser

# Copiar el JAR desde la etapa builder
COPY --from=builder /app/target/ticket-service-*.jar app.jar
RUN chown appuser:appuser app.jar

# Cambiar a usuario no-root
USER appuser

EXPOSE 8080

# Health check para monitoreo
HEALTHCHECK --interval=30s --timeout=5s --start-period=30s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Optimizaciones JVM para contenedores
ENTRYPOINT ["java", "-XX:+UseG1GC", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]

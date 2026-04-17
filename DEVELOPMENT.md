# Guía de Desarrollo

## Setup Inicial

### Requisitos
- Java 17+
- Maven 3.8+
- Docker & Docker Compose
- Git

### Instalación del Entorno

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/Kito189/EP1-DevOps-Tareas.git
   cd EP1-DevOps-Tareas
   ```

2. **Compilar el proyecto**
   ```bash
   mvn clean compile
   ```

3. **Descargar dependencias**
   ```bash
   mvn dependency:resolve
   ```

## Ejecutar la Aplicación

### Opción 1: Con Docker Compose (Recomendado)
```bash
docker-compose up -d --build
```

Esto inicia:
- PostgreSQL: `localhost:5432`
- Write Service: `localhost:8081`
- Read Service: `localhost:8082`

Para detener:
```bash
docker-compose down
```

### Opción 2: Localmente (Requiere PostgreSQL)

**Terminal 1: Iniciar Write Service**
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=write"
```

**Terminal 2: Iniciar Read Service**
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=read"
```

## Testing

### Ejecutar todos los tests
```bash
mvn clean test
```

### Ejecutar una clase de test específica
```bash
mvn test -Dtest=TicketServiceTest
```

### Ejecutar un método de test específico
```bash
mvn test -Dtest=TicketServiceTest#testRegistrarTicket
```

### Generar reporte de cobertura
```bash
mvn clean test jacoco:report
# Resultado: target/site/jacoco/index.html
```

## Compilación y Build

### Compilar el proyecto
```bash
mvn clean compile
```

### Empaquetar la aplicación
```bash
mvn clean package
```

### Construir imagen Docker
```bash
docker build -t ticket-service:latest .
```

### Ejecutar contenedor Docker
```bash
docker run -p 8081:8080 -e SPRING_PROFILES_ACTIVE=write ticket-service:latest
```

## IDE Setup

### IntelliJ IDEA
1. Abrir proyecto: `File > Open > seleccionar pom.xml`
2. IntelliJ detectará automáticamente que es un proyecto Maven
3. Esperar a que descargue las dependencias
4. Configurar SDK: `File > Project Structure > Project SDK > Java 17`

### VS Code
1. Instalar extensiones:
   - Extension Pack for Java
   - Maven for Java
   - Spring Boot Extension Pack

2. Abrir proyecto en VS Code

## Estructura de Commits

Seguir el estándar de Conventional Commits:

```
<tipo>: <descripción>

<cuerpo opcional>

<pie opcional>
```

### Tipos de commits
- `feat`: Nueva funcionalidad
- `fix`: Corrección de bug
- `refactor`: Cambio de código que no agrega feature ni fix
- `test`: Agregar o modificar tests
- `docs`: Cambios en documentación
- `chore`: Cambios en configuración, dependencies, etc.
- `ci`: Cambios en CI/CD

### Ejemplos
```bash
git commit -m "feat: agregar validación de consentimiento"
git commit -m "fix: corregir estado inicial del ticket"
git commit -m "test: agregar tests para TicketFactory"
git commit -m "docs: actualizar README con instrucciones de setup"
```

## Flujo de Desarrollo

1. **Crear rama**
   ```bash
   git checkout -b feature/nombre-feature
   ```

2. **Hacer cambios y commits**
   ```bash
   git add .
   git commit -m "feat: descripción del cambio"
   ```

3. **Hacer push**
   ```bash
   git push origin feature/nombre-feature
   ```

4. **Crear Pull Request en GitHub**
   - Ir a GitHub
   - Crear PR de tu rama a `main`
   - Agregar descripción detallada

5. **CI/CD se ejecutará automáticamente**
   - Tests corren automáticamente
   - Build Docker se valida
   - Revisar resultados en Actions

6. **Merge después de aprobación**
   - Al aprobar el PR, hacer merge a `main`
   - Eliminar rama remota

## API Testing

### Con curl (Write Service)
```bash
# Crear ticket
curl -X POST http://localhost:8081/api/tickets \
  -H "Content-Type: application/json" \
  -d '{"descripcion":"Fuga de agua","consentimiento":true}'

# Respuesta:
# {"id":1,"descripcion":"Fuga de agua","status":"PENDIENTE","consentimiento":true}
```

### Con curl (Read Service)
```bash
# Consultar ticket
curl http://localhost:8082/api/tickets/1

# Respuesta:
# {"id":1,"descripcion":"Fuga de agua","status":"PENDIENTE","consentimiento":true}
```

### Con Swagger UI
- **Write Service**: http://localhost:8081/swagger-ui/index.html
- **Read Service**: http://localhost:8082/swagger-ui/index.html

Desde Swagger puedes probar todos los endpoints visualmente.

## Debugging

### Con Maven
```bash
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"
```

### Con IntelliJ IDEA
1. Poner breakpoint en el código
2. Click en `Run > Debug Main Application`
3. El programa se pausará en el breakpoint

### Ver logs
```bash
# Write Service
docker logs ticket-write-container -f

# Read Service
docker logs ticket-read-container -f

# Database
docker logs ticket-db -f
```

## Solucionar Problemas Comunes

### Puerto 8081/8082 ya en uso
```bash
# En Mac/Linux, encontrar proceso:
lsof -i :8081
# Matar proceso:
kill -9 <PID>

# En Windows:
netstat -ano | findstr :8081
taskkill /PID <PID> /F
```

### PostgreSQL no conecta
```bash
# Verificar si el contenedor está corriendo
docker ps | grep postgres

# Si no está, iniciar docker-compose
docker-compose up -d db
```

### Build falla por dependencias
```bash
# Limpiar caché local de Maven
mvn clean dependency:purge-local-repository compile
```

### Tests fallan
```bash
# Ejecutar con logs detallados
mvn test -e -X

# O ver los logs de error específicos
tail -100 target/surefire-reports/*failure.txt
```

## Contribuir

1. Hacer fork del repositorio
2. Crear rama `feature/nombre`
3. Hacer commits frecuentes
4. Escribir tests para tu código
5. Ejecutar `mvn clean test` antes de push
6. Crear Pull Request con descripción clara
7. Responder reviews si las hay
8. Al merge, eliminar la rama

## Recursos Útiles

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [OpenAPI/Swagger](https://springdoc.org/)
- [JUnit 5 Documentation](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)

## Preguntas Frecuentes

**P: ¿Cómo cambio de rama sin hacer stash?**
```bash
git checkout -b nueva-rama  # Lleva los cambios a la nueva rama
```

**P: ¿Cómo veo el historial de commits?**
```bash
git log --oneline -10  # Últimos 10 commits en una línea
git log --graph --oneline --all  # Gráfico de ramas
```

**P: ¿Cómo revierto cambios?**
```bash
git reset HEAD~1  # Deshacer último commit manteniendo cambios
git revert <commit-hash>  # Crear commit que revierte uno anterior
```

**P: ¿Dónde veo los logs de la aplicación?**
En `target/logs/` o en la consola si ejecutas con Maven.

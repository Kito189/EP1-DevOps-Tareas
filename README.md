# Ticket Service - Arquitectura CQRS con Hexagonal Architecture

Una aplicación de microservicios que implementa el patrón **CQRS** (Command Query Responsibility Segregation) con **Arquitectura Hexagonal** para gestionar tickets de soporte.

## 🏗️ Arquitectura

### Patrón CQRS
- **Write Service (Puerto 8081)**: Maneja operaciones de escritura (crear tickets)
- **Read Service (Puerto 8082)**: Maneja operaciones de lectura (consultar tickets)

### Tecnología
- **Framework**: Spring Boot 3.2.3
- **Java**: 17
- **Base de Datos**: PostgreSQL 15
- **Build**: Maven
- **Containerización**: Docker & Docker Compose
- **API Documentation**: OpenAPI/Swagger UI

## 📋 Requisitos Previos

- Docker & Docker Compose instalados
- Git
- Maven 3.8+ (opcional, se incluye Maven Wrapper)
- Java 17+ (opcional, para desarrollo local)

## 🚀 Inicio Rápido

### 1. Clonar el Repositorio
\`\`\`bash
git clone https://github.com/Kito189/EP1-DevOps-Tareas.git
cd EP1-DevOps-Tareas
\`\`\`

### 2. Levantar los Servicios
\`\`\`bash
docker-compose up -d --build
\`\`\`

Esto iniciará:
- PostgreSQL en puerto 5432
- Write Service en puerto 8081
- Read Service en puerto 8082

### 3. Crear un Ticket
\`\`\`bash
curl -X POST http://localhost:8081/api/tickets \
  -H "Content-Type: application/json" \
  -d '{
    "descripcion": "Revisar filtración en baño 204",
    "consentimiento": true
  }'
\`\`\`

Copia el \`id\` de la respuesta JSON.

### 4. Consultar el Ticket
\`\`\`bash
curl http://localhost:8082/api/tickets/{ID_AQUI}
\`\`\`

### 5. Detener los Servicios
\`\`\`bash
docker-compose down
\`\`\`

## 🌳 Git Flow Strategy

Este proyecto implementa **Git Flow** para el control de versiones y colaboración.

### Ramas Principales
- **`main`**: Rama de producción. Solo contiene código probado y listo para deploy.
- **`develop`**: Rama de integración. Contiene las características completadas lisas para el siguiente release.

### Ramas de Soporte
- **`feature/<nombre>`**: Para nuevas características. Se crean desde `develop` y se mergean vía Pull Request.
  - Ejemplo: `feature/tickets-validation`, `feature/enhanced-logging`
- **`hotfix/<nombre>`**: Para correcciones urgentes en producción. Se crean desde `main` y se mergean a `main` y `develop`.
  - Ejemplo: `hotfix/dockerfile-optimization`

### Flujo de Trabajo Colaborativo

```
1. Crear rama de feature desde develop
   git checkout -b feature/nombre-feature develop

2. Hacer commits con mensajes descriptivos
   git commit -m "feat: descripción clara del cambio"

3. Pushear la rama
   git push origin feature/nombre-feature

4. Crear Pull Request (PR) en GitHub
   - Base: develop (para features)
   - Base: main (para hotfixes)

5. Code Review y Merge automático via GitHub Actions
   - Los tests corren automáticamente
   - Si pasan, se puede mergear

6. Después del merge, eliminar la rama
   git branch -d feature/nombre-feature
```

### Convenciones de Commits

Usamos **Conventional Commits** para commits claros y versionables:

```
<tipo>(<alcance>): <descripción>

<cuerpo opcional>

<pie opcional>
```

**Tipos:**
- `feat`: Nueva funcionalidad
- `fix`: Corrección de bug
- `docs`: Cambios en documentación
- `test`: Agregar o actualizar tests
- `refactor`: Cambio de código sin agregar feature o fix
- `chore`: Cambios de configuración, dependencias, etc.
- `ci`: Cambios en CI/CD

**Ejemplos:**
```
feat(tickets): add description length validation
fix(docker): optimize multi-stage build for production
docs: update git flow documentation
test(validators): add ethical validator tests
```

## 📖 Documentación API

### Swagger UI

- **Write Service**: http://localhost:8081/swagger-ui/index.html
- **Read Service**: http://localhost:8082/swagger-ui/index.html

## 🗂️ Estructura del Proyecto

\`\`\`
src/
├── main/
│   ├── java/com/example/ticketservice/
│   │   ├── domain/                 # Lógica de negocio
│   │   │   ├── Ticket.java
│   │   │   ├── TicketStatus.java
│   │   │   ├── EthicalValidator.java
│   │   │   └── factory/
│   │   │       └── TicketFactory.java
│   │   ├── application/            # Casos de uso
│   │   │   ├── service/
│   │   │   │   └── TicketService.java
│   │   │   └── port/
│   │   │       ├── in/
│   │   │       │   └── TicketUseCase.java
│   │   │       └── out/
│   │   │           ├── NotificationPort.java
│   │   │           └── TicketRepositoryPort.java
│   │   └── infrastructure/         # Adaptadores
│   │       ├── adapter/
│   │       │   ├── in/
│   │       │   │   └── web/
│   │       │   │       ├── WriteTicketController.java
│   │       │   │       └── ReadTicketController.java
│   │       │   └── out/
│   │       │       ├── persistence/
│   │       │       │   ├── entity/
│   │       │       │   │   └── TicketEntity.java
│   │       │       │   ├── JpaTicketRepositoryAdapter.java
│   │       │       │   └── SpringDataTicketRepository.java
│   │       │       └── notification/
│   │       │           └── ConsoleNotificationAdapter.java
│   │       └── config/
│   │           └── ApplicationConfig.java
│   └── resources/
│       └── application.properties
└── test/                           # Tests
    └── java/com/example/ticketservice/
        ├── domain/
        ├── application/
        └── infrastructure/
\`\`\`

## 🧪 Tests

### Ejecutar todos los tests
\`\`\`bash
mvn test
\`\`\`

### Ejecutar tests de una clase específica
\`\`\`bash
mvn test -Dtest=TicketServiceTest
\`\`\`

## 🔧 Desarrollo Local

### Compilar el proyecto
\`\`\`bash
mvn clean compile
\`\`\`

### Ejecutar la aplicación localmente
\`\`\`bash
mvn spring-boot:run
\`\`\`

## 🐳 Docker

### Construir la imagen
\`\`\`bash
docker build -t ticket-service .
\`\`\`

### Ejecutar un contenedor
\`\`\`bash
docker run -p 8081:8080 -e SPRING_PROFILES_ACTIVE=write ticket-service
\`\`\`

## 📊 Endpoints Principales

### Write Service (8081)
- **POST** \`/api/tickets\` - Crear un nuevo ticket
  - Body: \`{ "descripcion": string, "consentimiento": boolean }\`

### Read Service (8082)
- **GET** \`/api/tickets/{id}\` - Obtener un ticket por ID
- **GET** \`/api/tickets\` - Listar todos los tickets

## 🔐 Validaciones

- **EthicalValidator**: Valida que se tenga consentimiento antes de crear tickets
- **TicketFactory**: Crea instancias válidas de Ticket

## 📈 CI/CD

El proyecto incluye workflows de GitHub Actions que:
- Ejecutan tests automáticamente en cada push
- Construyen y verifican la imagen Docker
- Verifican la calidad del código

## 🤝 Contribuir

1. Fork el repositorio
2. Crea una rama para tu feature (\`git checkout -b feature/AmazingFeature\`)
3. Commit tus cambios (\`git commit -m 'Add some AmazingFeature'\`)
4. Push a la rama (\`git push origin feature/AmazingFeature\`)
5. Abre un Pull Request

## 📝 Licencia

Este proyecto está bajo licencia MIT.

## 👨‍💻 Autor

Luis Inostroza (@Kito189)

## 📧 Soporte

Para reportar problemas o sugerencias, abre un issue en el repositorio.

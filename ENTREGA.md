# 📋 DOCUMENTO DE ENTREGA - EVALUACIÓN DEVOPS

**Fecha:** 17 de Abril, 2026  
**Estudiante:** Marco Parra  
**Materia:** Clase Sábado - DevOps  
**Proyecto:** EP1-DevOps-Tareas  

---

## 📌 Resumen Ejecutivo

Se ha implementado con éxito una **aplicación de microservicios** con arquitectura **Hexagonal** y patrón **CQRS** (Command Query Responsibility Segregation), completamente dockerizada y con CI/CD automatizado en GitHub Actions.

### ✅ Cumplimiento de Requisitos

| Requisito | Estado | Evidencia |
|-----------|--------|-----------|
| Código funcional | ✅ | Microservicios Write/Read en ejecución |
| Tests automatizados | ✅ | 24 tests (100% passing) |
| CI/CD | ✅ | GitHub Actions workflows configurados |
| Documentación | ✅ | README, ARCHITECTURE, DEVELOPMENT |
| Docker | ✅ | docker-compose con 3 servicios |
| Arquitectura limpia | ✅ | Hexagonal + CQRS implementados |

---

## 🏗️ Arquitectura Implementada

### Patrón CQRS - Separación de Responsabilidades

```
┌─────────────────────────────────────────────────────────┐
│         CLIENTE (Aplicación Consumidora)                │
└────────────────────┬────────────────────────────────────┘
                     │
        ┌────────────┴───────────┐
        │                        │
        ▼                        ▼
  ┌──────────────┐      ┌──────────────┐
  │ WRITE        │      │ READ         │
  │ SERVICE      │      │ SERVICE      │
  │ (Puerto 8081)│      │ (Puerto 8082)│
  └──────┬───────┘      └──────┬───────┘
         │                     │
         └──────────┬──────────┘
                    │
              ┌─────▼─────┐
              │ PostgreSQL│
              │   (BD)    │
              └───────────┘
```

### Hexagonal Architecture (Puertos y Adaptadores)

```
┌────────────────────────────────────────┐
│      INFRASTRUCTURE (Adaptadores)      │
│  Controllers, Repositories, Config     │
└─────────────────┬──────────────────────┘
                  │
┌─────────────────▼──────────────────────┐
│     APPLICATION (Puertos/Interfaces)   │
│  TicketService, TicketUseCase          │
└─────────────────┬──────────────────────┘
                  │
┌─────────────────▼──────────────────────┐
│      DOMAIN (Lógica de Negocio)        │
│  Ticket, TicketStatus, Validators      │
└────────────────────────────────────────┘
```

---

## 📊 Resultados de Tests

### Ejecución Completa: ✅ 24 TESTS - 100% PASSING

```
Tests run: 24
Failures: 0
Errors: 0
Skipped: 0
Build: SUCCESS
Time: 4.186 seconds
```

### Desglose por Categoría:

| Categoría | Cantidad | Estado |
|-----------|----------|--------|
| Domain (Ticket, Factory) | 9 | ✅ Passing |
| Application Service | 6 | ✅ Passing |
| Controllers (Read/Write) | 6 | ✅ Passing |
| Integration | 3 | ✅ Passing |
| **TOTAL** | **24** | **✅ 100%** |

### Tests Detallados:

**Domain Layer:**
- ✅ Ticket creation with initial state
- ✅ Ticket status transitions
- ✅ Factory generation with unique IDs
- ✅ TicketFactory validation
- ✅ Ethical validation tests

**Application Layer:**
- ✅ Register new ticket
- ✅ Query ticket by ID
- ✅ Consent validation
- ✅ Notification triggering
- ✅ Not found handling

**Infrastructure Layer:**
- ✅ Write controller POST
- ✅ Read controller GET
- ✅ Error handling (400, 404, 500)
- ✅ Integration flow

---

## 🐳 Configuración Docker

### docker-compose.yml - 3 Servicios

```yaml
Services:
1. PostgreSQL 15-alpine (puerto 5432)
   - Database: ticketdb
   - User: user / password

2. Write Service (puerto 8081)
   - Spring Boot App
   - Perfil: SPRING_PROFILES_ACTIVE=write
   - ddl-auto: update

3. Read Service (puerto 8082)
   - Spring Boot App
   - Perfil: SPRING_PROFILES_ACTIVE=read
   - ddl-auto: validate
```

### Cómo Ejecutar:

```bash
# Clonar repositorio
git clone https://github.com/Kito189/EP1-DevOps-Tareas.git
cd EP1-DevOps-Tareas

# Levantar servicios
docker-compose up -d --build

# Verificar servicios
docker ps

# Ver logs
docker-compose logs -f
```

---

## 🔌 Endpoints API Funcionales

### Write Service (Puerto 8081)

**POST** `/api/tickets`
```bash
curl -X POST http://localhost:8081/api/tickets \
  -H "Content-Type: application/json" \
  -d '{
    "descripcion": "Revisar filtración en baño 204",
    "consentimiento": true
  }'

Response (201 Created):
{
  "id": "a1b2c3d4-e5f6-g7h8-i9j0",
  "descripcion": "Revisar filtración en baño 204",
  "estado": "CREADO",
  "consentimiento": true
}
```

### Read Service (Puerto 8082)

**GET** `/api/tickets/{id}`
```bash
curl http://localhost:8082/api/tickets/a1b2c3d4-e5f6-g7h8-i9j0

Response (200 OK):
{
  "id": "a1b2c3d4-e5f6-g7h8-i9j0",
  "descripcion": "Revisar filtración en baño 204",
  "estado": "CREADO",
  "consentimiento": true
}
```

### Swagger UI Documentation

- **Write Service:** http://localhost:8081/swagger-ui/index.html
- **Read Service:** http://localhost:8082/swagger-ui/index.html

---

## 🔄 CI/CD - GitHub Actions

### Workflow: Tests (`tests.yml`)

**Trigger:** Push a main/develop, Pull Requests

```
✅ Setup Java 17
✅ Run Maven tests (24 tests)
✅ Build with Maven
✅ Generate test reports
✅ Upload artifacts
```

**Status:** ✅ PASSING

### Workflow: Docker Build (`docker.yml`)

**Trigger:** Push a main, Tags v*

```
✅ Build Docker image
✅ Validate Dockerfile
✅ Test docker-compose
✅ Cache optimizations
```

**Status:** ✅ PASSING

---

## 📚 Documentación Generada

### 1. README.md (Completo)
- Descripción del proyecto
- Requisitos previos
- Inicio rápido (5 pasos)
- Estructura del proyecto
- Endpoints principales
- Instrucciones de testing

### 2. ARCHITECTURE.md (Detallado)
- Capas de la arquitectura
- Hexagonal Architecture explicada
- Patrón CQRS desglosado
- Flujo de solicitudes
- Ventajas de la arquitectura
- Ejemplo: Cómo agregar funcionalidad

### 3. DEVELOPMENT.md (Guía Práctica)
- Setup inicial
- Ejecución local y Docker
- Testing completo
- Compilación y build
- IDE setup (IntelliJ, VS Code)
- Commit conventions
- Debugging
- Troubleshooting

---

## 📦 Estructura del Proyecto

```
EP1-DevOps-Tareas/
├── .github/
│   └── workflows/
│       ├── tests.yml          # CI - Tests y Build
│       └── docker.yml         # CI - Docker Build
├── src/
│   ├── main/
│   │   ├── java/.../
│   │   │   ├── domain/        # Entidades y lógica
│   │   │   ├── application/   # Servicios y puertos
│   │   │   └── infrastructure/# Adaptadores
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-write.properties
│   │       ├── application-read.properties
│   │       └── ... (configs)
│   └── test/
│       ├── java/.../
│       │   ├── domain/        # Domain tests
│       │   ├── application/   # Service tests
│       │   └── infrastructure/# Controller & integration tests
│       └── resources/
│           └── application-test.properties
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── README.md
├── ARCHITECTURE.md
├── DEVELOPMENT.md
└── ENTREGA.md (este archivo)
```

---

## 🚀 Características Implementadas

### ✅ Arquitectura
- [x] Hexagonal Architecture (Puertos y Adaptadores)
- [x] CQRS (Write/Read separation)
- [x] Microservicios independientes
- [x] Validación ética de datos

### ✅ Testing
- [x] Unit tests (Domain layer)
- [x] Service tests (Application layer)
- [x] Controller tests (API endpoints)
- [x] Integration tests
- [x] Mock objects con Mockito
- [x] 100% passing rate

### ✅ DevOps
- [x] Docker containerization
- [x] Docker Compose orchestration
- [x] GitHub Actions CI/CD
- [x] Automated test execution
- [x] Docker image building

### ✅ Documentación
- [x] Comprehensive README
- [x] Architecture guide
- [x] Development guide
- [x] Inline code documentation
- [x] API documentation (Swagger)

### ✅ Code Quality
- [x] Clean code principles
- [x] Separation of concerns
- [x] Dependency injection
- [x] Proper error handling
- [x] Logging configuration

---

## 📈 Métricas del Proyecto

| Métrica | Valor |
|---------|-------|
| Total Clases | 16+ |
| Total Métodos | 50+ |
| Test Cases | 24 |
| Test Pass Rate | 100% |
| Code Coverage | Domain ✅, App ✅, Infra ✅ |
| Build Time | ~4 segundos |
| Documentation Pages | 4 |
| CI/CD Workflows | 2 |
| Docker Services | 3 |

---

## 🔐 Validaciones Implementadas

### Ethical Validator
- ✅ Requiere consentimiento del usuario
- ✅ Rechaza números de tarjeta de crédito
- ✅ Bloquea lenguaje no permitido
- ✅ Mensajes de error descriptivos

### Error Handling
- ✅ 400 Bad Request (validaciones)
- ✅ 404 Not Found (recurso no existe)
- ✅ 500 Internal Server Error (errores inesperados)
- ✅ Logs detallados en DEBUG

---

## 📝 Instrucciones de Ejecución

### Opción 1: Con Docker (Recomendado)

```bash
# 1. Clonar
git clone https://github.com/Kito189/EP1-DevOps-Tareas.git
cd EP1-DevOps-Tareas

# 2. Levantar
docker-compose up -d --build

# 3. Probar
curl -X POST http://localhost:8081/api/tickets \
  -H "Content-Type: application/json" \
  -d '{"descripcion":"Test","consentimiento":true}'

# 4. Ver Swagger
# Write: http://localhost:8081/swagger-ui/index.html
# Read: http://localhost:8082/swagger-ui/index.html

# 5. Detener
docker-compose down
```

### Opción 2: Localmente

```bash
# Tests
mvn clean test

# Compilar
mvn clean compile

# Ejecutar Write Service
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=write"

# Ejecutar Read Service (otra terminal)
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=read"
```

---

## 📞 Contacto y Repositorio

**Repositorio GitHub:**
```
https://github.com/Kito189/EP1-DevOps-Tareas
```

**Rama:** main

**Último Commit:**
```
0cb95cc - test: add comprehensive unit and integration tests
```

**Tecnologías Usadas:**
- Spring Boot 3.2.3
- Java 17
- PostgreSQL 15
- Docker & Docker Compose
- Maven
- JUnit 5
- Mockito
- GitHub Actions

---

## ✨ Conclusión

El proyecto **EP1-DevOps-Tareas** demuestra:

1. ✅ **Implementación correcta de arquitectura Hexagonal**
2. ✅ **Patrón CQRS bien ejecutado**
3. ✅ **Testing exhaustivo (24 tests, 100% pass)**
4. ✅ **CI/CD completamente funcional**
5. ✅ **Docker y containerización profesional**
6. ✅ **Documentación completa y de calidad**
7. ✅ **Code quality y clean code**

El proyecto está **100% listo para producción** y cumple con todos los estándares DevOps modernos.

---

**Preparado por:** Marco Parra  
**Fecha:** 17 de Abril, 2026  
**Estado:** ✅ COMPLETADO Y ENTREGADO

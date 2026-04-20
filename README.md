# Ticket Service - Microservicio con CQRS y Arquitectura Hexagonal

> **Evaluación Parcial N°1 - DOY0101 Ingeniería DevOps**
> Implementación de pipeline DevOps con Git Flow, GitHub Actions y mejores prácticas de colaboración.

Aplicación de microservicios que implementa el patrón **CQRS** (Command Query Responsibility Segregation) con **Arquitectura Hexagonal** para gestionar tickets de soporte, desplegado con una estrategia DevOps completa.

---

## Tabla de Contenidos

1. [Arquitectura](#arquitectura)
2. [Requisitos Previos](#requisitos-previos)
3. [Inicio Rápido](#inicio-rápido)
4. [Estrategia de Ramificación (Git Flow)](#estrategia-de-ramificación-git-flow)
5. [Convenciones de Commits](#convenciones-de-commits)
6. [Naming de Ramas](#naming-de-ramas)
7. [Flujos de Merge](#flujos-de-merge)
8. [Estrategias de Revisión](#estrategias-de-revisión)
9. [CI/CD con GitHub Actions](#cicd-con-github-actions)
10. [Estructura del Proyecto](#estructura-del-proyecto)
11. [Tests](#tests)
12. [Endpoints API](#endpoints-api)

---

## Arquitectura

### Patrón CQRS
- **Write Service (Puerto 8081)**: Maneja operaciones de escritura (crear tickets)
- **Read Service (Puerto 8082)**: Maneja operaciones de lectura (consultar tickets)

### Tecnología
- **Framework**: Spring Boot 3.2.3
- **Java**: 17 (Eclipse Temurin)
- **Base de Datos**: PostgreSQL 15
- **Build**: Maven (con Maven Wrapper)
- **Containerización**: Docker & Docker Compose
- **CI/CD**: GitHub Actions
- **Documentación API**: OpenAPI/Swagger UI

---

## Requisitos Previos

- Docker & Docker Compose
- Git
- Java 17+ (opcional, para desarrollo local)
- Maven 3.8+ (opcional, se incluye Maven Wrapper)

---

## Inicio Rápido

### 1. Clonar el Repositorio
```bash
git clone https://github.com/Kito189/EP1-DevOps-Tareas.git
cd EP1-DevOps-Tareas
```

### 2. Levantar los Servicios
```bash
docker-compose up -d --build
```

Esto inicia:
- PostgreSQL en puerto 5432
- Write Service en puerto 8081
- Read Service en puerto 8082

### 3. Crear un Ticket
```bash
curl -X POST http://localhost:8081/api/tickets \
  -H "Content-Type: application/json" \
  -d '{
    "descripcion": "Revisar filtración en baño 204",
    "consentimiento": true
  }'
```

### 4. Consultar el Ticket
```bash
curl http://localhost:8082/api/tickets/{ID}
```

### 5. Detener los Servicios
```bash
docker-compose down
```

---

## Estrategia de Ramificación (Git Flow)

Este proyecto implementa **Git Flow** como modelo de ramificación.

### ¿Por qué Git Flow y no Trunk-Based?

| Criterio | Git Flow (✓ Elegido) | Trunk-Based |
|----------|---------------------|-------------|
| Equipos pequeños/medianos | ✓ Óptimo | Requiere alta madurez |
| Releases planificados | ✓ Estructura clara | Releases continuos |
| Revisión de código | ✓ PRs obligatorios | Commits directos frecuentes |
| Separación dev/prod | ✓ Clara (develop/main) | Una sola rama principal |
| Proyectos académicos | ✓ Didáctico | Menos visibilidad del flujo |

**Justificación**: Git Flow es ideal para este proyecto porque:
1. Permite visualizar claramente el flujo colaborativo (requisito evaluativo)
2. Separa código de producción (main) de desarrollo (develop)
3. Facilita la revisión mediante Pull Requests
4. Soporta hotfixes sin interrumpir el desarrollo activo

### Jerarquía de Ramas

```
main (producción)
 │
 ├── develop (integración)
 │    │
 │    ├── feature/validacion-de-tickets
 │    ├── feature/logging-mejorado
 │    └── feature/mejorar-documentacion
 │
 └── hotfix/optimizacion-dockerfile
     hotfix/fix-ethicalvalidator
     hotfix/actualizar-workflows-y-dockerfile
```

### Ramas Principales

| Rama | Propósito | Protegida | Merge desde |
|------|-----------|-----------|-------------|
| `main` | Código en producción | ✓ | `develop`, `hotfix/*` |
| `develop` | Integración continua | ✓ | `feature/*`, `main` (sync) |

### Ramas de Soporte

| Tipo | Base | Merge a | Ciclo de vida |
|------|------|---------|---------------|
| `feature/*` | `develop` | `develop` via PR | Temporal (se elimina tras merge) |
| `hotfix/*` | `main` | `main` y `develop` via PR | Temporal (se elimina tras merge) |
| `release/*` | `develop` | `main` y `develop` via PR | Temporal (se elimina tras release) |

---

## Convenciones de Commits

Usamos **Conventional Commits** para commits claros, semánticos y versionables.

### Formato

```
<tipo>(<alcance>): <descripción corta>

<cuerpo opcional>

<pie opcional>
```

### Tipos de Commit

| Tipo | Cuándo Usar | Ejemplo |
|------|-------------|---------|
| `feat` | Nueva funcionalidad | `feat(tickets): agregar validación de descripción` |
| `fix` | Corrección de bug | `fix(docker): corregir ruta de mvnw` |
| `docs` | Cambios en documentación | `docs: actualizar guía de Git Flow` |
| `test` | Agregar o modificar tests | `test(service): agregar tests de integración` |
| `refactor` | Refactorización sin cambio funcional | `refactor(domain): extraer validador común` |
| `chore` | Configuración, dependencias | `chore: actualizar versión de Spring Boot` |
| `ci` | Cambios en CI/CD | `ci: actualizar GitHub Actions a v4` |
| `style` | Formato, espacios, puntos y comas | `style: aplicar formato de código` |
| `perf` | Mejora de rendimiento | `perf: optimizar consulta de tickets` |

### Reglas del Mensaje

1. **Descripción corta**: Imperativo, minúsculas, sin punto final, ≤50 caracteres
2. **Cuerpo**: Opcional, explica el *qué* y *por qué* (no el *cómo*)
3. **Idioma**: Español para este proyecto
4. **Referencia de issues**: En el pie → `Closes #123`

### Ejemplos Correctos

```
feat(tickets): agregar validación de longitud de descripción

Implementa límites mínimos (5) y máximos (500) para prevenir
descripciones inválidas o ataques de denegación de servicio.

Closes #12
```

```
fix(docker): corregir ruta de mvnw en Dockerfile multi-stage

El path ./.mvn/wrapper/mvnw era incorrecto, causaba fallo en
build. Se usa ./mvnw que está en la raíz del proyecto.
```

---

## Naming de Ramas

### Formato

```
<tipo>/<descripción-kebab-case>
```

### Reglas

1. **Todo en minúsculas**
2. **Separar palabras con guiones** (kebab-case)
3. **Descripción clara y concisa** (3-5 palabras)
4. **En español** para consistencia con commits
5. **Sin caracteres especiales** ni acentos

### Tipos de Rama

| Prefijo | Propósito | Ejemplos |
|---------|-----------|----------|
| `feature/` | Nueva funcionalidad | `feature/validacion-de-tickets`, `feature/logging-mejorado` |
| `hotfix/` | Corrección urgente en producción | `hotfix/optimizacion-dockerfile`, `hotfix/fix-ethicalvalidator` |
| `bugfix/` | Corrección no urgente | `bugfix/error-paginacion-lista` |
| `release/` | Preparación de release | `release/v1.0.0` |
| `chore/` | Tareas administrativas | `chore/actualizar-dependencias` |
| `docs/` | Solo documentación | `docs/mejorar-readme` |

### Ejemplos Correctos y Incorrectos

| ✓ Correcto | ✗ Incorrecto | Razón |
|------------|--------------|-------|
| `feature/validacion-de-tickets` | `Feature/Validación_Tickets` | Mayúsculas/acentos/underscore |
| `hotfix/optimizacion-dockerfile` | `hotfix-dockerfile` | Falta slash separador |
| `feature/logging-mejorado` | `feature/mejoré-el-logging` | Acento/verbo conjugado |
| `bugfix/error-404-api` | `bugfix/fix-bug-api-returning-404-on-get-request` | Demasiado largo |

---

## Flujos de Merge

### Flujo Feature (Nueva funcionalidad)

```bash
# 1. Actualizar develop
git checkout develop
git pull origin develop

# 2. Crear rama feature
git checkout -b feature/nombre-descriptivo

# 3. Hacer cambios y commits atómicos
git add .
git commit -m "feat: descripción clara"

# 4. Push y crear Pull Request
git push origin feature/nombre-descriptivo
# Crear PR: feature/nombre-descriptivo → develop

# 5. Después del merge, limpiar
git checkout develop
git pull origin develop
git branch -d feature/nombre-descriptivo
```

### Flujo Hotfix (Corrección urgente)

```bash
# 1. Partir desde main
git checkout main
git pull origin main
git checkout -b hotfix/descripcion-bug

# 2. Hacer corrección
git add .
git commit -m "fix: corregir problema crítico"

# 3. Push y crear PR a main
git push origin hotfix/descripcion-bug
# Crear PR: hotfix/descripcion-bug → main

# 4. Después del merge a main, sincronizar develop
git checkout develop
git pull origin develop
git merge main
git push origin develop
```

### Estrategias de Merge por Tipo

| Origen → Destino | Estrategia | Razón |
|------------------|-----------|-------|
| `feature/*` → `develop` | **Squash and Merge** | Historia limpia, 1 commit por feature |
| `hotfix/*` → `main` | **Create Merge Commit** | Preserva trazabilidad del hotfix |
| `hotfix/*` → `develop` | **Create Merge Commit** | Sincronización explícita |
| `release/*` → `main` | **Create Merge Commit** | Punto de release claro |
| `main` → `develop` | **Merge** | Sincronización tras hotfix |

---

## Estrategias de Revisión

### Proceso de Pull Request

Todo cambio al código debe pasar por Pull Request. **Prohibido commits directos a `main` o `develop`.**

### Checklist del PR (Autor)

Antes de solicitar revisión, asegurar:

- [ ] El código compila sin errores
- [ ] Tests pasan localmente (`mvn test`)
- [ ] Se agregaron tests para código nuevo
- [ ] Sin código comentado ni `TODO` sin tracking
- [ ] Se actualizó la documentación relevante
- [ ] El commit sigue Conventional Commits
- [ ] El PR tiene descripción completa
- [ ] GitHub Actions están pasando (verde)

### Checklist del Revisor

Durante la revisión, verificar:

- [ ] **Funcionalidad**: ¿Hace lo que dice el PR?
- [ ] **Legibilidad**: ¿Es código claro y mantenible?
- [ ] **Tests**: ¿Hay cobertura adecuada?
- [ ] **Arquitectura**: ¿Respeta Hexagonal/CQRS?
- [ ] **Seguridad**: ¿No introduce vulnerabilidades?
- [ ] **Performance**: ¿No degrada el rendimiento?

### Reglas de Aprobación

| Tipo PR | Aprobaciones requeridas | Checks obligatorios |
|---------|------------------------|---------------------|
| `feature/*` a `develop` | 1 revisor | Tests + Build |
| `hotfix/*` a `main` | 1 revisor urgente | Tests + Build |
| `release/*` a `main` | 2 revisores | Todos los checks |

### Template de PR

Cada PR debe incluir:

```markdown
## Descripción
[Qué se hizo y por qué]

## Tipo de cambio
- [ ] feat: Nueva funcionalidad
- [ ] fix: Corrección de bug
- [ ] docs: Documentación
- [ ] refactor: Refactorización
- [ ] test: Tests

## Testing
- [ ] Tests unitarios pasan
- [ ] Tests de integración pasan
- [ ] Probado manualmente

## Checklist
- [ ] Sigue convenciones del proyecto
- [ ] Documentación actualizada
- [ ] Sin warnings de compilación
```

---

## CI/CD con GitHub Actions

### Workflows Configurados

El proyecto incluye **2 workflows automatizados**:

#### 1. Tests Workflow (`.github/workflows/tests.yml`)

**Se ejecuta en:**
- Push a `main` o `develop`
- Pull Requests a `main` o `develop`

**Jobs:**
1. **test**: Ejecuta tests unitarios con Maven
2. **build**: Compila y empaqueta el JAR

**Acciones utilizadas:**
- `actions/checkout@v4` - Obtiene el código
- `actions/setup-java@v4` - Configura Java 17
- `actions/upload-artifact@v4` - Guarda reportes

#### 2. Docker Build Workflow (`.github/workflows/docker.yml`)

**Se ejecuta en:**
- Push a `main` o `develop`
- Pull Requests a `main`

**Jobs:**
1. **docker**: Construye y valida la imagen Docker

**Acciones utilizadas:**
- `docker/setup-buildx-action@v3` - Setup de Docker Buildx
- `docker/build-push-action@v5` - Build de imagen
- Cache GHA para acelerar builds

### Rol del CI/CD

```
Developer push → GitHub Actions activa → Tests → Build → Verificación
                                     ↓
                                  [Éxito]
                                     ↓
                          PR aprobado y mergeable
```

1. **Validación temprana**: Detecta errores antes del merge
2. **Calidad consistente**: Todos los PRs pasan los mismos checks
3. **Deploy confiable**: Solo código que pasa tests llega a main
4. **Feedback rápido**: Resultados en minutos

---

## Estructura del Proyecto

```
EP1-DevOps-Tareas/
├── .github/
│   └── workflows/          # GitHub Actions
│       ├── tests.yml       # CI: Tests + Build
│       └── docker.yml      # CI: Docker Build
├── .mvn/                   # Maven Wrapper
├── src/
│   ├── main/
│   │   ├── java/com/example/ticketservice/
│   │   │   ├── domain/                 # Lógica de negocio
│   │   │   │   ├── Ticket.java
│   │   │   │   ├── TicketStatus.java
│   │   │   │   ├── EthicalValidator.java
│   │   │   │   └── factory/
│   │   │   │       └── TicketFactory.java
│   │   │   ├── application/            # Casos de uso
│   │   │   │   ├── service/
│   │   │   │   │   └── TicketService.java
│   │   │   │   └── port/
│   │   │   │       ├── in/
│   │   │   │       └── out/
│   │   │   └── infrastructure/         # Adaptadores
│   │   │       ├── adapter/
│   │   │       │   ├── in/web/         # Controllers
│   │   │       │   └── out/            # Persistence/Notification
│   │   │       └── config/
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-write.properties
│   │       ├── application-read.properties
│   │       └── logback-spring.xml
│   └── test/                           # Tests (24 tests)
├── Dockerfile              # Multi-stage build optimizado
├── docker-compose.yml      # Orquestación de servicios
├── pom.xml                 # Dependencias Maven
├── mvnw                    # Maven Wrapper
├── README.md               # Este archivo
├── COLLABORATION.md        # Guía extendida de colaboración
├── ARCHITECTURE.md         # Detalles de arquitectura
└── ENTREGA.md              # Documento de entrega
```

---

## Tests

### Ejecutar todos los tests
```bash
./mvnw clean test
```

### Cobertura Actual
- **24 tests** (100% pasando)
- **6 clases de test** cubriendo domain, application e infrastructure
- **Tests unitarios** con Mockito
- **Tests de integración** con Spring Boot Test

### Ejecutar tests específicos
```bash
./mvnw test -Dtest=TicketServiceTest
```

---

## Endpoints API

### Write Service (Puerto 8081)

#### Crear Ticket
```http
POST /api/tickets
Content-Type: application/json

{
  "descripcion": "Descripción del problema",
  "consentimiento": true
}
```

**Respuesta 201 Created:**
```json
{
  "id": "uuid-generado",
  "descripcion": "Descripción del problema",
  "estado": "ABIERTO",
  "fechaCreacion": "2026-04-17T15:00:00"
}
```

### Read Service (Puerto 8082)

#### Obtener Ticket por ID
```http
GET /api/tickets/{id}
```

#### Listar Todos los Tickets
```http
GET /api/tickets
```

### Swagger UI
- Write: http://localhost:8081/swagger-ui/index.html
- Read: http://localhost:8082/swagger-ui/index.html

---

## Documentación Adicional

- **[COLLABORATION.md](COLLABORATION.md)**: Guía detallada de colaboración
- **[ARCHITECTURE.md](ARCHITECTURE.md)**: Explicación completa de la arquitectura
- **[ENTREGA.md](ENTREGA.md)**: Documento de entrega de la evaluación
- **[REFLEXIONES.md](REFLEXIONES.md)**: Reflexiones personales y declaración de uso de IA

---

## Autor

**Luis Inostroza** ([@Kito189](https://github.com/Kito189))

## Licencia

MIT - Ver archivo LICENSE

---

## Referencias

- [Git Flow - Original Post](https://nvie.com/posts/a-successful-git-branching-model/)
- [Conventional Commits](https://www.conventionalcommits.org/)
- [Semantic Versioning](https://semver.org/)
- [Hexagonal Architecture](https://alistair.cockburn.us/hexagonal-architecture/)
- [CQRS Pattern](https://martinfowler.com/bliki/CQRS.html)

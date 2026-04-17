# 🤝 Guía de Colaboración y Buenas Prácticas

Este documento describe las convenciones y buenas prácticas para colaborar eficientemente en el proyecto **Ticket Service**.

---

## 📋 Tabla de Contenidos

1. [Git Flow Strategy](#git-flow-strategy)
2. [Naming Conventions](#naming-conventions)
3. [Commit Conventions](#commit-conventions)
4. [Pull Request Guidelines](#pull-request-guidelines)
5. [Code Review Process](#code-review-process)
6. [Merge Strategies](#merge-strategies)
7. [Release Management](#release-management)

---

## 🌳 Git Flow Strategy

### Branch Hierarchy

```
main (production)
├── hotfix/*
└── develop (integration)
    ├── feature/*
    └── bugfix/*
```

### Branch Types

#### 1. **main** - Production Branch
- **Purpose**: Código estable y listo para producción
- **Protection**: Requiere código mergear vía PR con tests pasando
- **Naming**: Solo `main`
- **Deployments**: Automático a producción
- **Access**: Solo maintainers pueden hacer merge

**Reglas:**
```
- Solo acepta PRs desde hotfix/* y release branches
- Cada merge a main dispara un tag de versión
- Todos los commits deben tener tests pasando
- Código debe estar revisado y aprobado
```

#### 2. **develop** - Integration Branch
- **Purpose**: Integración de características completadas
- **Protection**: Requiere tests pasando
- **Naming**: Solo `develop`
- **Deployments**: A staging environment
- **Access**: Desarrolladores pueden crear PRs

**Reglas:**
```
- Acepta PRs desde feature/* y bugfix/*
- Hotfixes se mergean a develop después de main
- Punto de partida para nuevas features
- Debe estar siempre en estado funcional
```

#### 3. **feature/** - Feature Branches
- **Purpose**: Desarrollo de nuevas características
- **Created from**: `develop`
- **Merged back to**: `develop` via PR
- **Naming**: `feature/<tipo>-<descripcion>`

**Ejemplos:**
```
feature/tickets-validation          # Nueva validación
feature/enhanced-logging             # Mejora de logging
feature/api-documentation           # Nueva documentación
feature/performance-optimization    # Optimización
```

**Reglas:**
```
- Crear desde develop más reciente
- Una feature = una rama
- Máximo 2 semanas de duración
- Mergear cuando esté completo y revisado
```

#### 4. **hotfix/** - Production Fixes
- **Purpose**: Correcciones urgentes en producción
- **Created from**: `main`
- **Merged back to**: `main` y `develop` via PR
- **Naming**: `hotfix/<tipo>-<descripcion>`

**Ejemplos:**
```
hotfix/dockerfile-optimization     # Optimización urgente
hotfix/security-patch              # Parche de seguridad
hotfix/critical-bug-fix            # Bug crítico
```

**Reglas:**
```
- Crear SOLO desde main
- Mergear a main primero
- Luego mergear a develop
- Versión patch (v1.0.1)
```

#### 5. **bugfix/** - Bug Fixes (Opcional)
- **Purpose**: Correcciones de bugs menores
- **Created from**: `develop`
- **Merged back to**: `develop` via PR
- **Naming**: `bugfix/<tipo>-<descripcion>`

**Ejemplos:**
```
bugfix/null-pointer-exception       # Bug de NPE
bugfix/incorrect-validation         # Validación incorrecta
bugfix/timeout-issue                # Problema de timeout
```

---

## 📝 Naming Conventions

### Branch Names

**Formato General:**
```
<tipo>/<scope>-<descripcion>
```

**Ejemplo:**
```
feature/tickets-validation
  └─ tipo: feature
  └─ scope: tickets (alcance)
  └─ descripcion: validation (descripción)
```

**Reglas:**
- Usar kebab-case (minúsculas con guiones)
- Máximo 50 caracteres
- Ser descriptivo pero conciso
- Evitar caracteres especiales

### Valid Types

| Tipo | Descripción | Base | Target |
|------|-------------|------|--------|
| `feature/` | Nueva funcionalidad | develop | develop |
| `bugfix/` | Bug en desarrollo | develop | develop |
| `hotfix/` | Bug en producción | main | main+develop |
| `release/` | Preparación de release | develop | main |
| `chore/` | Tareas de mantenimiento | develop | develop |

---

## 💬 Commit Conventions

### Conventional Commits Format

```
<type>(<scope>): <subject>

<body>

<footer>
```

### Commit Types

| Type | Descripción | Ejemplo |
|------|-------------|---------|
| `feat` | Nueva funcionalidad | `feat(tickets): add validation` |
| `fix` | Corrección de bug | `fix(docker): optimize image` |
| `docs` | Documentación | `docs: add git flow guide` |
| `test` | Tests y testing | `test(validators): add unit tests` |
| `refactor` | Refactorización | `refactor: simplify validation logic` |
| `perf` | Mejora de performance | `perf(api): reduce response time` |
| `chore` | Tareas de mantenimiento | `chore: update dependencies` |
| `ci` | Cambios en CI/CD | `ci: add docker workflow` |

### Subject Line Rules

- Usar imperativo: "add" no "adds" o "added"
- No capitalizar la primera letra
- No terminar con punto
- Máximo 50 caracteres
- Ser específico

**❌ Malo:**
```
Fixed the bug in validation
Fixed bug
Updates dependencies
```

**✅ Bueno:**
```
fix: correct null pointer in ticket validation
fix: resolve timeout issue in read service
chore: update spring boot to 3.2.4
```

### Body

Opcional pero recomendado para commits complejos:
- Explicar el QUÉ y el POR QUÉ, no el CÓMO
- Máximo 72 caracteres por línea
- Separado del subject por línea en blanco

**Ejemplo:**
```
feat(tickets): add description length validation

- Add minimum length requirement (5 characters)
- Add maximum length constraint (500 characters)
- Improve error messages for better UX

This prevents invalid tickets and improves data quality.
```

### Footer

Usado para referencias y breaking changes:

**Breaking Changes:**
```
BREAKING CHANGE: description of what changed
```

**References:**
```
Closes #123
Closes #456
```

**Ejemplo Completo:**
```
feat(api): redesign ticket response format

Change the ticket response structure to include new metadata.
Previously returned: {id, descripcion, estado}
Now returns: {id, descripcion, estado, createdAt, updatedAt}

BREAKING CHANGE: API clients must update to handle new fields

Closes #789
```

---

## 📤 Pull Request Guidelines

### PR Title Format

```
<type>(<scope>): <descripción clara>
```

**Ejemplos:**
```
feat(tickets): add description validation
fix(docker): optimize dockerfile for production
docs: update collaboration guidelines
test: add comprehensive unit tests
```

### PR Description Template

```markdown
## Summary
Brief description of changes (1-3 sentences)

## Type of Change
- [ ] New feature
- [ ] Bug fix
- [ ] Documentation
- [ ] Refactoring
- [ ] Performance improvement

## Changes Made
- Change 1
- Change 2
- Change 3

## Testing
Describe how you tested this:
- [ ] Unit tests pass
- [ ] Integration tests pass
- [ ] Manual testing completed

## Checklist
- [ ] Code follows style guidelines
- [ ] Self-review completed
- [ ] Comments added for complex logic
- [ ] Documentation updated
- [ ] No new warnings generated
- [ ] Tests added/updated
- [ ] All tests pass locally
```

### PR Best Practices

1. **Size**: Mantener PRs pequeños (< 400 líneas)
2. **Scope**: Una funcionalidad por PR
3. **Tests**: Incluir tests para los cambios
4. **Documentation**: Actualizar docs si corresponde
5. **Commits**: Commits pequeños y lógicos dentro del PR
6. **Descripciones**: Claras y detalladas
7. **Responsiveness**: Responder a comentarios rápidamente

### PR Labels

```
- enhancement    # Mejora de funcionalidad
- bug            # Corrección de bug
- documentation  # Cambios en documentación
- testing        # Cambios en tests
- ci/cd          # Cambios en pipelines
- refactor       # Refactorización
- urgent         # Alta prioridad
- wip            # Trabajo en progreso
```

---

## 🔍 Code Review Process

### Reviewer Responsibilities

1. **Funcionalidad**: ¿Hace lo que promete?
2. **Calidad**: ¿Es el código mantenible?
3. **Tests**: ¿Hay suficiente cobertura?
4. **Documentación**: ¿Está bien documentado?
5. **Performance**: ¿Hay impacto en rendimiento?
6. **Seguridad**: ¿Hay vulnerabilidades?

### Review Checklist

- [ ] Código es legible y mantenible
- [ ] No hay duplicación
- [ ] Nombres son descriptivos
- [ ] Tests cubren casos principales
- [ ] Documentación está actualizada
- [ ] No hay breaking changes sin documentar
- [ ] Performance aceptable
- [ ] Sin code smells obvios

### Comment Types

```
// Must fix before merge
MUST: This will cause runtime error

// Should fix for code quality
SHOULD: Consider using stream API here

// Nice to have improvement
NICE: Could also handle edge case

// Informational comment
INFO: This follows pattern from service.java
```

---

## 🔀 Merge Strategies

### Default Strategy: Squash + Merge

**Para features y bugfixes:**
```
- Squash commits en feature branch
- Un commit limpio en develop
- Preservar historial de feature branch
```

**Ventajas:**
- Historia limpia en develop
- Revertes más fáciles
- Identifica cambios por PR

### Merge Strategy: Create a Merge Commit

**Para hotfixes a main:**
```
- Crear merge commit explícito
- Preservar historia completa
- Identificar punto de hotfix
```

### No-Fast-Forward Policy

**Siempre mergear con --no-ff:**
```bash
git merge --no-ff feature/branch
```

**Beneficios:**
- Historial claro de branches
- Sabe dónde se originaron features
- Facilita identificar hotfixes

---

## 🏷️ Release Management

### Versioning (Semantic Versioning)

```
v<MAJOR>.<MINOR>.<PATCH>
v1.2.3
  └─ MAJOR: Cambios incompatibles
  └─ MINOR: Nuevas funcionalidades
  └─ PATCH: Correcciones de bugs
```

### Release Branch

```bash
# Crear release branch
git checkout -b release/v1.2.0 develop

# Hacer cambios finales (version bumps, etc)
git commit -m "chore: bump version to 1.2.0"

# Mergear a main y tag
git checkout main
git merge --no-ff release/v1.2.0
git tag -a v1.2.0 -m "Release version 1.2.0"

# Mergear a develop
git checkout develop
git merge --no-ff release/v1.2.0

# Eliminar release branch
git branch -d release/v1.2.0
git push origin main develop
git push origin v1.2.0
```

---

## 📊 Workflow Summary

```
1. FEATURE DEVELOPMENT
   develop → feature/nombre → (test & review) → develop

2. BUG FIX (DEVELOPMENT)
   develop → bugfix/nombre → (test & review) → develop

3. PRODUCTION FIX
   main → hotfix/nombre → (test & review) → main → develop

4. RELEASE
   develop → release/v1.2.0 → main (tag) + develop

5. PIPELINE
   push → GitHub Actions → run tests → auto-deploy (si PR approved)
```

---

## ✅ Checklist para Colaboradores

Antes de crear un PR:

- [ ] Rama creada desde la rama correcta (develop o main)
- [ ] Rama sigue convención de naming
- [ ] Commits siguen Conventional Commits
- [ ] Tests creados/actualizados
- [ ] Tests pasan localmente (`mvn test`)
- [ ] Código compilable (`mvn compile`)
- [ ] Sin código comentado o temporal
- [ ] Documentación actualizada
- [ ] PR title es descriptivo
- [ ] PR description completa
- [ ] Auto-merge habilitado si es posible

---

## 🔗 Referencias Útiles

- [Git Flow Original](https://nvie.com/posts/a-successful-git-branching-model/)
- [Conventional Commits](https://www.conventionalcommits.org/)
- [GitHub Flow](https://guides.github.com/introduction/flow/)
- [Semantic Versioning](https://semver.org/)

---

**Última actualización:** 17 de Abril, 2026  
**Responsable:** Equipo DevOps

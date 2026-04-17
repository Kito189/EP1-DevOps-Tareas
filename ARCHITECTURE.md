# Arquitectura - Ticket Service

## Descripción General

Este proyecto implementa una arquitectura **Hexagonal (Puertos y Adaptadores)** combinada con el patrón **CQRS (Command Query Responsibility Segregation)**.

## Capas de la Arquitectura

### 1. Domain (Núcleo del Negocio)
**Ubicación**: `src/main/java/com/example/ticketservice/domain/`

Esta capa contiene la lógica de negocio pura, sin dependencias externas:

- **Entities**: 
  - `Ticket.java`: Entidad principal que representa un ticket de soporte
  - `TicketStatus.java`: Enum con los estados posibles del ticket
  
- **Value Objects**:
  - `EthicalValidator.java`: Valida que se tenga consentimiento
  
- **Factories**:
  - `TicketFactory.java`: Crea instancias válidas de Ticket

**Características**:
- No depende de Spring
- Reglas de negocio encapsuladas
- Fácil de testear

### 2. Application (Casos de Uso)
**Ubicación**: `src/main/java/com/example/ticketservice/application/`

Define los puertos (interfaces) que el dominio expone:

- **Service**:
  - `TicketService.java`: Orquesta el caso de uso de registrar y consultar tickets

- **Ports (Interfaces)**:
  - `TicketUseCase.java`: Puerto de entrada (In Port) - define lo que la aplicación puede hacer
  - `TicketRepositoryPort.java`: Puerto de salida (Out Port) - interfaz con persistencia
  - `NotificationPort.java`: Puerto de salida (Out Port) - interfaz con notificaciones

**Características**:
- Define contratos (interfaces) sin implementación
- Aislada del mundo externo
- Independiente de frameworks

### 3. Infrastructure (Adaptadores)
**Ubicación**: `src/main/java/com/example/ticketservice/infrastructure/`

Implementa los puertos definidos en la capa de aplicación:

#### Adaptadores de Entrada (In Adapters)
- `WriteTicketController.java` (Puerto 8081): Expone endpoints POST para crear tickets
- `ReadTicketController.java` (Puerto 8082): Expone endpoints GET para consultar tickets

#### Adaptadores de Salida (Out Adapters)
- **Persistencia**:
  - `JpaTicketRepositoryAdapter.java`: Adapta el puerto TicketRepositoryPort a JPA
  - `SpringDataTicketRepository.java`: Interfaz Spring Data JPA
  - `TicketEntity.java`: Mapeo JPA de Ticket

- **Notificación**:
  - `ConsoleNotificationAdapter.java`: Adapta el puerto NotificationPort para imprimir en consola

#### Configuración
- `ApplicationConfig.java`: Beans y configuración de Spring

## Patrón CQRS

El proyecto separa las responsabilidades en dos servicios:

### Write Service (Puerto 8081)
```
Cliente -> POST /api/tickets -> WriteTicketController
              |
              ├-> TicketService.registrarTicket()
              ├-> Valida consentimiento (EthicalValidator)
              ├-> TicketFactory.crearTicket()
              ├-> Persiste en BD (TicketRepositoryPort)
              └-> Notifica (NotificationPort)
```

**Base de Datos**: Optimizada para escritura
- `spring.jpa.hibernate.ddl-auto=update`

### Read Service (Puerto 8082)
```
Cliente -> GET /api/tickets/{id} -> ReadTicketController
               |
               └-> TicketService.consultarTicket()
                   └-> Lee de BD (TicketRepositoryPort)
```

**Base de Datos**: Optimizada para lectura
- `spring.jpa.hibernate.ddl-auto=validate`

## Flujo de una Solicitud

### Crear Ticket (Write)
```
1. Cliente hace POST /api/tickets con JSON
2. WriteTicketController recibe el request
3. Valida con EthicalValidator (consentimiento)
4. TicketFactory crea instancia de Ticket
5. TicketService.registrarTicket orquesta el flujo
6. JpaTicketRepositoryAdapter persiste en BD
7. ConsoleNotificationAdapter notifica
8. Retorna Ticket creado (201 Created)
```

### Consultar Ticket (Read)
```
1. Cliente hace GET /api/tickets/{id}
2. ReadTicketController recibe el request
3. TicketService.consultarTicket busca en BD
4. JpaTicketRepositoryAdapter recupera de BD
5. Si existe: retorna Ticket (200 OK)
6. Si no existe: retorna 404 Not Found
```

## Ventajas de esta Arquitectura

### Hexagonal Architecture
- ✅ Fácil testabilidad: El dominio no depende de nada
- ✅ Independencia de frameworks: Se puede cambiar Spring fácilmente
- ✅ Flexibilidad: Los adaptadores se pueden cambiar sin afectar el dominio
- ✅ Mantenibilidad: Código organizado y con responsabilidades claras

### CQRS
- ✅ Escalabilidad: Servicios separados pueden escalar independientemente
- ✅ Performance: Optimizaciones específicas para lectura/escritura
- ✅ Independencia: Un servicio no afecta al otro
- ✅ Elasticidad: Se pueden desplegar por separado

## Ejemplo: Agregar Nueva Funcionalidad

Supongamos que quieres agregar la capacidad de actualizar un ticket:

### 1. Dominio (Domain)
```java
// Agregar método en Ticket.java
public void actualizarDescripcion(String nuevaDescripcion) {
    this.descripcion = nuevaDescripcion;
}
```

### 2. Puertos (Application)
```java
// Agregar método en TicketUseCase.java
Ticket actualizarTicket(Long id, String nuevaDescripcion);
```

### 3. Servicio (Application)
```java
// Implementar en TicketService.java
public Ticket actualizarTicket(Long id, String nuevaDescripcion) {
    Ticket ticket = ticketRepository.obtenerPorId(id)
        .orElseThrow(() -> new TicketNotFoundException(id));
    ticket.actualizarDescripcion(nuevaDescripcion);
    return ticketRepository.guardar(ticket);
}
```

### 4. Adaptador (Infrastructure)
```java
// Agregar en WriteTicketController.java
@PutMapping("/{id}")
public ResponseEntity<TicketResponse> actualizarTicket(@PathVariable Long id, ...) {
    Ticket ticket = ticketService.actualizarTicket(id, ...);
    return ResponseEntity.ok(new TicketResponse(ticket));
}
```

### 5. Tests
```java
// Agregar tests en TicketServiceTest.java
void testActualizarTicket() { ... }
```

## Dependencias entre Capas

```
┌─────────────────────────────────┐
│      Infrastructure             │
│   (Controllers, Adapters)       │
└──────────────┬──────────────────┘
               │
               ▼
┌─────────────────────────────────┐
│      Application                │
│   (Services, Ports/Interfaces)  │
└──────────────┬──────────────────┘
               │
               ▼
┌─────────────────────────────────┐
│      Domain                     │
│   (Business Logic, Entities)    │
└─────────────────────────────────┘
```

**Regla**: Las capas superiores dependen de las inferiores, pero nunca al revés.

## Testing

La arquitectura facilita el testing:

- **Unit Tests**: Testean el dominio sin dependencias
- **Integration Tests**: Testean los servicios con mocks de puertos
- **Controller Tests**: Testean los controladores con MockMvc

Ejemplo de test con mocks:
```java
@Test
void testRegistrarTicket() {
    when(ticketRepository.guardar(any())).thenReturn(ticket);
    Ticket resultado = ticketService.registrarTicket("Desc", true);
    verify(ticketRepository).guardar(any());
}
```

## Conclusión

Esta arquitectura proporciona una base sólida, mantenible y escalable para desarrollar aplicaciones empresariales complejas.

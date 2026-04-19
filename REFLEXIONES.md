# Reflexiones Individuales y Declaración de Uso de IA

**Evaluación Parcial N°1 - DOY0101 Ingeniería DevOps**
**Encargo:** Tu primer pipeline de despliegue
**Modalidad:** Parejas

---

## Integrantes del Equipo

| Nombre | GitHub |
|--------|--------|
| [INTEGRANTE 1 - Marco parra ]  [@Marco-Parra25](https://github.com/Marco-Parra25). |
| [INTEGRANTE 2 - Luis Inostroza ]  [@Kito189](https://github.com/Kito189) |

---

## Conclusiones del Equipo

Conclusiones del Equipo
A través de este encargo, como equipo logramos consolidar el aprendizaje sobre el ciclo de vida de un microservicio bajo una cultura DevOps. El principal aprendizaje grupal fue comprender que la automatización y la estandarización no son solo tareas técnicas, sino pilares para la colaboración efectiva; al definir convenciones de commits y flujos de trabajo claros, logramos integrar código de manera fluida y sin conflictos mayores. El trabajo en parejas se articuló mediante una división de roles donde uno se enfocó en la solidez de la infraestructura (CI/CD y Docker) y el otro en la integridad de la arquitectura y la calidad del código, utilizando los Pull Requests como nuestra principal herramienta de comunicación y revisión técnica. Esta dinámica nos permitió asegurar que el producto final no solo fuera funcional, sino que también cumpliera con estándares profesionales de trazabilidad y mantenibilidad.

---

## Reflexión Individual - Integrante 1

**Nombre:** [Marco Parra]

### ¿Qué aprendí en este encargo?
En este encargo comprendí profundamente cómo una estrategia de ramificación como Git Flow permite organizar el desarrollo colaborativo en la nube. Aprendí a diferenciar el uso de ramas de soporte (feature y hotfix) para mantener la estabilidad de la rama principal. También entendí el rol crítico de las herramientas de automatización como GitHub Actions en un flujo de CI/CD, viendo cómo los tests y builds automáticos previenen errores antes de integrar el código. Superé la dificultad de coordinar cambios simultáneos simulando un entorno de trabajo real
Puedes mencionar:


### ¿Cuál fue mi contribución al proyecto?

Configuración de CI/CD: Implementé los flujos de GitHub Actions para ejecutar pruebas automáticas en cada push a develop.Gestión de Ramas: Trabajé directamente en las ramas feature/configurar-actions y realicé la simulación del error crítico en la rama hotfix/fix-ethicalvalidator para demostrar la trazabilidad del código.Documentación Técnica: Redacté las secciones de arquitectura y convenciones de commits en el README.md, estableciendo los estándares para el equipo.Revisión de Código: Revisé y aprobé Pull Requests (PRs) fundamentales para asegurar que el código cumpliera con la Arquitectura Hexagonal propuesta

### ¿Qué haría diferente la próxima vez?
Para futuros proyectos, me gustaría implementar un modelo de Trunk-based development para comparar la velocidad de entrega frente a Git Flow. También buscaría profundizar en la automatización del despliegue (Continuous Deployment) hacia un entorno cloud real y no solo simulado, para cerrar el ciclo completo de DevOps. Finalmente, mejoraría la gestión de tiempos para evitar errores en las últimas correcciones de los workflows.

---

## Reflexión Individual - Integrante 2

**Nombre:** [Luis inostroza ]

### ¿Qué aprendí en este encargo?
A través de este proyecto, comprendí la importancia de la trazabilidad del código en entornos profesionales. Aprendí que DevOps no es solo herramientas, sino una cultura de colaboración que se apoya en convenciones claras, como los Conventional Commits, para que cualquier miembro del equipo entienda la historia del proyecto sin ambigüedades. También reforcé mis conocimientos en Arquitectura Hexagonal, entendiendo cómo el aislamiento del dominio facilita el testing automatizado que luego integramos en el pipeline. Superé el reto de gestionar conflictos de integración al trabajar con múltiples ramas de feature de forma simultánea.

### ¿Cuál fue mi contribución al proyecto?
Desarrollo de Microservicios: Trabajé en la implementación del patrón CQRS, asegurando la correcta separación entre el servicio de lectura y el de escritura.


Gestión de Hotfixes: Lideré la resolución de errores en la rama hotfix/actualizar-workflows-y-dockerfile, asegurando que la imagen de Docker fuera ligera y funcional para el despliegue.

Simulación Colaborativa: Realicé los cambios correspondientes a la rama feature/mejorar-documentacion, documentando los flujos de merge y estrategias de revisión en el archivo COLLABORATION.md.

Testing: Aseguré la cobertura de los 24 tests unitarios e integrales, verificando que los checks de GitHub Actions pasaran correctamente antes de cada merge a develop.

### ¿Qué haría diferente la próxima vez?
En una próxima oportunidad, me gustaría implementar pruebas de seguridad automatizadas (SAST) dentro del workflow de GitHub Actions para detectar vulnerabilidades en las dependencias de forma temprana. También considero que podríamos haber utilizado una herramienta de gestión de proyectos como GitHub Projects para visualizar mejor el flujo de las tareas y los estados de cada Pull Request, optimizando aún más la comunicación del equipo.

---

## Declaración de Uso de Inteligencia Artificial

Siguiendo las indicaciones éticas de Duoc UC para el uso de IA en trabajos académicos, declaramos el siguiente uso de herramientas de IA:

### Herramientas utilizadas

| Herramienta | Proveedor | Propósito |
|-------------|-----------|-----------|
| Claude (Anthropic) | Anthropic | Asistencia en redacción de documentación y generación de código boilerplate |
| [Agregar otras si se usaron] | | |

### ¿Cómo se aplicó?

**Uso permitido según normativa:**
- ✅ Mejora de redacción en documentación técnica (README, guías)
- ✅ Búsqueda de referencias sobre Git Flow y Conventional Commits
- ✅ Generación de ejemplos de código y plantillas
- ✅ Explicación de conceptos técnicos para mejor comprensión
- ✅ Configuración inicial de GitHub Actions workflows

**NO se utilizó IA para:**
- ❌ Redactar estas reflexiones personales
- ❌ Justificaciones técnicas (elaboradas por el equipo)
- ❌ Conclusiones críticas del proyecto
- ❌ Análisis del proceso de aprendizaje

### Validación y responsabilidad

- Todo el contenido generado con IA fue **revisado y validado** por el equipo
- Las **ideas, análisis y justificaciones técnicas** son propias del equipo
- Las **reflexiones personales** fueron redactadas sin apoyo de IA
- Se siguió la guía oficial: https://bibliotecas.duoc.cl/ia

### Cita formal (APA 7)

```
Anthropic. (2026). Claude [Modelo de lenguaje grande].
Recuperado de https://www.anthropic.com/claude
```



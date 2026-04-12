Microservicio: TareasFullStack

Repositorio: https://github.com/Kito189/EP1-DevOps-Tareas.git

Este repositorio corresponde al desarrollo del microservicio TareasFullStack, utilizado como base para implementar un pipeline DevOps en la Evaluación Parcial 1 de la asignatura Ingeniería DevOps.

1. Estrategia de Ramificación
Modelo usado: GitFlow
Por qué lo usamos:
Se eligió GitFlow porque ayuda a mantener el proyecto ordenado. La idea es separar bien lo que está funcionando en producción (main) de lo que se está desarrollando (develop).
También permite trabajar en paralelo sin problemas, ya que cada funcionalidad va en su propia rama (feature).
En caso de que aparezca un error en producción, se puede arreglar rápido con una rama hotfix sin afectar lo que se está desarrollando.
2. Buenas Prácticas

Para evitar desorden y facilitar el trabajo en equipo, se acordaron las siguientes reglas:

Ramas
main: versión estable del proyecto
develop: donde se integran los avances
feature/<nombre>: nuevas funcionalidades
Ej: feature/login, feature/api-tareas
hotfix/<nombre>: arreglos urgentes
Ej: hotfix/error-login
Commits

Se usa un formato simple para que sea fácil entender los cambios:

tipo: descripción

Tipos más comunes:

feat: nueva funcionalidad
fix: corrección de errores
docs: cambios en documentación
chore: configuración o mantenimiento

Ejemplo:
feat: crear endpoint para listar tareas

Pull Request y Merge
Las funcionalidades salen desde develop hacia ramas feature.
No se hacen commits directos a main ni a develop.
Todo cambio debe pasar por un Pull Request.
El otro integrante debe revisar antes de hacer merge.
Estructura del Proyecto
/src: código del microservicio
/.github/workflows: configuración de GitHub Actions
/docs: documentación extra (si se necesita)
Uso de IA

Se utilizó IA como apoyo para mejorar la redacción y ordenar el README, pero la estructura del proyecto y las decisiones tomadas fueron definidas por el equipo.
# Memoria del Proyecto - Gestión de Usuarios (Hexagonal + DDD)

Este documento registra los cambios del proyecto, su ubicación y la justificación técnica aplicada para cada uno de ellos, permitiendo una rápida auditoría y recuperación.

## Arquitectura del Proyecto

El proyecto sigue los principios de Arquitectura Hexagonal y Diseño Guiado por el Dominio (DDD):

*   **domain:** Contiene el modelo de dominio puro sin dependencias de frameworks ni librerías externas.
*   **application:** Define los puertos de entrada (in) y salida (out), así como los servicios de aplicación que implementan los casos de uso.
*   **infrastructure:** Contiene los adaptadores de persistencia (ahora con PostgreSQL mediante JDBC puro), adaptadores de notificación de correo (SMTP con JavaMail), los puntos de entrada (CLI para escritorio y REST para HTTP) y la configuración de inyección manual de dependencias.

---

## Historial de Cambios

### 25 de Agosto de 2026 - Preparación para Despliegue en Render (Dockerización)

#### 1. Configuración de Propiedades Dinámicas
*   **Ubicación:** [application.properties](file:///Users/mac/universidad/users-management-spring-boot-hexagonal-ddd/src/main/resources/application.properties)
*   **Modificación:** Se reemplazan los valores estáticos de conexión a base de datos, SMTP y puerto del servidor por variables de entorno con valores por defecto. Esto permite inyectar credenciales de forma segura en entornos de producción (Render) sin alterar el código fuente.
*   **Lógica:** Cumplir con el principio de configuración desacoplada del código y seguridad de variables de entorno.
*   **Estado:** Completado e integrado.

#### 2. Creación del Dockerfile
*   **Ubicación:** [Dockerfile](file:///Users/mac/universidad/users-management-spring-boot-hexagonal-ddd/Dockerfile)
*   **Modificación:** Creación de un archivo de definición de contenedor multi-etapa (Maven para compilación y Eclipse Temurin 17 JRE Alpine para ejecución).
*   **Lógica:** Optimizar el tamaño de la imagen final y mejorar la seguridad ejecutando la aplicación con un usuario sin privilegios de administrador (no-root).
*   **Estado:** Completado e integrado.

### 25 de Agosto de 2026 - Migración a PostgreSQL y Orquestación Local (Docker Compose)

#### 3. Migración del Driver de Base de Datos
*   **Ubicación:** [pom.xml](file:///Users/mac/universidad/users-management-spring-boot-hexagonal-ddd/pom.xml)
*   **Modificación:** Se eliminó la dependencia del conector de MySQL (`mysql-connector-j`) y se añadió el driver JDBC oficial de PostgreSQL (`postgresql` versión `42.7.2`).
*   **Lógica:** Permitir que la aplicación se conecte de forma nativa a la base de datos PostgreSQL gestionada de Render.
*   **Estado:** Completado e integrado.

#### 4. Adaptación de URL de Conexión
*   **Ubicación:** [DatabaseConfig.java](file:///Users/mac/universidad/users-management-spring-boot-hexagonal-ddd/src/main/java/com/jcaa/usersmanagement/infrastructure/adapter/persistence/config/DatabaseConfig.java)
*   **Modificación:** Se actualizó la plantilla de URL JDBC para utilizar el protocolo `jdbc:postgresql` en lugar de `jdbc:mysql` y se simplificaron los parámetros de consulta de conexión.
*   **Lógica:** Compatibilidad con el nuevo motor de base de datos relacional.
*   **Estado:** Completado e integrado.

#### 5. Modificación del Esquema SQL
*   **Ubicación:** [schema.sql](file:///Users/mac/universidad/users-management-spring-boot-hexagonal-ddd/src/main/resources/schema.sql)
*   **Modificación:** Redefinición del esquema para PostgreSQL. Se eliminaron sentencias específicas de MySQL (`CREATE DATABASE`, `USE`, `ENGINE=InnoDB`, `ON UPDATE CURRENT_TIMESTAMP`), se transformaron columnas `ENUM` a `VARCHAR` y se añadió `ON CONFLICT DO NOTHING` al script de inserción.
*   **Lógica:** Asegurar la portabilidad del esquema y prevenir fallos de inicialización automatizada en múltiples ejecuciones.
*   **Estado:** Completado e integrado.

#### 6. Transición del Adaptador de Persistencia
*   **Ubicaciones:** 
    *   [UserRepositoryPostgres.java](file:///Users/mac/universidad/users-management-spring-boot-hexagonal-ddd/src/main/java/com/jcaa/usersmanagement/infrastructure/adapter/persistence/repository/UserRepositoryPostgres.java) (Nuevo)
    *   `UserRepositoryMySQL.java` (Eliminado)
    *   [DependencyContainer.java](file:///Users/mac/universidad/users-management-spring-boot-hexagonal-ddd/src/main/java/com/jcaa/usersmanagement/infrastructure/config/DependencyContainer.java) (Modificado)
*   **Modificación:** Creación del nuevo repositorio adaptado para PostgreSQL, remoción del repositorio MySQL original y actualización de la inyección en el contenedor de dependencias manual.
*   **Lógica:** Adaptar la capa de infraestructura física al nuevo motor de persistencia del sistema sin afectar los puertos de salida ni el dominio de la aplicación.
*   **Estado:** Completado e integrado.

#### 7. Orquestación del Entorno Local con Docker Compose
*   **Ubicación:** [docker-compose.yml](file:///Users/mac/universidad/users-management-spring-boot-hexagonal-ddd/docker-compose.yml)
*   **Modificación:** Creación del archivo de composición para orquestar localmente un contenedor de base de datos PostgreSQL 15 inicializado con `schema.sql` y el contenedor de la aplicación Java vinculados en la misma red privada.
*   **Lógica:** Simplificar el proceso de despliegue, desarrollo y pruebas locales en un comando único.
*   **Estado:** Completado e integrado.

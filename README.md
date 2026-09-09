# Users Management — Spring Boot, Arquitectura Hexagonal y DDD

Aplicación de gestión de usuarios construida con Java 17 y Spring Boot. La API REST es el punto de entrada activo. El código de la antigua CLI se conserva como adaptador inactivo y no posee un contenedor de dependencias independiente.

Spring es el único *composition root*: `Main` inicia el contexto y las dependencias se resuelven mediante configuración y component scanning de Spring.

---

## Ejecución con Docker (recomendado)

El sistema completo (aplicación + MySQL + SMTP) se levanta con un solo comando.

### Prerequisitos

- [Docker](https://docs.docker.com/get-docker/) >= 20.10
- [Docker Compose](https://docs.docker.com/compose/install/) >= 2.0

### Levantar todo

```bash
docker compose up --build
```

Esto construye la imagen de la aplicación, levanta MySQL, MailHog (SMTP de prueba) y la app. La base de datos se inicializa automáticamente con el esquema y un usuario administrador.

### Servicios y puertos

| Servicio  | Puerto  | Descripción                         |
|-----------|---------|-------------------------------------|
| App       | 8080    | API REST                            |
| MySQL     | 3306    | Base de datos                       |
| MailHog   | 8025    | Web UI para ver correos enviados    |
| MailHog   | 1025    | SMTP (para la app)                  |

### Verificar

```bash
# Crear usuario
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "id": "usr-001",
    "name": "Juan Perez",
    "email": "juan@example.com",
    "password": "Password123!",
    "role": "MEMBER"
  }'

# Listar usuarios
curl http://localhost:8080/api/users

# Swagger UI
# Abrir http://localhost:8080/swagger-ui.html
```

### Ver correos enviados

Abrir http://localhost:8025 para acceder a la interfaz de MailHog y ver los correos generados por la aplicación (bienvenida, actualización).

### Detener

```bash
docker compose down
```

### Eliminar datos (MySQL volumes)

```bash
docker compose down -v
```

---

## Ejecución local (sin Docker)

### Requisitos

- Java 17
- MySQL 8 corriendo en `localhost:3306`
- Base de datos `crud_usuarios` creada (ejecutar `src/main/resources/schema.sql`)

### Build y test

```bash
./mvnw clean test
./mvnw clean package
```

En Windows se puede utilizar `mvnw.cmd`.

### Ejecutar

```bash
./mvnw spring-boot:run
# o
java -jar target/users-management-2.1.0.jar
```

La aplicación usa las propiedades de `src/main/resources/application.properties` por defecto (MySQL en `localhost`, SMTP en Gmail).

---

## Configuración SMTP

Por defecto en Docker se usa **MailHog** como servidor SMTP de prueba. Para usar Gmail u otro proveedor real, sobrescribir las variables de entorno en `docker-compose.yml` o pasarlas al ejecutar:

```bash
SMTP_HOST=smtp.gmail.com
SMTP_PORT=587
SMTP_USERNAME=tu_correo@gmail.com
SMTP_PASSWORD=tu_app_password
SMTP_FROM_ADDRESS=tu_correo@gmail.com
SMTP_FROM_NAME="Gestion de Usuarios"
```

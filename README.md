# Users Management — Spring Boot, Arquitectura Hexagonal y DDD

Aplicación de gestión de usuarios construida con Java 17 y Spring Boot. La API REST es el punto de entrada activo. El código de la antigua CLI se conserva como adaptador inactivo y no posee un contenedor de dependencias independiente.

Spring es el único *composition root*: `Main` inicia el contexto y las dependencias se resuelven mediante configuración y component scanning de Spring.

## Verificación

```bash
./mvnw clean test
./mvnw clean package
```

En Windows se puede utilizar `mvnw.cmd`.

## PostgreSQL con Docker

Requisitos: Docker y Docker Compose.

Para levantar PostgreSQL, MailHog y la aplicación:

```bash
docker compose up -d --build
```

Servicios disponibles:

- API: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- MailHog: `http://localhost:8025`
- PostgreSQL: `localhost:5432`, base `crud_usuarios`, usuario `postgres`, contraseña `postgres`

La configuración local usa el perfil `postgresql` por defecto. Las variables `DB_HOST`, `DB_PORT`,
`DB_NAME`, `DB_USERNAME` y `DB_PASSWORD` permiten conectarse a otra instancia. Para apagar los
servicios sin borrar los datos:

```bash
docker compose down
```

Para borrar también el volumen de PostgreSQL:

```bash
docker compose down -v
```

El adaptador MySQL se conserva para compatibilidad y se puede activar con
`SPRING_PROFILES_ACTIVE=mysql`.

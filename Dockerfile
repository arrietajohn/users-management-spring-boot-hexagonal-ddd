# ==============================================================================
# ETAPA 1: Compilacion y Construccion del Artefacto
# ==============================================================================
# Utiliza la imagen oficial de Maven con Eclipse Temurin JDK 17
FROM maven:3.9.6-eclipse-temurin-17 AS constructor

# Definir el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo de definicion del proyecto para resolver las dependencias
COPY pom.xml .

# Descargar las dependencias en cache para optimizar futuras construcciones
RUN mvn dependency:go-offline -B

# Copiar el codigo fuente del proyecto
COPY src ./src

# Compilar y empaquetar el proyecto saltando las pruebas unitarias para agilizar el despliegue
RUN mvn clean package -DskipTests

# ==============================================================================
# ETAPA 2: Entorno de Ejecucion (Runtime)
# ==============================================================================
# Utiliza una imagen ligera de Eclipse Temurin JRE 17 basada en Alpine para mayor seguridad
FROM eclipse-temurin:17-jre-alpine

# Crear un usuario de sistema no privilegiado para ejecutar la aplicacion (seguridad no-root)
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Definir el directorio de ejecucion
WORKDIR /app

# Copiar el archivo .jar generado en la etapa de compilacion usando comodines
# Esto previene errores de compilacion en Docker si cambia la version en el pom.xml
COPY --from=constructor /app/target/users-management-*.jar app.jar

# Cambiar la propiedad de los archivos al usuario no-root
RUN chown -R appuser:appgroup /app

# Cambiar al usuario no-root para las instrucciones siguientes y la ejecucion
USER appuser

# Exponer el puerto en el que Spring Boot escuchara las peticiones HTTP
EXPOSE 8080

# Comando de inicio del contenedor. Se pasa la variable PORT dinamica de Render con fallback a 8080
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT:-8080}"]

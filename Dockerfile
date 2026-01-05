# Imagen base con Java
FROM eclipse-temurin:17-jdk-alpine

# Directorio de trabajo
WORKDIR /app

# Copiar el jar generado
COPY target/*.jar app.jar

# Exponer el puerto (Render usa PORT)
EXPOSE 8080

# Ejecutar la app
ENTRYPOINT ["java","-jar","/app/app.jar"]

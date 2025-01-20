# Usa una imagen base de Java
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el JAR del backend al contenedor
COPY target/backend.jar /app/backend.jar

# Expone el puerto del backend
EXPOSE 8080

# Comando para ejecutar el backend
ENTRYPOINT ["java", "-jar", "backend.jar"]

# Usa Node.js para construir la aplicación
FROM node:18 AS build

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia los archivos del frontend
COPY package*.json ./
COPY ./ ./

# Instala las dependencias y construye la aplicación
RUN npm install
RUN npm run build

# Usa Nginx para servir la aplicación
FROM nginx:alpine

# Copia el archivo de configuración de Nginx al contenedor
COPY nginx/nginx.conf /etc/nginx/conf.d/default.conf

# Copia los archivos construidos al contenedor de Nginx
COPY --from=build /app/build /usr/share/nginx/html

# Expone el puerto 80
EXPOSE 80

# Comando para iniciar Nginx
CMD ["nginx", "-g", "daemon off;"]

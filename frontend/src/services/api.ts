/***********************************************
 * src/services/api.tsx
 ***********************************************/
import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080", // Ajusta a tu entorno
  headers: {
    "Content-Type": "application/json",
  },
});

// Interceptor para inyectar el token en las peticiones
api.interceptors.request.use((config) => {
  // Evitar inyectar el token si la URL es /api/login
  if (config.url !== "/api/login") {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`; // Añade el token al encabezado
    }
  }
  return config;
});

export default api;
/* 
   Misiones Diarias
*/
export const obtenerMisionesDiarias = async () => {
  const respuesta = await api.get("/api/misiones/diarias");
  return respuesta.data; // array de MisionUsuarioDTO
};

export const actualizarProgresoMision = async (
  misionId: number,
  incremento: number
) => {
  // Notar que en el controlador se espera un POST a "/api/misiones/{misionId}/progreso"
  // con un JSON { "incremento": valor }
  return api.post(`/api/misiones/${misionId}/progreso`, {
    incremento,
  });
};

/*
   Clasificación (Ranking)
*/
export const obtenerClasificacion = async () => {
  const respuesta = await api.get("/api/clasificacion");
  return respuesta.data;
};

// Registrar un nuevo usuario

export const registrarUsuario = async (
  email: string,
  nombreUsuario: string,
  contrasena: string
) => {
  return api.post("/api/usuarios/registro", {
    email,
    nombreUsuario,
    contrasena,
  });
};

/* 
   Info de Usuario
*/

export const obtenerInfoUsuario = async () => {
  const respuesta = await api.get("/api/usuarios/perfil");
  return respuesta.data; // Devuelve {id, nombreUsuario, email, nivel, exp, ...}
};

export const actualizarNombreUsuario = async (nuevoNombreUsuario: string) => {
  return api.patch("/api/usuarios/perfil", {
    nombreUsuario: nuevoNombreUsuario,
  });
};

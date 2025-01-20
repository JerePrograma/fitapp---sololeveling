/***********************************************
 * src/hooks/context/authContext.tsx
 ***********************************************/

import React, {
  createContext,
  useContext,
  useEffect,
  useState,
  ReactNode,
} from "react";
import api from "../../services/api"; // Axios config con baseURL y cabeceras

interface AuthContextProps {
  isAuth: boolean; // Indica si hay sesión
  loading: boolean; // Indica si está cargando la verificación
  login: (email: string, contrasena: string) => Promise<void>;
  logout: () => void;
}

const authContext = createContext<AuthContextProps | undefined>(undefined);

/** Hook para consumir el authContext */
export const useAuth = () => {
  const context = useContext(authContext);
  if (!context) throw new Error("useAuth must be used within an AuthProvider");
  return context;
};

/** Provider que envuelve toda la app */
export const AuthProvider: React.FC<{ children: ReactNode }> = ({
  children,
}) => {
  const [isAuth, setIsAuth] = useState(false);
  const [loading, setLoading] = useState(true);

  // Al montar, chequea si hay token en localStorage
  useEffect(() => {
    const token = localStorage.getItem("token");
    setIsAuth(!!token);
    setLoading(false);
  }, []);

  const login = async (email: string, contrasena: string) => {
    try {
      const response = await api.post("/api/login", { email, contrasena }); // Realiza la solicitud de login
      const { token } = response.data;
      localStorage.setItem("token", token);

      setIsAuth(true); // Marca al usuario como autenticado
    } catch (error) {
      console.error("Error durante el inicio de sesión:", error);
      throw error; // Lanza el error para manejarlo en el componente
    }
  };
  /** Lógica de logout: elimina el token y marca isAuth en false */
  const logout = () => {
    localStorage.removeItem("token");
    setIsAuth(false);
    window.location.href = "/login";
  };

  return (
    <authContext.Provider value={{ isAuth, loading, login, logout }}>
      {children}
    </authContext.Provider>
  );
};

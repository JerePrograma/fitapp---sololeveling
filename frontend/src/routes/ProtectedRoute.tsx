/***********************************************
 * src/routes/ProtectedRoute.tsx
 ***********************************************/

import React from "react";
import { Route, Redirect } from "react-router-dom";
import { useAuth } from "../hooks/context/authContext";

interface ProtectedRouteProps {
  component: React.FC<any>;
  exact?: boolean;
  path: string;
}

const ProtectedRoute: React.FC<ProtectedRouteProps> = ({
  component: Component,
  ...rest
}) => {
  const { isAuth, loading } = useAuth();

  // Mientras loading sea true, podrías mostrar un Spinner
  if (loading) {
    return <div>Cargando...</div>;
  }

  // Si no está autenticado, redirigir al login
  return (
    <Route
      {...rest}
      render={(props) =>
        isAuth ? <Component {...props} /> : <Redirect to="/login" />
      }
    />
  );
};

export default ProtectedRoute;

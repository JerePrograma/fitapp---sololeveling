/***********************************************
 * src/pages/Form/LoginForm.tsx
 ***********************************************/

import React, { useState } from "react";
import {
  IonPage,
  IonHeader,
  IonToolbar,
  IonTitle,
  IonContent,
  IonItem,
  IonLabel,
  IonInput,
  IonButton,
  IonLoading,
} from "@ionic/react";
import { useAuth } from "../../hooks/context/authContext";
import "./Form.scss";

const LoginForm: React.FC = () => {
  const { login } = useAuth();

  // Estados para los campos
  const [email, setEmail] = useState("");
  const [contrasena, setContrasena] = useState("");

  // Para indicar si está procesando la petición
  const [cargando, setCargando] = useState(false);
  // Para manejar mensajes de error
  const [errorMsg, setErrorMsg] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setCargando(true);
    setErrorMsg("");

    try {
      await login(email, contrasena);
      // Si login es exitoso, authContext cambiará isAuth a true,
      // y redirigimos (puedes forzar la redirección si gustas).
      window.location.href = "/home";
    } catch (error) {
      setErrorMsg("Error al iniciar sesión. Verifica tus credenciales.");
      console.error(error);
    } finally {
      setCargando(false);
    }
  };

  return (
    <IonPage className="centeredContent">
      <IonHeader>
        <IonToolbar>
          <IonTitle>Iniciar Sesión</IonTitle>
        </IonToolbar>
      </IonHeader>

      <IonContent>
        <form onSubmit={handleSubmit} className="centeredContent">
          <IonItem>
            <IonLabel position="floating">Email</IonLabel>
            <IonInput
              value={email}
              onIonChange={(e) => setEmail(e.detail.value!)}
              required
            />
          </IonItem>

          <IonItem>
            <IonLabel position="floating">Contraseña</IonLabel>
            <IonInput
              type="password"
              value={contrasena}
              onIonChange={(e) => setContrasena(e.detail.value!)}
              required
            />
          </IonItem>

          <IonButton type="submit" expand="block" className="ion-margin-top">
            Iniciar Sesión
          </IonButton>
        </form>
      </IonContent>
    </IonPage>
  );
};

export default LoginForm;

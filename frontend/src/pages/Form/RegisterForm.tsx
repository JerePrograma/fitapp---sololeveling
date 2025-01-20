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
} from "@ionic/react";
import { registrarUsuario } from "../../services/api";
import "./Form.scss";

const Register: React.FC = () => {
  const [email, setEmail] = useState("");
  const [nombreUsuario, setNombreUsuario] = useState("");
  const [contrasena, setContrasena] = useState("");
  const [mensaje, setMensaje] = useState("");
  const [error, setError] = useState("");

  const handleRegistro = async (e: React.FormEvent) => {
    e.preventDefault();
    setMensaje("");
    setError("");

    try {
      const response = await registrarUsuario(email, nombreUsuario, contrasena);
      setMensaje(response.data); // "Usuario creado exitosamente."
    } catch (err: any) {
      if (err.response?.status === 400) {
        setError(err.response.data);
      } else {
        setError("Error interno del servidor.");
      }
    }
  };

  return (
    <IonPage className="centeredContent">
      <IonHeader>
        <IonToolbar>
          <IonTitle>Registro</IonTitle>
        </IonToolbar>
      </IonHeader>

      <IonContent>
        <form onSubmit={handleRegistro} className="centeredContent">
          <IonItem>
            <IonLabel position="floating">Email</IonLabel>
            <IonInput
              value={email}
              onIonChange={(e) => setEmail(e.detail.value!)}
            />
          </IonItem>

          <IonItem>
            <IonLabel position="floating">Nombre de usuario</IonLabel>
            <IonInput
              value={nombreUsuario}
              onIonChange={(e) => setNombreUsuario(e.detail.value!)}
            />
          </IonItem>

          <IonItem>
            <IonLabel position="floating">Contraseña</IonLabel>
            <IonInput
              type="password"
              value={contrasena}
              onIonChange={(e) => setContrasena(e.detail.value!)}
            />
          </IonItem>

          <IonButton expand="block" type="submit" className="ion-margin-top">
            Registrar
          </IonButton>
        </form>
      </IonContent>
    </IonPage>
  );
};

export default Register;

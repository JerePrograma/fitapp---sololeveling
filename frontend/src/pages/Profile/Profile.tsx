// src/pages/Profile/Profile.tsx
import React, { useEffect, useState } from "react";
import {
  IonPage,
  IonHeader,
  IonToolbar,
  IonTitle,
  IonContent,
} from "@ionic/react";
import { obtenerInfoUsuario } from "../../services/api";
import ProfileForm from "../Form/ProfileForm";
import "./Profile.scss";

const Profile: React.FC = () => {
  const [jugador, setJugador] = useState<any>({
    nombreUsuario: "",
    nivel: 1,
    exp: 0,
  });

  // Carga el perfil una vez al montar (o al refrescar)
  useEffect(() => {
    cargarDatosPerfil();
  }, []);

  const cargarDatosPerfil = async () => {
    try {
      const data = await obtenerInfoUsuario();
      setJugador(data);
    } catch (error) {
      console.error("Error al obtener datos de perfil:", error);
    }
  };

  // Callback que pasamos al formulario
  const handleNameUpdated = () => {
    // Después de actualizar el nombre, recargamos el perfil
    cargarDatosPerfil();
  };

  return (
    <IonPage className="centeredContent">
      <IonHeader>
        <IonToolbar>
          <IonTitle>Perfil</IonTitle>
        </IonToolbar>
      </IonHeader>

      <IonContent>
        {/* Mostrar datos del usuario */}
        <div style={{ padding: "1rem" }}>
          <h2>Nombre actual: {jugador.nombreUsuario}</h2>
          <p>Nivel: {jugador.nivel}</p>
          <p>Experiencia: {jugador.exp}</p>
        </div>

        {/* Formulario para cambiar el nombre */}
        <ProfileForm
          nombreActual={jugador.nombreUsuario}
          onNameUpdated={handleNameUpdated}
        />
      </IonContent>
    </IonPage>
  );
};

export default Profile;

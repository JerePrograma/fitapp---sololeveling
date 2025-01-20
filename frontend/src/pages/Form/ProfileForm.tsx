// src/components/ProfileForm.tsx
import React, { useState } from "react";
import { IonItem, IonLabel, IonInput, IonButton } from "@ionic/react";
import { actualizarNombreUsuario } from "../../services/api"; // Ajusta la ruta según tu estructura
import "./Form.scss";

interface ProfileFormProps {
  nombreActual: string;
  onNameUpdated: () => void; // callback para avisar al padre que se actualizó el nombre
}

const ProfileForm: React.FC<ProfileFormProps> = ({
  nombreActual,
  onNameUpdated,
}) => {
  const [nuevoNombre, setNuevoNombre] = useState(nombreActual);

  const manejarCambioNombre = async () => {
    try {
      await actualizarNombreUsuario(nuevoNombre);
      onNameUpdated(); // avisar al padre para que refresque el perfil
    } catch (error) {
      console.error("Error al actualizar el nombre de usuario:", error);
    }
  };

  return (
    <div style={{ padding: "1rem" }}>
      <IonItem>
        <IonLabel position="floating">Nuevo nombre</IonLabel>
        <IonInput
          value={nuevoNombre}
          onIonChange={(e) => setNuevoNombre(e.detail.value!)}
        />
      </IonItem>

      <IonButton
        expand="block"
        onClick={manejarCambioNombre}
        className="ion-margin-top"
      >
        Cambiar nombre
      </IonButton>
    </div>
  );
};

export default ProfileForm;

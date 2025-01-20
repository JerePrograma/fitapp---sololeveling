import React, { useEffect, useState } from "react";
import {
  IonPage,
  IonHeader,
  IonToolbar,
  IonTitle,
  IonContent,
  IonList,
  IonItem,
} from "@ionic/react";
import { obtenerClasificacion } from "../../services/api";
import "./Ranking.scss";

const Ranking: React.FC = () => {
  const [clasificacion, setClasificacion] = useState<any[]>([]);

  useEffect(() => {
    cargarClasificacion();
  }, []);

  const cargarClasificacion = async () => {
    try {
      const data = await obtenerClasificacion();
      setClasificacion(data);
    } catch (error) {
      console.error("Error al obtener la clasificación:", error);
    }
  };

  return (
    <IonPage className="centeredContent">
      <IonHeader>
        <IonToolbar>
          <IonTitle>Clasificación de Usuarios</IonTitle>
        </IonToolbar>
      </IonHeader>

      <IonContent>
        <IonList>
          {clasificacion.map((u, indice) => (
            <IonItem key={u.id} className="centeredContent">
              <strong>
                {indice + 1}. {u.nombreUsuario} - Nivel: {u.nivel}, EXP: {u.exp}
              </strong>
            </IonItem>
          ))}
        </IonList>
      </IonContent>
    </IonPage>
  );
};

export default Ranking;

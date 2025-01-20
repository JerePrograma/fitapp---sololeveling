import React, { useEffect, useState } from "react";
import {
  IonPage,
  IonHeader,
  IonToolbar,
  IonTitle,
  IonContent,
  IonList,
  IonItem,
  IonButton,
} from "@ionic/react";

import {
  obtenerMisionesDiarias,
  obtenerInfoUsuario,
  actualizarProgresoMision,
} from "../../services/api";
import "./Home.scss";

const Home: React.FC = () => {
  const [misiones, setMisiones] = useState<any[]>([]);
  const [usuario, setUsuario] = useState<any>({
    nombreUsuario: "",
    nivel: 1,
    exp: 0,
  });

  useEffect(() => {
    cargarMisionesDiarias();
    cargarInfoUsuario();
  }, []);

  const cargarMisionesDiarias = async () => {
    try {
      const data = await obtenerMisionesDiarias();
      setMisiones(data); // data es un array de { misionId, titulo, meta, progreso, completado }
    } catch (error) {
      console.error("Error al obtener misiones diarias:", error);
    }
  };

  const cargarInfoUsuario = async () => {
    try {
      const data = await obtenerInfoUsuario();
      setUsuario(data);
    } catch (error) {
      console.error("Error al obtener info de usuario:", error);
    }
  };

  const manejarIncrementoProgreso = async (misionId: number) => {
    try {
      // Ahora incrementa de a 1 (en vez de 5)
      await actualizarProgresoMision(misionId, 1);
      // Luego recargamos
      cargarMisionesDiarias();
      cargarInfoUsuario();
    } catch (error) {
      console.error("Error al incrementar progreso de misión:", error);
    }
  };

  return (
    <IonPage className="centeredContent">
      <IonHeader>
        <IonToolbar>
          <IonTitle>Entrenamiento</IonTitle>
        </IonToolbar>
      </IonHeader>

      <IonContent>
        <h2>Bienvenido, {usuario.nombreUsuario}</h2>
        <p>Experiencia: {usuario.exp}</p>

        <IonList>
          {misiones.map((m) => (
            <IonItem key={m.misionId} className="centeredContent">
              <strong>
                {m.titulo}, {m.progreso}/{m.meta}
              </strong>
              {m.completado ? (
                <p style={{ color: "green" }}>Completada</p>
              ) : (
                <IonButton
                  onClick={() => manejarIncrementoProgreso(m.misionId)}
                >
                  Añadir Progreso
                </IonButton>
              )}
            </IonItem>
          ))}
        </IonList>
      </IonContent>
    </IonPage>
  );
};

export default Home;

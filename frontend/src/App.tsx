import React from "react";
import { Redirect, Route, Switch } from "react-router-dom";
import {
  IonApp,
  IonIcon,
  IonLabel,
  IonRouterOutlet,
  IonTabBar,
  IonTabButton,
  IonTabs,
  setupIonicReact,
} from "@ionic/react";
import { IonReactRouter } from "@ionic/react-router";
import { ellipse, square, triangle } from "ionicons/icons";

/* Páginas principales */
import Home from "./pages/Home/Home";
import Ranking from "./pages/Ranking/Ranking";
import Profile from "./pages/Profile/Profile";

/* Ruta de Login */
import LoginForm from "./pages/Form/LoginForm";
import RegisterForm from "./pages/Form/RegisterForm";

/* Rutas protegidas */
import ProtectedRoute from "./routes/ProtectedRoute";

/* Contexto de autenticación */
import { AuthProvider } from "./hooks/context/authContext";

/* Estilos de Ionic */
import "@ionic/react/css/core.css";
import "@ionic/react/css/normalize.css";
import "@ionic/react/css/structure.css";
import "@ionic/react/css/typography.css";
import "@ionic/react/css/padding.css";
import "@ionic/react/css/float-elements.css";
import "@ionic/react/css/text-alignment.css";
import "@ionic/react/css/text-transformation.css";
import "@ionic/react/css/flex-utils.css";
import "@ionic/react/css/display.css";
import "@ionic/react/css/palettes/dark.system.css";
import "./theme/variables.scss";

setupIonicReact();

const App: React.FC = () => {
  return (
    <AuthProvider>
      <IonApp className="myBackground">
        <IonReactRouter>
          <IonTabs>
            {/* RouterOutlet principal */}
            <IonRouterOutlet>
              <Switch>
                {/* Ruta de Login (pública) */}
                <Route exact path="/login" component={LoginForm} />
                {/* Ruta pública: Register */}
                <Route exact path="/register" component={RegisterForm} />

                {/* Rutas protegidas: requieren que isAuth sea true */}
                <ProtectedRoute exact path="/inicio" component={Home} />
                <ProtectedRoute exact path="/ranking" component={Ranking} />
                <ProtectedRoute exact path="/perfil" component={Profile} />

                {/* Redirección raíz */}
                <Route exact path="/">
                  {localStorage.getItem("token") ? (
                    <Redirect to="/inicio" />
                  ) : (
                    <Redirect to="/login" />
                  )}
                </Route>
              </Switch>
            </IonRouterOutlet>

            {/* Barra de tabs inferior */}
            <IonTabBar slot="bottom">
              <IonTabButton tab="inicio" href="/inicio">
                <IonIcon aria-hidden="true" icon={triangle} />
                <IonLabel>Inicio</IonLabel>
              </IonTabButton>

              <IonTabButton tab="ranking" href="/ranking">
                <IonIcon aria-hidden="true" icon={ellipse} />
                <IonLabel>Ranking</IonLabel>
              </IonTabButton>

              <IonTabButton tab="perfil" href="/perfil">
                <IonIcon aria-hidden="true" icon={square} />
                <IonLabel>Perfil</IonLabel>
              </IonTabButton>
            </IonTabBar>
          </IonTabs>
        </IonReactRouter>
      </IonApp>
    </AuthProvider>
  );
};

export default App;

import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import LoginPage from "../pages/LoginPage";
import Dashboard from "../pages/Dashboard";
import PrivateRoute from "./PrivateRoute";
import LogoutPage from "../pages/LogoutPage";
import RegistrarProfesorPage from '../pages/RegistrarProfesorPage';
import ImportarAlumnosPage from "../pages/ImportarAlumnosPage";
import ChangePasswordPage from "../pages/ChangePasswordPage";
import AddAlumnoPage from "../pages/AddAlumnoPage";
import DeleteAlumnoPage from "../pages/DeleteAlumnoPage";
import AssignInsigniaPage from "../pages/AssignInsigniaPage";
import RemoveInsigniaPage from "../pages/RemoveInsigniaPage";
import DefineNivelesPage from "../pages/DefineNivelesPage";
import RankingPage from "../pages/RankingPage";
import ViewAlumnoInsigniasPage from "../pages/ViewAlumnoInsigniasPage";
import BestStudentPage from "../pages/BestStudentPage";
import ForgotPasswordPage from "../pages/ForgotPasswordPage";
import ResetPasswordPage from "../pages/ResetPasswordPage";

const AppRoutes: React.FC = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/dashboard" element={
            <PrivateRoute>
              <Dashboard />
            </PrivateRoute>
          }
        />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/forgot-password" element={<ForgotPasswordPage />} />
        <Route path="/reset-password" element={<ResetPasswordPage />} />
        <Route path="/logout" element={<LogoutPage />} />
        <Route path="/registrar-profesor" element={<RegistrarProfesorPage/>}/>
        <Route path="/alumnos/import" element={<ImportarAlumnosPage />} />
        <Route path="/cambiar-contrasena" element={<ChangePasswordPage />} />
        <Route path="/alumnos/new" element={<AddAlumnoPage />} />
        <Route path="/alumnos/delete" element={<DeleteAlumnoPage />} />
        <Route path="/insignias/assign" element={<AssignInsigniaPage />} />
        <Route path="/insignias/remove" element={<RemoveInsigniaPage />} />
        <Route path="/niveles" element={<DefineNivelesPage />} />
        <Route path="/ranking" element={<RankingPage />} />
        <Route path="/alumnos/:dni" element={<ViewAlumnoInsigniasPage />} />
        <Route path="/historico/mejor" element={<BestStudentPage />} />

      </Routes>
    </Router>
  );
};

export default AppRoutes;
/*
import axios from "axios";

const API_URL = "http://localhost:8080/api";

export const login = async (
  username: string,
  password: string
): Promise<LoginResponse> => {
  const response = await axios.post<LoginResponse>(`${API_URL}/auth/login`, {
    username,
    password,
  });

  const token = response.data.token;
  localStorage.setItem("token", token);
  return response.data;
};
*/

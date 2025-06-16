// src/pages/Dashboard.tsx
import React from 'react';
import { Link } from 'react-router-dom';
import MenuButton from '../components/MenuButton';

const Dashboard: React.FC = () => {
  return (
    <div className="bg-ujaGray min-h-screen text-gray-900 font-sans">
      {/* Header with Cerrar Sesión link */}
      <header className="bg-ujaGreen text-white p-4 shadow-md">
        <div className="max-w-7xl mx-auto flex items-center justify-between">
          <h1 className="text-2xl font-bold">Sistema de Gamificación - UJA</h1>
          <Link to="/logout" className="text-sm font-medium hover:underline">
            Cerrar Sesión
          </Link>
        </div>
      </header>

      <main className="max-w-6xl mx-auto p-6 space-y-8">
        {/* Gestión de Usuarios */}
        <section>
          <h2 className="text-xl font-semibold text-ujaGreen mb-4">Gestión de Usuarios</h2>
          <div className="grid sm:grid-cols-2 lg:grid-cols-3 gap-4">
            <MenuButton icon="➕" label="Registrar Profesor" link="/registrar-profesor" />
            <MenuButton icon="📂" label="Importar Alumnos (CSV)" link="/alumnos/import" />
            <MenuButton icon="🔒" label="Cambiar Contraseña" link="/cambiar-contrasena" />
          </div>
        </section>

        {/* Gestión de Alumnos */}
        <section>
          <h2 className="text-xl font-semibold text-ujaGreen mb-4">Gestión de Alumnos</h2>
          <div className="grid sm:grid-cols-2 lg:grid-cols-2 gap-4">
            <MenuButton icon="➕" label="Añadir Alumno" link="/alumnos/new" />
            <MenuButton icon="❌" label="Eliminar Alumno" link="/alumnos/delete" />
          </div>
        </section>

        {/* Insignias y Actividades */}
        <section>
          <h2 className="text-xl font-semibold text-ujaGreen mb-4">Insignias y Actividades</h2>
          <div className="grid sm:grid-cols-2 gap-4">
            <MenuButton icon="🏅" label="Asignar Insignia" link="/insignias/assign" />
            <MenuButton icon="🗑️" label="Quitar Insignia" link="/insignias/remove" />
          </div>
        </section>

        {/* Niveles y Ranking */}
        <section>
          <h2 className="text-xl font-semibold text-ujaGreen mb-4">Niveles y Ranking</h2>
          <div className="grid sm:grid-cols-2 gap-4">
            <MenuButton icon="⚙️" label="Definir Niveles" link="/niveles" />
            <MenuButton icon="📊" label="Ver Ranking General" link="/ranking" />
          </div>
        </section>

        {/* Consultas e Histórico */}
        <section>
          <h2 className="text-xl font-semibold text-ujaGreen mb-4">Consultas e Histórico</h2>
          <div className="grid sm:grid-cols-2 gap-4">
            <MenuButton icon="🔍" label="Ver Insignias de Alumno" link="/alumnos/:dni" />
            <MenuButton icon="🏆" label="Mejor Alumno Histórico" link="/historico/mejor" />
          </div>
        </section>
      </main>

      <footer className="bg-white text-center py-4 text-sm text-gray-600">
        &copy; 2025 Universidad de Jaén - Sistema de Gamificación
      </footer>
    </div>
  );
};

export default Dashboard;

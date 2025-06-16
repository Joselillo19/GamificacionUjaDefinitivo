// src/pages/AddAlumnoPage.tsx
import React, { useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

const AddAlumnoPage: React.FC = () => {
  const [dni, setDni] = useState("");
  const [nombre, setNombre] = useState("");
  const [apellido1, setApellido1] = useState("");
  const [apellido2, setApellido2] = useState("");
  const [email, setEmail] = useState("");
  const [curso, setCurso] = useState("");
  const [message, setMessage] = useState<string>("");
  const [messageType, setMessageType] = useState<'success' | 'error' | ''>("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        "http://localhost:8080/api/alumnos/register",
        { dni, nombre, primerApellido: apellido1, segundoApellido: apellido2, correoElectronico: email, curso }
      );
      if (response.status === 200 || response.status === 201) {
        setMessageType('success');
        setMessage("✅ Alumno añadido correctamente.");
        setDni("");
        setNombre("");
        setApellido1("");
        setApellido2("");
        setEmail("");
        setCurso("");
      } else {
        setMessageType('error');
        setMessage("❌ Error al añadir el alumno.");
      }
    } catch (error: any) {
      setMessageType('error');
      setMessage(error.response?.data || "❌ Error al añadir el alumno.");
      console.error(error);
    }
  };

  return (
    <div className="bg-ujaGray min-h-screen text-gray-900 font-sans">
      {/* Header with back link */}
      <header className="bg-ujaGreen text-white p-4 shadow-md">
        <div className="max-w-7xl mx-auto flex items-center justify-between">
          <h1 className="text-2xl font-bold">Gestión de Alumnos - Añadir Alumno</h1>
          <Link to="/dashboard" className="text-sm font-medium hover:underline">
            Volver al inicio
          </Link>
        </div>
      </header>

      {/* Main form container */}
      <main className="flex justify-center pt-8 pb-8">
        <div className="w-full max-w-md p-6 bg-white rounded-lg shadow-md">
          {message && (
            <div className={`mb-4 p-3 text-sm rounded ${
              messageType === 'success' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'
            }`}>
              {message}
            </div>
          )}
          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <label htmlFor="dni" className="block text-sm font-semibold mb-1">DNI/NIE *</label>
              <input
                id="dni"
                type="text"
                value={dni}
                onChange={(e) => setDni(e.target.value)}
                required
                className="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-ujaGreen"
              />
            </div>
            <div>
              <label htmlFor="nombre" className="block text-sm font-semibold mb-1">Nombre *</label>
              <input
                id="nombre"
                type="text"
                value={nombre}
                onChange={(e) => setNombre(e.target.value)}
                required
                className="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-ujaGreen"
              />
            </div>
            <div>
              <label htmlFor="apellido1" className="block text-sm font-semibold mb-1">Primer Apellido *</label>
              <input
                id="apellido1"
                type="text"
                value={apellido1}
                onChange={(e) => setApellido1(e.target.value)}
                required
                className="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-ujaGreen"
              />
            </div>
            <div>
              <label htmlFor="apellido2" className="block text-sm font-semibold mb-1">Segundo Apellido *</label>
              <input
                id="apellido2"
                type="text"
                value={apellido2}
                onChange={(e) => setApellido2(e.target.value)}
                className="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-ujaGreen"
              />
            </div>
            <div>
              <label htmlFor="email" className="block text-sm font-semibold mb-1">Correo UJA *</label>
              <input
                id="email"
                type="email"
                value={email}
                //pattern="[A-Za-z0-9._%+\-]+@(red\.)?ujaen\.es"
                onChange={(e) => setEmail(e.target.value)}
                required
                className="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-ujaGreen"
              />
              <p className="text-xs text-gray-500 mt-1">Solo correos terminados en @ujaen.es o @red.ujaen.es</p>
            </div>
            <div>
              <label htmlFor="curso" className="block text-sm font-semibold mb-1">Curso *</label>
              <input
                id="curso"
                type="text"
                value={curso}
                onChange={(e) => setCurso(e.target.value)}
                placeholder="Ej: 2024-25"
                required
                className="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-ujaGreen"
              />
              <p className="text-xs text-gray-500 mt-1">Formato: YYYY-YY (p.ej., 2024-25)</p>
            </div>
            <button
              type="submit"
              className="w-full bg-ujaGreen text-white py-2 rounded hover:bg-green-800 transition"
            >
              Añadir Alumno
            </button>
          </form>
        </div>
      </main>
    </div>
  );
};

export default AddAlumnoPage;

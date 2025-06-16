/* eslint-disable no-template-curly-in-string */
import React, { useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

const DeleteAlumnoPage: React.FC = () => {
  const [identifier, setIdentifier] = useState("");
  const [message, setMessage] = useState<string>("");
  const [messageType, setMessageType] = useState<"success" | "error" | "">("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    // Confirmación antes de eliminar
    if (
      !window.confirm(
        "¿Estás seguro de que deseas eliminar este alumno? Esta acción no se puede deshacer."
      )
    ) {
      return;
    }

    try {
      const response = await axios.delete(
        `http://localhost:8080/api/alumnos/${encodeURIComponent(identifier)}`
      );

      if (response.status === 200) {
        setMessageType("success");
        setMessage("✅ Alumno eliminado correctamente.");
        setIdentifier("");
      } else {
        setMessageType("error");
        setMessage("❌ Error al eliminar el alumno.");
      }
    } catch (error: any) {
      setMessageType("error");
      if (error.response?.status === 404) {
        setMessage("❌ Alumno no encontrado.");
      } else {
        setMessage("❌ Error al eliminar el alumno.");
      }
      console.error(error);
    }
  };

  return (
    <div className="bg-ujaGray min-h-screen text-gray-900 font-sans">
      {/* Header with back link */}
      <header className="bg-ujaGreen text-white p-4 shadow-md">
        <div className="max-w-7xl mx-auto flex items-center justify-between">
          <h1 className="text-2xl font-bold">
            Gestión de Alumnos - Eliminar Alumno
          </h1>
          <Link to="/dashboard" className="text-sm font-medium hover:underline">
            Volver al inicio
          </Link>
        </div>
      </header>

      {/* Main container */}
      <main className="flex justify-center pt-8 pb-8">
        <div className="w-full max-w-lg p-6 bg-white rounded-lg shadow-md border-t-8 border-ujaGreen">
          {message && (
            <div
              className={`mb-4 p-3 text-sm rounded ${
                messageType === "success"
                  ? "bg-green-100 text-green-800"
                  : "bg-red-100 text-red-800"
              }`}
            >
              {message}
            </div>
          )}

          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <label
                htmlFor="identificador"
                className="block text-sm font-semibold mb-1"
              >
                Buscar por DNI o nombre completo:
              </label>
              <input
                id="identificador"
                type="text"
                value={identifier}
                onChange={(e) => setIdentifier(e.target.value)}
                placeholder="Ej: 12345678A o Juan Pérez"
                required
                className="w-full p-2 border rounded focus:outline-none focus:ring-2 focus:ring-ujaGreen"
              />
            </div>
            <button
              type="submit"
              className="w-full bg-red-600 text-white py-2 rounded hover:bg-red-700 transition"
            >
              Eliminar Alumno
            </button>
          </form>
        </div>
      </main>
    </div>
  );
};

export default DeleteAlumnoPage;

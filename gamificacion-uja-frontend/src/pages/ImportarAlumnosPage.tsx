// src/pages/ImportarAlumnosPage.tsx
import React, { useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

const ImportarAlumnosPage: React.FC = () => {
  const [file, setFile] = useState<File | null>(null);
  const [message, setMessage] = useState<string>("");

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files.length > 0) {
      setFile(e.target.files[0]);
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!file) {
      setMessage("❌ Por favor selecciona un archivo CSV.");
      return;
    }

    const formData = new FormData();
    formData.append("file", file); // Cambiado a 'file' para coincidir con @RequestParam("file") del back
    formData.append("rol", "ADMIN");

    try {
      const response = await axios.post(
        "http://localhost:8080/api/alumnos/csv/upload",
        formData,
        {
          headers: { "Content-Type": "multipart/form-data" },
        }
      );
      if (response.status === 200) {
        setMessage("✅ Alumnos importados correctamente.");
      } else {
        setMessage("❌ Error al importar alumnos.");
      }
    } catch (error: any) {
      console.error(error);
      setMessage("❌ Error al importar alumnos.");
    }
  };

  return (
    <div className="bg-ujaGray min-h-screen text-gray-900 font-sans">
      {/* Header with return link */}
      <header className="bg-ujaGreen text-white p-4 shadow-md">
        <div className="max-w-7xl mx-auto flex items-center justify-between">
          <h1 className="text-2xl font-bold">Gestión de Alumnos - Cargar Listado</h1>
          <Link to="/dashboard" className="text-sm font-medium hover:underline">
            Volver al inicio
          </Link>
        </div>
      </header>

      {/* Main container with some top padding */}
      <main className="flex justify-center pt-8 pb-8">
        <div className="w-full max-w-lg p-8 bg-white rounded-lg shadow-md">
          {message && (
            <div className="mb-4 text-center text-sm font-medium text-green-700">
              {message}
            </div>
          )}

          <h2 className="text-xl font-semibold mb-4">Subir fichero CSV con datos de alumnos</h2>

          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <label htmlFor="archivo" className="block text-sm font-semibold mb-1">
                Selecciona archivo .csv:
              </label>
              <input
                type="file"
                id="archivo"
                accept=".csv"
                onChange={handleFileChange}
                className="w-full"
                required
              />
            </div>

            {/* Informational banner moved between input and button */}
            <div className="bg-gray-100 p-4 rounded text-sm text-gray-700">
              Se enviará un correo a todos los alumnos para que puedan cambiar su contraseña y demás.
            </div>

            <button
              type="submit"
              className="w-full bg-ujaGreen text-white py-2 rounded hover:bg-green-800 transition"
            >
              Cargar
            </button>
          </form>
        </div>
      </main>
    </div>
  );
};

export default ImportarAlumnosPage;

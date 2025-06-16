// src/pages/AssignInsigniaPage.tsx
import React, { useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

const activityOptions = [
  "Realizar ejercicios en pizarra",
  "Responder preguntas en clase",
  "Realizar test en platea",
  "Realizar ejercicios evaluables/revisados por el profesor"
];


const AssignInsigniaPage: React.FC = () => {
  const [identifier, setIdentifier] = useState("");
  const [actividad, setActividad] = useState(activityOptions[0]);
  const [insignia, setInsignia] = useState("Oro");
  const [message, setMessage] = useState<string>("");
  const [messageType, setMessageType] = useState<'success' | 'error' | ''>("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    // Confirmación antes de asignar
    if (!window.confirm("¿Estás seguro de que deseas asignar esta insignia?")) {
      return;
    }

    try {
      const response = await axios.post(
        "http://localhost:8080/api/alumnos/insignias/asignar",
        { identificador: identifier, actividad, tipoInsignia: insignia }
      );
      if (response.status === 200) {
        setMessageType('success');
        setMessage("✅ Insignia asignada correctamente.");
        setIdentifier("");
        setActividad(activityOptions[0]);
        setInsignia("Oro");
      } else {
        setMessageType('error');
        setMessage("❌ Error al asignar la insignia.");
      }
    } catch (error: any) {
      setMessageType('error');
      setMessage("❌ Error al asignar la insignia.");
      console.error(error);
    }
  };

  return (
    <div className="bg-ujaGray min-h-screen text-gray-900 font-sans">
      {/* Header with back link */}
      <header className="bg-ujaGreen text-white p-4 shadow-md">
        <div className="max-w-7xl mx-auto flex items-center justify-between">
          <h1 className="text-2xl font-bold">Insignias y Actividades - Asignar Insignia</h1>
          <Link to="/dashboard" className="text-sm font-medium hover:underline">
            Volver al inicio
          </Link>
        </div>
      </header>

      {/* Main form container */}
      <main className="flex justify-center pt-8 pb-8">
        <div className="w-full max-w-lg p-6 bg-white rounded-2xl shadow-md border border-gray-200">
          {message && (
            <div className={`mb-4 p-3 text-sm rounded ${
              messageType === 'success' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'
            }`}>
              {message}
            </div>
          )}
          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <label htmlFor="identificador" className="block text-sm font-medium mb-1">
                DNI o Nombre del Alumno
              </label>
              <input
                id="identificador"
                type="text"
                value={identifier}
                onChange={(e) => setIdentifier(e.target.value)}
                placeholder="Ej: 12345678A o Juan Pérez"
                required
                className="w-full border-gray-300 rounded-xl shadow-sm focus:ring-ujaGreen focus:border-ujaGreen p-2"
              />
            </div>
            <div>
              <label htmlFor="actividad" className="block text-sm font-medium mb-1">
                Actividad
              </label>
              <select
                id="actividad"
                value={actividad}
                onChange={(e) => setActividad(e.target.value)}
                required
                className="w-full border-gray-300 rounded-xl shadow-sm focus:ring-ujaGreen focus:border-ujaGreen p-2"
              >
                {activityOptions.map((act) => (
                  <option key={act} value={act}>
                    {act}
                  </option>
                ))}
              </select>
            </div>
            <div>
              <label htmlFor="insignia" className="block text-sm font-medium mb-1">
                Insignia
              </label>
              <select
                id="insignia"
                value={insignia}
                onChange={(e) => setInsignia(e.target.value)}
                className="w-full border-gray-300 rounded-xl shadow-sm focus:ring-ujaGreen focus:border-ujaGreen p-2"
              >
                <option>Oro</option>
                <option>Plata</option>
                <option>Bronce</option>
              </select>
            </div>
            <button
              type="submit"
              className="w-full bg-ujaGreen text-white py-2 px-4 rounded-xl hover:bg-green-700 transition"
            >
              Asignar
            </button>
          </form>
        </div>
      </main>
    </div>
  );
};

export default AssignInsigniaPage;

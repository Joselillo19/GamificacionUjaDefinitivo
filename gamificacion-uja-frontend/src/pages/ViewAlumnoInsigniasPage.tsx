// src/pages/ViewAlumnoInsigniasPage.tsx
import React, { useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

interface InsigniaRecord {
  actividad: string;
  tipoInsignia: string;
}

const ViewAlumnoInsigniasPage: React.FC = () => {
  const [query, setQuery] = useState("");
  const [insignias, setInsignias] = useState<InsigniaRecord[]>([]);
  const [nivel, setNivel] = useState<number | null>(null);
  const [puntos, setPuntos] = useState<number | null>(null);
  const [posicion, setPosicion] = useState<number | null>(null);
  const [error, setError] = useState<string>("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");
    setInsignias([]);
    setNivel(null);
    setPuntos(null);
    setPosicion(null);
    try {
      const response = await axios.get(
  `http://localhost:8080/api/alumnos/insignias/${encodeURIComponent(query)}`
);
      

      // Esperamos { insignias: [...], nivel: number, puntos: number, posicion: number }
      const data = response.data;
      setInsignias(data.insignias);
      setNivel(data.nivel);
      setPuntos(data.puntos);
      setPosicion(data.posicion);
    } catch (err: any) {
      setError("❌ No se encontraron datos para ese alumno.");
      console.error(err);
    }
  };

  return (
    <div className="bg-ujaGray min-h-screen text-gray-900 font-sans">
      {/* Header */}
      <header className="bg-ujaGreen text-white p-4 shadow-md">
        <div className="max-w-7xl mx-auto flex items-center justify-between">
          <h1 className="text-2xl font-bold">
            Consultas e Histórico - Insignias del Alumno
          </h1>
          <Link to="/dashboard" className="text-sm font-medium hover:underline">
            Volver al inicio
          </Link>
        </div>
      </header>

      {/* Main content */}
      <main className="flex justify-center pt-8 pb-8">
        <div className="w-full max-w-2xl p-6 bg-white rounded-2xl shadow-md border border-gray-200">
          <form onSubmit={handleSubmit} className="mb-6 space-y-4">
            <div>
              <label htmlFor="query" className="block text-sm font-medium mb-1">
                Buscar por DNI o Nombre
              </label>
              <input
                id="query"
                type="text"
                value={query}
                onChange={(e) => setQuery(e.target.value)}
                placeholder="Ej: 12345678A o Ana García"
                required
                className="w-full border-gray-300 rounded-xl shadow-sm focus:ring-ujaGreen focus:border-ujaGreen p-2"
              />
            </div>
            <button
              type="submit"
              className="w-full bg-ujaGreen text-white py-2 px-4 rounded-xl hover:bg-green-700 transition"
            >
              Buscar
            </button>
          </form>

          {error && <p className="text-red-600 text-center mb-4">{error}</p>}

          {insignias.length > 0 && (
            <div className="bg-gray-100 p-4 rounded-xl shadow-sm border border-gray-300">
              <h2 className="text-lg font-semibold mb-2">Resultados:</h2>
              <ul className="list-disc pl-5 space-y-1">
                {insignias.map((rec, idx) => (
                  <li key={idx}>
                    <strong>{rec.actividad}:</strong> Insignia de{" "}
                    {rec.tipoInsignia}
                  </li>
                ))}
              </ul>
              <div className="mt-4 text-sm space-y-1">
                <p>
                  <strong>Nivel:</strong> {nivel}
                </p>
                <p>
                  <strong>Puntos:</strong> {puntos}
                </p>
                <p>
                  <strong>Posición en Ranking:</strong> {posicion}
                </p>
              </div>
            </div>
          )}
        </div>
      </main>
    </div>
  );
};

export default ViewAlumnoInsigniasPage;

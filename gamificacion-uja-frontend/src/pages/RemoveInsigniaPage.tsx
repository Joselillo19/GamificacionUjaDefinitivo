// src/pages/RemoveInsigniaPage.tsx
import React, { useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import { AlumnoInsigniasDTO, InsigniaInfo } from "../types/alumno";

interface InsigniaRecord {
  id: number;
  actividad: string;
  tipoInsignia: string;
}

const RemoveInsigniaPage: React.FC = () => {
  const [identifier, setIdentifier] = useState("");
  const [insignias, setInsignias] = useState<InsigniaRecord[]>([]);
  const [message, setMessage] = useState<string>("");
  const [messageType, setMessageType] = useState<"success" | "error" | "">("");
  const [loading, setLoading] = useState<boolean>(false);

  const fetchInsignias = async () => {
    setLoading(true);
    setMessage("");
    try {
      const resp = await axios.get<AlumnoInsigniasDTO>(
        `http://localhost:8080/api/alumnos/${encodeURIComponent(
          identifier
        )}/insignias`
      );

      console.log("Datos recibidos del back:", resp.data);

      // Asegurarnos de que insignias es un array
      const listaInsignias = Array.isArray(resp.data.insignias)
        ? resp.data.insignias
        : [];

      // Mapear al formato InsigniaRecord
      const registros = listaInsignias.map((ai: InsigniaInfo) => ({
        id: ai.id,
        actividad: ai.actividad,
        tipoInsignia: ai.tipoInsignia,
      }));

      setInsignias(registros);

      if (registros.length === 0) {
        setMessageType("error");
        setMessage("ℹ️ No hay insignias asignadas para este alumno.");
      }
    } catch (err: any) {
      setMessageType("error");
      setMessage("❌ No se pudieron obtener las insignias.");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault();
    if (!identifier) return;
    fetchInsignias();
  };

  const handleRemove = async (rec: InsigniaRecord) => {
    if (
      !window.confirm(
        `¿Seguro que quieres quitar la insignia ${rec.tipoInsignia} de '${rec.actividad}'?`
      )
    ) {
      return;
    }
    try {
      await axios.delete(
        `http://localhost:8080/api/alumnos/insignias/${rec.id}`
      );
      setMessageType("success");
      setMessage("✅ Insignia quitada correctamente.");
      fetchInsignias();
    } catch (err: any) {
      setMessageType("error");
      if (err.response?.status === 404) {
        setMessage("❌ Asignación no encontrada.");
      } else {
        setMessage("❌ Error al quitar la insignia.");
      }
      console.error(err);
    }
  };

  return (
    <div className="bg-ujaGray min-h-screen text-gray-900 font-sans">
      <header className="bg-ujaGreen text-white p-4 shadow-md">
        <div className="max-w-7xl mx-auto flex items-center justify-between">
          <h1 className="text-2xl font-bold">
            Insignias y Actividades - Quitar Insignia
          </h1>
          <Link to="/dashboard" className="text-sm font-medium hover:underline">
            Volver al inicio
          </Link>
        </div>
      </header>

      <main className="flex flex-col items-center pt-8 pb-8 space-y-6">
        <form
          onSubmit={handleSearch}
          className="w-full max-w-lg p-6 bg-white rounded-2xl shadow-md border border-gray-200 space-y-4"
        >
          <label htmlFor="identificador" className="block text-sm font-medium">
            DNI o Nombre del Alumno
          </label>
          <div className="flex gap-2">
            <input
              id="identificador"
              type="text"
              value={identifier}
              onChange={(e) => setIdentifier(e.target.value)}
              placeholder="Ej: 12345678A o Juan Pérez"
              required
              className="flex-grow border-gray-300 rounded-xl shadow-sm focus:ring-ujaGreen focus:border-ujaGreen p-2"
            />
            <button
              type="submit"
              className="bg-ujaGreen text-white py-2 px-4 rounded-xl hover:bg-green-700 transition"
            >
              Buscar
            </button>
          </div>
        </form>

        {message && (
          <div
            className={`w-full max-w-lg p-4 text-sm rounded ${
              messageType === "success"
                ? "bg-green-100 text-green-800"
                : "bg-red-100 text-red-800"
            }`}
          >
            {message}
          </div>
        )}

        {loading ? (
          <p>Cargando insignias...</p>
        ) : (
          insignias.length > 0 && (
            <div className="w-full max-w-lg overflow-x-auto">
              <table className="w-full table-auto border-collapse">
                <thead className="bg-ujaGreen text-white">
                  <tr>
                    <th className="px-4 py-2 text-left">Actividad</th>
                    <th className="px-4 py-2 text-left">Insignia</th>
                    <th className="px-4 py-2">Acción</th>
                  </tr>
                </thead>
                <tbody className="bg-white divide-y divide-gray-200">
                  {insignias.map((rec) => (
                    <tr key={rec.id}>
                      <td className="px-4 py-2">{rec.actividad}</td>
                      <td className="px-4 py-2">{rec.tipoInsignia}</td>
                      <td className="px-4 py-2 text-center">
                        <button
                          onClick={() => handleRemove(rec)}
                          className="bg-red-600 text-white py-1 px-3 rounded hover:bg-red-700 transition text-sm"
                        >
                          Quitar
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )
        )}
      </main>
    </div>
  );
};

export default RemoveInsigniaPage;

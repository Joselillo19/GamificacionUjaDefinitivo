// src/pages/DefineNivelesPage.tsx
import React, { useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

interface Nivel {
  id?: number;
  min: number;
  max: number;
}

const DefineNivelesPage: React.FC = () => {
  // Inicialmente sin id; los obtendremos del back
  const [niveles, setNiveles] = useState<Nivel[]>([
    { min: 0, max: 49 },
    { min: 50, max: 99 },
    { min: 100, max: 150 },
    { min: 151, max: 200 },
  ]);
  const [message, setMessage] = useState<string>("");
  const [messageType, setMessageType] = useState<"success" | "error" | "">(
    ""
  );
  const [loading, setLoading] = useState(false);

  const handleChange = (index: number, field: "min" | "max", value: number) => {
    const updated = [...niveles];
    updated[index] = { ...updated[index], [field]: value };
    if (field === "max" && index + 1 < updated.length) {
      updated[index + 1].min = value + 1;
    }
    setNiveles(updated);
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setMessage("");
    setMessageType("");

    try {
      // 1) Obtengo niveles existentes
      const resp = await axios.get<Nivel[]>("http://localhost:8080/api/niveles");
      const existentes = resp.data; // cada uno tiene id y nombre

      // 2) Hago el upsert
      await Promise.all(
        niveles.map((nivel, idx) => {
          const nombre = `Nivel ${idx + 1}`;
          // Busco en existentes
          const found = existentes.find((n) => n.id !== undefined && n.id !== null && n.id >= 0 && n && (n as any).nombre === nombre);
          // Construyo payload
          const payload = {
            nombre,
            puntosMinimos: nivel.min,
            puntosMaximos: nivel.max,
          };
          if (found && (found as any).id) {
            // PUT si existe
            const id = (found as any).id;
            return axios.put(`http://localhost:8080/api/niveles/${id}`, payload);
          } else {
            // POST si no existe
            return axios.post("http://localhost:8080/api/niveles", payload);
          }
        })
      );

      setMessageType("success");
      setMessage("✅ Niveles creados/actualizados correctamente.");
    } catch (error: any) {
      console.error(error);
      setMessageType("error");
      setMessage("❌ Error al guardar los niveles.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="bg-ujaGray min-h-screen text-gray-900 font-sans">
      <header className="bg-ujaGreen text-white p-4 shadow-md">
        <div className="max-w-7xl mx-auto flex items-center justify-between">
          <h1 className="text-2xl font-bold">
            Niveles y Ranking - Definir Niveles
          </h1>
          <Link to="/dashboard" className="text-sm font-medium hover:underline">
            Volver al inicio
          </Link>
        </div>
      </header>

      <main className="flex justify-center pt-8 pb-8">
        <div className="w-full max-w-lg p-6 bg-white rounded-2xl shadow-md border border-gray-200">
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
          <form onSubmit={handleSubmit} className="space-y-6">
            {niveles.map((nivel, idx) => (
              <fieldset
                key={idx}
                className="border border-gray-300 rounded-xl p-4"
              >
                <legend className="font-semibold text-ujaGreen mb-2 px-2">
                  Nivel {idx + 1}
                </legend>
                <div className="flex gap-4">
                  <div className="flex flex-col w-1/2">
                    <label
                      htmlFor={`nivel${idx}-min`}
                      className="block text-sm font-medium mb-1"
                    >
                      Mínimo
                    </label>
                    <input
                      id={`nivel${idx}-min`}
                      type="number"
                      value={nivel.min}
                      onChange={(e) =>
                        handleChange(idx, "min", Number(e.target.value))
                      }
                      required
                      className="border-gray-300 rounded-xl shadow-sm focus:ring-ujaGreen focus:border-ujaGreen p-2"
                    />
                  </div>
                  <div className="flex flex-col w-1/2">
                    <label
                      htmlFor={`nivel${idx}-max`}
                      className="block text-sm font-medium mb-1"
                    >
                      Máximo
                    </label>
                    <input
                      id={`nivel${idx}-max`}
                      type="number"
                      value={nivel.max}
                      onChange={(e) =>
                        handleChange(idx, "max", Number(e.target.value))
                      }
                      required
                      className="border-gray-300 rounded-xl shadow-sm focus:ring-ujaGreen focus:border-ujaGreen p-2"
                    />
                  </div>
                </div>
              </fieldset>
            ))}
            <button
              type="submit"
              disabled={loading}
              className="w-full bg-ujaGreen text-white py-2 px-4 rounded-xl hover:bg-green-700 transition"
            >
              {loading ? "Guardando..." : "Guardar Niveles"}
            </button>
          </form>
        </div>
      </main>
    </div>
  );
};

export default DefineNivelesPage;

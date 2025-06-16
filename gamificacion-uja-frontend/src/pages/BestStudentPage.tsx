// src/pages/BestStudentPage.tsx
import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

interface Insignias { oro: number; plata: number; bronce: number; }
interface MejorAlumno {
  nombre: string;
  dni: string;
  insignias: Insignias;
  nivel: string;
  puntuacion: number;
  posicion: number;
}

const BestStudentPage: React.FC = () => {
  const [curso, setCurso] = useState<string>("");
  const [cursos, setCursos] = useState<string[]>([]);
  const [topAlumnos, setTopAlumnos] = useState<MejorAlumno[]>([]);
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string>("");
  const [loadingCursos, setLoadingCursos] = useState<boolean>(true);
  const [errorCursos, setErrorCursos] = useState<string>("");

  // Carga dinámica de cursos disponibles
 useEffect(() => {
  const fetchCursos = async () => {
    try {
      const response = await axios.get<string[]>(
        "http://localhost:8080/api/historico/cursos"
      );
      setCursos(response.data);
    } catch (err: any) {
      setErrorCursos("❌ Error al cargar la lista de cursos.");
      console.error(err);
    } finally {
      setLoadingCursos(false);
    }
  };
  fetchCursos();
}, []);


  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");
    setTopAlumnos([]);
    setLoading(true);
    try {
      const response = await axios.get<MejorAlumno[]>(
        `http://localhost:8080/api/historico/mejor?curso=${encodeURIComponent(curso)}`
      );
      // Esperamos un array ordenado by posicion
      setTopAlumnos(response.data.slice(0, 3));
    } catch (err: any) {
      setError("❌ Error al consultar el mejor alumno.");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="bg-ujaGray min-h-screen text-gray-900 font-sans">
      {/* Header */}
      <header className="bg-ujaGreen text-white p-4 shadow-md">
        <div className="max-w-7xl mx-auto flex items-center justify-between">
          <h1 className="text-2xl font-bold">Consultas e Histórico - Top 3 Alumnos</h1>
          <Link to="/dashboard" className="text-sm font-medium hover:underline">
            Volver al inicio
          </Link>
        </div>
      </header>

      {/* Main content */}
      <main className="flex justify-center pt-8 pb-8">
        <div className="w-full max-w-lg p-6 bg-white rounded-2xl shadow-md border border-gray-200">
          {loadingCursos ? (
            <p className="text-center">Cargando cursos...</p>
          ) : errorCursos ? (
            <p className="text-center text-red-600">{errorCursos}</p>
          ) : (
            <form onSubmit={handleSubmit} className="space-y-4">
              <div>
                <label htmlFor="curso" className="block text-sm font-medium mb-1">
                  Selecciona el curso:
                </label>
                <select
                  id="curso"
                  value={curso}
                  onChange={(e) => setCurso(e.target.value)}
                  required
                  className="w-full border-gray-300 rounded-xl shadow-sm focus:ring-ujaGreen focus:border-ujaGreen p-2"
                >
                  <option value="" disabled>-- Elige un curso --</option>
                  {cursos.map((c) => (
                    <option key={c} value={c}>{c}</option>
                  ))}
                </select>
              </div>
              <button
                type="submit"
                disabled={!curso}
                className="w-full bg-ujaGreen text-white py-2 px-4 rounded-xl hover:bg-green-700 transition disabled:opacity-50"
              >
                Consultar Top 3
              </button>
            </form>
          )}

          {loading && <p className="mt-4 text-center">Cargando...</p>}
          {error && <p className="mt-4 text-center text-red-600">{error}</p>}

          {topAlumnos.length > 0 && (
            <div className="resultado mt-6 border-t pt-4 text-sm space-y-4">
              <h2 className="text-lg font-semibold">Top 3 del curso {curso}</h2>
              {topAlumnos.map((alumno, idx) => (
                <div key={idx} className="alumno-info">
                  <p><strong>Posición {alumno.posicion}:</strong> {alumno.nombre} ({alumno.dni})</p>
                  <p>Insignias - Oro: {alumno.insignias.oro}, Plata: {alumno.insignias.plata}, Bronce: {alumno.insignias.bronce}</p>
                  <p>Nivel: {alumno.nivel}, Puntos: {alumno.puntuacion}</p>
                </div>
              ))}
            </div>
          )}
        </div>
      </main>
    </div>
  );
};

export default BestStudentPage;

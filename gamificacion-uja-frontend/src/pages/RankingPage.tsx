// src/pages/RankingPage.tsx
import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

interface AlumnoRanking {
  posicion: number;
  nombre: string;
  dni: string;
  puntos: number;
  nivel: number;
}

const RankingPage: React.FC = () => {
  const [ranking, setRanking] = useState<AlumnoRanking[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string>("");

  useEffect(() => {
    const fetchRanking = async () => {
      try {
        const response = await axios.get<AlumnoRanking[]>(
          "http://localhost:8080/api/ranking"
        );
        setRanking(response.data);
      } catch (err: any) {
        setError("❌ Error al cargar el ranking.");
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchRanking();
  }, []);

  return (
    <div className="bg-ujaGray min-h-screen text-gray-900 font-sans">
      {/* Header */}
      <header className="bg-ujaGreen text-white p-4 shadow-md">
        <div className="max-w-7xl mx-auto flex items-center justify-between">
          <h1 className="text-2xl font-bold">Niveles y Ranking - Ranking General</h1>
          <Link to="/dashboard" className="text-sm font-medium hover:underline">
            Volver al inicio
          </Link>
        </div>
      </header>

      {/* Main content */}
      <main className="flex justify-center pt-8 pb-8">
        <div className="w-full max-w-3xl p-6 bg-white rounded-2xl shadow-md border border-gray-200">
          {loading ? (
            <p className="text-center">Cargando ranking...</p>
          ) : error ? (
            <p className="text-center text-red-600">{error}</p>
          ) : (
            // Scrollable table container
            <div className="overflow-x-auto">
              <div className="max-h-[400px] overflow-y-auto border border-gray-300 rounded-lg">
                <table className="w-full table-auto border-collapse">
                  <thead className="bg-ujaGreen text-white sticky top-0">
                    <tr>
                      <th className="px-4 py-2 text-left">Posición</th>
                      <th className="px-4 py-2 text-left">Nombre</th>
                      <th className="px-4 py-2 text-left">DNI</th>
                      <th className="px-4 py-2 text-left">Puntos</th>
                      <th className="px-4 py-2 text-left">Nivel</th>
                    </tr>
                  </thead>
                  <tbody className="bg-white divide-y divide-gray-200">
                    {ranking.map((alumno) => (
                      <tr key={alumno.posicion}>
                        <td className="px-4 py-2">{alumno.posicion}</td>
                        <td className="px-4 py-2">{alumno.nombre}</td>
                        <td className="px-4 py-2">{alumno.dni}</td>
                        <td className="px-4 py-2">{alumno.puntos}</td>
                        <td className="px-4 py-2">{alumno.nivel}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          )}
        </div>
      </main>
    </div>
  );
};

export default RankingPage;

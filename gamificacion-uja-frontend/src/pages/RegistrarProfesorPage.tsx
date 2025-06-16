// src/pages/RegistrarProfesorPage.tsx
import { useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

const RegistrarProfesorPage: React.FC = () => {
  const [nombre, setNombre] = useState("");
  const [apellidos, setApellidos] = useState("");
  const [dni, setDni] = useState("");
  const [email, setEmail] = useState("");
  const [mensaje, setMensaje] = useState("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        "http://localhost:8080/api/profesores/registrar-profesor",
        { nombre, apellidos, dni, correoUja: email, usuario: email },

        {
          headers: {
            Authorization: "Bearer fake-token",
          },
        }
      );

      if (response.status === 200 || response.status === 201) {
        setMensaje("✅ Profesor registrado correctamente.");
        setNombre("");
        setApellidos("");
        setDni("");
        setEmail("");
      }
    } catch (error: any) {
      setMensaje("❌ Error al registrar el profesor.");
      console.error("Error 500 detalle:", error.response?.data);
    }
  };

  return (
    <div className="bg-ujaGray min-h-screen text-gray-900 font-sans flex flex-col">
      {/* Header similar to Dashboard */}
      <header className="bg-ujaGreen text-white p-4 shadow-md">
        <div className="max-w-7xl mx-auto flex items-center justify-between">
          <h1 className="text-2xl font-bold">
            Gestión de Usuarios - Registrar profesorado
          </h1>
          {/* Volver al inicio link */}
          <Link to="/dashboard" className="text-sm font-medium hover:underline">
            Volver al inicio
          </Link>
        </div>
      </header>

      {/* Centrar el formulario verticalmente con espacio superior */}
      <main className="flex-grow flex items-center justify-center">
        <div className="w-full max-w-xl p-8 bg-white rounded-lg shadow-md">
          {mensaje && (
            <div className="mb-4 text-center text-sm font-medium text-green-700">
              {mensaje}
            </div>
          )}

          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <label className="block text-sm font-semibold mb-1">Nombre *</label>
              <input
                type="text"
                value={nombre}
                onChange={(e) => setNombre(e.target.value)}
                className="w-full p-2 border rounded"
                required
              />
            </div>
            <div>
              <label className="block text-sm font-semibold mb-1">
                Apellidos
              </label>
              <input
                type="text"
                value={apellidos}
                onChange={(e) => setApellidos(e.target.value)}
                className="w-full p-2 border rounded"
                required
              />
            </div>
            <div>
              <label className="block text-sm font-semibold mb-1">DNI *</label>
              <input
                type="text"
                value={dni}
                onChange={(e) => setDni(e.target.value)}
                className="w-full p-2 border rounded"
                required
              />
            </div>
            <div>
              <label className="block text-sm font-semibold mb-1">
                Correo UJA
              </label>
              <input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                pattern="[A-Za-z0-9._%+\-]+@(red\.)?ujaen\.es"
                className="w-full p-2 border rounded"
                required
              />

              <p className="text-sm text-gray-500 mt-1">
                Solo se permiten correos que terminen en @ujaen.es o en
                @red.ujaen.es
              </p>
            </div>
            <button
              type="submit"
              className="w-full bg-ujaGreen text-white py-2 rounded hover:bg-green-800 transition"
            >
              Registrar Profesor
            </button>
          </form>
        </div>
      </main>
    </div>
  );
};

export default RegistrarProfesorPage;

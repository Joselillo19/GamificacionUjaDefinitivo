import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { login } from "../services/authService";

const LoginPage: React.FC = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError("");
    try {
      await login(username, password);
      navigate("/dashboard");
    } catch (err: any) {
      setError(err.response?.data?.message || "Usuario o contraseña incorrectos");
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-ujaGray px-4">
      <form
        onSubmit={handleSubmit}
        className="bg-white p-8 rounded-xl shadow-md w-full max-w-md"
      >
        <h2 className="text-2xl font-bold text-ujaGreen mb-6 text-center">Iniciar Sesión</h2>
        {error && <p className="text-red-600 mb-4 text-center">{error}</p>}
        <div className="mb-4">
          <label htmlFor="username" className="block mb-1 text-sm font-medium">
            Nombre de usuario
          </label>
          <input
            id="username"
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            className="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-ujaGreen"
            required
          />
        </div>
        <div className="mb-6">
          <label htmlFor="password" className="block mb-1 text-sm font-medium">
            Contraseña
          </label>
          <input
            id="password"
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            className="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-ujaGreen"
            required
          />
        </div>
        <button
          type="submit"
          className="w-full bg-ujaGreen text-white py-2 rounded hover:bg-green-800 transition"
        >
          Entrar
        </button>
        {/* Enlace para recuperación de contraseña */}
        <div className="mt-4 text-center">
          <Link
            to="/forgot-password"
            className="text-sm text-ujaGreen hover:underline"
          >
            ¿Has olvidado tu contraseña?
          </Link>
        </div>
      </form>
    </div>
  );
};

export default LoginPage;

// src/pages/ChangePasswordPage.tsx
import React, { useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

const ChangePasswordPage: React.FC = () => {
  const [currentPassword, setCurrentPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [message, setMessage] = useState<string>("");
  const [messageType, setMessageType] = useState<'success' | 'error' | ''>("");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (newPassword !== confirmPassword) {
      setMessageType('error');
      setMessage("❌ La nueva contraseña y la confirmación no coinciden.");
      return;
    }

    try {
      const token = localStorage.getItem("token");
      const response = await axios.post(
        "http://localhost:8080/api/auth/change-password",
        { currentPassword, newPassword },
        { headers: { Authorization: `Bearer ${token}` } }
      );

      if (response.status === 200) {
        setMessageType('success');
        setMessage("✅ Contraseña actualizada correctamente.");
        setCurrentPassword("");
        setNewPassword("");
        setConfirmPassword("");
      } else {
        setMessageType('error');
        setMessage("❌ Error al actualizar la contraseña.");
      }
    } catch (error: any) {
      setMessageType('error');
      setMessage("❌ Error al actualizar la contraseña.");
      console.error(error);
    }
  };

  return (
    <div className="bg-ujaGray min-h-screen text-gray-900 font-sans">
      {/* Header */}
      <header className="bg-ujaGreen text-white p-4 shadow-md">
        <div className="max-w-7xl mx-auto flex items-center justify-between">
          <h1 className="text-2xl font-bold">Cambiar Contraseña</h1>
          <Link to="/dashboard" className="text-sm font-medium hover:underline">
            Volver al inicio
          </Link>
        </div>
      </header>

      {/* Main form container */}
      <main className="flex justify-center pt-6 pb-6">
        <div className="w-full max-w-md p-6 bg-white rounded-lg shadow-md">
          {message && (
            <div className={`mb-4 p-3 text-sm rounded ${
              messageType === 'success' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'
            }`}>
              {message}
            </div>
          )}
          <form onSubmit={handleSubmit} className="space-y-6">
            <div>
              <label htmlFor="currentPassword" className="block font-medium mb-1">
                Contraseña Actual
              </label>
              <input
                id="currentPassword"
                type="password"
                value={currentPassword}
                onChange={(e) => setCurrentPassword(e.target.value)}
                required
                className="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-ujaGreen"
              />
            </div>
            <div>
              <label htmlFor="newPassword" className="block font-medium mb-1">
                Nueva Contraseña
              </label>
              <input
                id="newPassword"
                type="password"
                value={newPassword}
                onChange={(e) => setNewPassword(e.target.value)}
                required
                className="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-ujaGreen"
              />
            </div>
            <div>
              <label htmlFor="confirmPassword" className="block font-medium mb-1">
                Confirmar Nueva Contraseña
              </label>
              <input
                id="confirmPassword"
                type="password"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                required
                className="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-ujaGreen"
              />
            </div>
            <div className="flex justify-end">
              <button
                type="submit"
                className="bg-ujaGreen text-white px-6 py-2 rounded hover:bg-green-800 transition"
              >
                Actualizar Contraseña
              </button>
            </div>
          </form>
        </div>
      </main>
    </div>
  );
};

export default ChangePasswordPage;

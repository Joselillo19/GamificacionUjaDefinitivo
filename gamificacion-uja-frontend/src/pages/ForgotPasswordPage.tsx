import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { forgotPassword } from '../services/authService';

const ForgotPasswordPage: React.FC = () => {
  const [emailOrDni, setEmailOrDni] = useState('');
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setMessage('');
    try {
      await forgotPassword(emailOrDni);
      setMessage('Si existe una cuenta asociada, recibirás un correo con instrucciones.');
      // Redirigir automáticamente después de 5 segundos
      setTimeout(() => navigate('/login'), 5000);
    } catch (err: any) {
      setError(err.message || 'Error al enviar el correo de recuperación');
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-ujaGray px-4">
      <form onSubmit={handleSubmit} className="bg-white p-8 rounded-xl shadow-md w-full max-w-md">
        <h2 className="text-2xl font-bold text-ujaGreen mb-6 text-center">Recuperar Contraseña</h2>
        {message && <p className="text-green-600 mb-4 text-center">{message}</p>}
        {error && <p className="text-red-600 mb-4 text-center">{error}</p>}
        <div className="mb-6">
          <label htmlFor="emailOrDni" className="block mb-1 text-sm font-medium">
            Correo UJA o DNI
          </label>
          <input
            id="emailOrDni"
            type="text"
            value={emailOrDni}
            onChange={(e) => setEmailOrDni(e.target.value)}
            className="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-ujaGreen"
            required
          />
        </div>
        <button
          type="submit"
          className="w-full bg-ujaGreen text-white py-2 rounded hover:bg-green-800 transition"
        >
          Enviar correo de recuperación
        </button>
        <div className="mt-4 flex justify-between items-center">
          <Link to="/login" className="text-sm text-gray-600 hover:underline">
            Volver al login
          </Link>
        </div>
      </form>
    </div>
  );
};

export default ForgotPasswordPage;

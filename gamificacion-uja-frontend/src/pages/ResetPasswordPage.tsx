import React, { useState, useEffect } from 'react';
import { useNavigate, useSearchParams, Link } from 'react-router-dom';
import { resetPassword } from '../services/authService';

const ResetPasswordPage: React.FC = () => {
  const [password, setPassword] = useState('');
  const [confirm, setConfirm] = useState('');
  const [error, setError] = useState('');
  const [token, setToken] = useState<string | null>(null);
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();

  useEffect(() => {
    const t = searchParams.get('token');
    setToken(t);
  }, [searchParams]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    if (password !== confirm) {
      setError('Las contraseñas no coinciden');
      return;
    }
    if (!token) {
      setError('Token no válido');
      return;
    }
    try {
      await resetPassword(token, password);
      navigate('/login', { state: { fromReset: true } });
    } catch (err: any) {
      setError(err.message || 'Error al restablecer la contraseña');
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-ujaGray px-4">
      <form onSubmit={handleSubmit} className="bg-white p-8 rounded-xl shadow-md w-full max-w-md">
        <h2 className="text-2xl font-bold text-ujaGreen mb-6 text-center">Nueva Contraseña</h2>
        {error && <p className="text-red-600 mb-4 text-center">{error}</p>}
        <div className="mb-4">
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
        <div className="mb-6">
          <label htmlFor="confirm" className="block mb-1 text-sm font-medium">
            Confirmar Contraseña
          </label>
          <input
            id="confirm"
            type="password"
            value={confirm}
            onChange={(e) => setConfirm(e.target.value)}
            className="w-full border border-gray-300 rounded-lg p-2 focus:outline-none focus:ring-2 focus:ring-ujaGreen"
            required
          />
        </div>
        <button
          type="submit"
          className="w-full bg-ujaGreen text-white py-2 rounded hover:bg-green-800 transition"
        >
          Restablecer contraseña
        </button>
        <div className="mt-4 text-center">
          <Link to="/login" className="text-sm text-gray-600 hover:underline">
            Volver al login
          </Link>
        </div>
      </form>
    </div>
  );
};

export default ResetPasswordPage;
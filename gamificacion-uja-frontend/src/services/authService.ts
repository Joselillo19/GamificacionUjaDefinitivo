import axios from "axios";

const API_URL = process.env.REACT_APP_API_URL || "http://localhost:8080/api/auth";

export interface LoginResponse {
  token: string;
  // Otros campos opcionales
}

/**
 * Realiza el login contra el backend (POST /auth/login).
 * Guarda el token en localStorage.
 */
export const login = async (
  username: string,
  password: string
): Promise<LoginResponse> => {
  try {
    const response = await axios.post<LoginResponse>(
      `${API_URL}/login`,
      { username, password },
      { headers: { "Content-Type": "application/json" } }
    );

    if (response.status === 200 && response.data.token) {
      localStorage.setItem("token", response.data.token);
      return response.data;
    }

    throw new Error("No se recibió token de autenticación.");
  } catch (err: unknown) {
    if (axios.isAxiosError(err)) {
      if (err.response?.status === 401) {
        throw new Error("Usuario o contraseña incorrectos.");
      }
      const apiMessage = err.response?.data?.message;
      throw new Error(apiMessage || err.message || "Error al iniciar sesión.");
    }
    // Error genérico
    const message = err instanceof Error ? err.message : String(err);
    throw new Error(message);
  }
};

/**
 * Solicita el envío de correo para recuperar la contraseña (POST /auth/forgot-password).
 */
export const forgotPassword = async (emailOrDni: string) => {
  const response = await fetch('http://localhost:8080/api/auth/forgot-password', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ emailOrDni }),
  });

  if (!response.ok) {
    const errorText = await response.text();
    throw new Error(errorText || 'Error al enviar el correo');
  }
};


/**
 * Restablece la contraseña usando token (POST /auth/reset-password).
 */
export const resetPassword = async (
  token: string,
  newPassword: string
): Promise<void> => {
  try {
    await axios.post(
      `${API_URL}/reset-password`,
      { token, newPassword },
      { headers: { "Content-Type": "application/json" } }
    );
  } catch (err: unknown) {
    if (axios.isAxiosError(err)) {
      const apiMessage = err.response?.data?.message;
      throw new Error(apiMessage || err.message || "Error al restablecer la contraseña.");
    }
    const message = err instanceof Error ? err.message : String(err);
    throw new Error(message);
  }
};

/**
 * Elimina el token para cerrar sesión.
 */
export const logout = (): void => {
  localStorage.removeItem("token");
};

/**
 * Obtiene el header Authorization con JWT.
 */
export const authHeader = (): { Authorization: string } => {
  const token = localStorage.getItem("token");
  return token ? { Authorization: `Bearer ${token}` } : { Authorization: "" };
};
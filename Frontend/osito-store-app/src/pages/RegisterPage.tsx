import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { registerUser } from "../api/userApi";

export default function RegisterPage() {
const [formData, setFormData] = useState({
  name: "",
  email: "",
  password: "",
  confirmPassword: "",
});

const [error, setError] = useState<string | null>(null);
const [loading, setLoading] = useState(false);
const navigate = useNavigate();


const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
  const { id, value } = e.target;
  setFormData((prev) => ({ ...prev, [id]: value }));
};

const validateForm = () => {
  if (!formData.name.trim()) {
    setError('El nombre es requerido');
    return false;
  }
  if (!formData.email.includes('@')) {
    setError('Ingrese un email válido');
    return false;
  }
  if (formData.password.length < 6) {
    setError('La contraseña debe tener al menos 6 caracteres');
    return false;
  }
  if (formData.password !== formData.confirmPassword) {
    setError('Las contraseñas no coinciden');
    return false;
  }
  return true;
};

const handleSubmit = async (e: React.FormEvent) => {
  e.preventDefault();
  
  if (!validateForm()) return;
  
  try {
    setLoading(true);
    setError(null);

    const response = await registerUser({
      name: formData.name,
      email: formData.email,
      password: formData.password,
    });
    
    localStorage.setItem("token", response.token);
    navigate('/');
  } catch (err) {
    setError(
      err instanceof Error ? err.message : 'Error al registrar usuario'
    );
  } finally {
    setLoading(false);
  }
};



  
  return (
    <div className="min-h-screen bg-blue-400 flex items-center justify-center">
      <div className="w-full max-w-2xl bg-white shadow-lg rounded-2xl p-12">
        <Link to="/">
          <img src="/logo.png" alt="Logo" className="w-40 h-40 mx-auto" />
        </Link>
        <h1 className="font-black text-4xl text-center mb-10">Registrate</h1>
        {error && (
          <div className="mb-4 p-3 bg-red-100 text-red-700 rounded">
            {error}
          </div>
        )}

        <form className="space-y-6" onSubmit={handleSubmit}>
          <div>
            <label htmlFor="name" className="text-lg font-semibold block mb-2">
              Nombre Completo
            </label>
            <input
              className="w-full p-4 border border-gray-300 rounded text-lg"
              type="text"
              id="name"
              value={formData.name}
              onChange={handleChange}
              placeholder="Escriba su nombre completo"
              required
            />
          </div>

          <div>
            <label htmlFor="email" className="text-lg font-semibold block mb-2">
              Email
            </label>
            <input
              className="w-full p-4 border border-gray-300 rounded text-lg"
              type="email"
              id="email"
              value={formData.email}
              onChange={handleChange}
              placeholder="Escriba su email"
              required
            />
          </div>

          <div>
            <label
              htmlFor="password"
              className="text-lg  font-semibold block mb-2"
            >
              Contraseña
            </label>
            <input
              className="w-full p-4 border border-gray-300 rounded text-lg"
              type="password"
              id="password"
              value={formData.password}
              onChange={handleChange}
              placeholder="Escriba su contraseña"
              required
            />
          </div>

          <div>
            <label
              htmlFor="confirmPassword"
              className="text-lg  font-semibold block mb-2"
            >
              Confirmar Contraseña
            </label>
            <input
              className="w-full p-4 border border-gray-300 rounded text-lg"
              type="password"
              id="confirmPassword"
              value={formData.confirmPassword}
              onChange={handleChange}
              placeholder="Escriba su contraseña"
              required
            />
          </div>

          <button
            type="submit"
            className="w-full bg-black text-white py-4 rounded-lg text-lg hover:bg-gray-800 transition"
            disabled={loading}
          >
            {loading ? "Creando cuenta..." : "Crear Cuenta"}
          </button>

          <p className="text-center text-gray-600">
            ¿Ya tienes una cuenta{" "}
            <Link
              to="/login"
              className="text-blue-600 hover:underline font-medium"
            >
              Iniciar Sesion
            </Link>
          </p>
        </form>
      </div>
    </div>
  );
}

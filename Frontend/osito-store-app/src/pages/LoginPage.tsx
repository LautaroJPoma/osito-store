import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { loginUser } from "../api/userApi";

export default function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const response = await loginUser({ email, password }); // Ahora response incluye { token, username }
      
      // Guardar token y username en localStorage
      localStorage.setItem("token", response.token);
      localStorage.setItem("username", response.username);
      navigate("/");
    } catch (error) {
      console.error(error);
      alert(
        "Error: " +
          (error instanceof Error ? error.message : "Error desconocido")
      );
    }
  };

  return (
    <div className="min-h-screen bg-blue-400 flex items-center justify-center">
      <div className="w-full max-w-2xl bg-white shadow-lg rounded-2xl p-12">
        <Link to="/">
          <img src="/logo.png" alt="Logo" className="w-40 h-40 mx-auto" />
        </Link>
        <h1 className="font-black text-4xl text-center mb-10">
          Iniciar Sesión
        </h1>

        <form className="space-y-6" onSubmit={handleSubmit}>
          <div>
            <label htmlFor="email" className="text-lg font-semibold block mb-2">
              Email
            </label>
            <input
              className="w-full p-4 border border-gray-300 rounded text-lg"
              type="email"
              id="email"
              placeholder="Escriba su email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
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
              placeholder="Escriba su contraseña"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>

          <div className="flex items-center gap-2">
            <input type="checkbox" id="remember" className="w-4 h-4" />
            <label htmlFor="remember" className="text-base font-medium">
              Recordarme
            </label>
          </div>

          <button
            type="submit"
            className="w-full bg-black text-white py-4 rounded-lg text-lg hover:bg-gray-800 transition"
          >
            Ingresar
          </button>

          <p className="text-center text-gray-600">
            ¿Todavía no tenés una cuenta?{" "}
            <Link
              to="/register"
              className="text-blue-600 hover:underline font-medium"
            >
              Registrate
            </Link>
          </p>
        </form>
      </div>
    </div>
  );
}

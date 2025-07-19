import { Link } from "react-router-dom";

export default function RegisterPage() {
  return (
    <div className="min-h-screen bg-blue-400 flex items-center justify-center">
      <div className="w-full max-w-2xl bg-white shadow-lg rounded-2xl p-12">
        <Link to="/">
          <img src="/logo.png" alt="Logo" className="w-40 h-40 mx-auto" />
        </Link>
        <h1 className="font-black text-4xl text-center mb-10">Registrate</h1>

        <form className="space-y-6">
          <div>
            <label htmlFor="name" className="text-lg font-semibold block mb-2">
              Nombre Completo
            </label>
            <input
              className="w-full p-4 border border-gray-300 rounded text-lg"
              type="text"
              id="name"
              placeholder="Escriba su nombre completo"
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
              placeholder="Escriba su email"
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
              type="confirmPassword"
              id="confirmPassword"
              placeholder="Escriba su contraseña"
            />
          </div>

          <button
            type="submit"
            className="w-full bg-black text-white py-4 rounded-lg text-lg hover:bg-gray-800 transition"
          >
            Crear Cuenta
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

import { Link } from "react-router-dom";


export default function LoginPage() {
  return (
    <div className="min-h-screen bg-blue-400 flex items-center justify-center">
  <div className="w-full max-w-2xl bg-white shadow-lg rounded-2xl p-12">
    <img src="/logo.png" alt="Logo" className="w-40 h-40 mx-auto" />
    <h1 className="font-black text-4xl text-center mb-10">Iniciar Sesión</h1>

    <form className="space-y-6">
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
        <label htmlFor="password" className="text-lg  font-semibold block mb-2">
          Contraseña
        </label>
        <input
          className="w-full p-4 border border-gray-300 rounded text-lg"
          type="password"
          id="password"
          placeholder="Escriba su contraseña"
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
  ¿Todavía no tenés una cuenta?{' '}
  <Link to="/register" className="text-blue-600 hover:underline font-medium">
    Registrate
  </Link>
</p>

    </form>
  </div>
</div>

  );
}


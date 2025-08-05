import { Link, useNavigate } from "react-router-dom";

export default function ProfilePage() {
  const username = localStorage.getItem("username");
  const email = localStorage.getItem("email");

  const navigate = useNavigate();
  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("username");
    localStorage.removeItem("email");
    navigate("/login");
  };

  return (
    <div className="mt-6 max-w-4xl mx-auto h-full p-6 bg-white rounded-2xl shadow-lg">
      <h1 className="text-3xl font-bold mb-6 text-gray-800">Mi Cuenta</h1>

      {/* Información personal */}
      <div className="mb-6">
        <h2 className="text-xl font-semibold mb-4 text-gray-700">
          Información Personal
        </h2>
        <p>
          <strong>Nombre:</strong> {username}
        </p>
        <p>
          <strong>Email:</strong> {email}
        </p>

        <p className="mt-4">
          <strong>¿Quieres cambiar de contraseña?</strong>
        </p>
        <Link
          to="/change-password"
          className="text-blue-500 hover:underline block mb-4"
        >
          Puedes cambiarla aquí.
        </Link>
        <button
          onClick={handleLogout}
          className="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600 transition"
        >
          Cerrar Sesión
        </button>
      </div>

      <hr className="border-t-2 border-gray-300 my-6" />

      {/* Historial de compras */}
      <div className="mb-6">
        <h2 className="text-xl font-semibold mb-4 text-gray-700">
          Historial de Compras
        </h2>
        <p className="text-gray-500">No hay compras recientes...</p>
      </div>

      <hr className="border-t-2 border-gray-300 my-6" />

      {/* Historial de ventas */}
      <div className="mb-6">
        <h2 className="text-xl font-semibold mb-4 text-gray-700">
          Historial de Ventas
        </h2>
        <p className="text-gray-500">No hay ventas recientes...</p>
      </div>

      <hr className="border-t-2 border-gray-300 my-6" />

      {/* Publicaciones */}
      <div>
        <h2 className="text-xl font-semibold mb-4 text-gray-700">
          Tus Productos Publicados
        </h2>
        <p className="text-gray-500">Todavía no has publicado nada...</p>
      </div>
    </div>
  );
}

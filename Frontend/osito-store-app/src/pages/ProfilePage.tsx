import { Link, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import type { Post } from "../types";
import { getPostsByUserId } from "../api/postApi";
import BaseCard from "../components/BaseCard";

export default function ProfilePage() {
  const username = localStorage.getItem("username");
  const email = localStorage.getItem("email");
  const userId = localStorage.getItem("userId");
  const [userPosts, setUserPosts] = useState<Post[]>([]);
  const [loading, setLoading] = useState(true);

  const navigate = useNavigate();

  useEffect(() => {
    const fetchUserPosts = async () => {
      try {
        // Asume que tienes una función getPostsByUserId en tu API
        const posts = await getPostsByUserId(Number(userId));
        setUserPosts(posts);
      } catch (error) {
        console.error("Error fetching user posts:", error);
      } finally {
        setLoading(false);
      }
    };

    if (userId) {
      fetchUserPosts();
    }
  }, [userId]);

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
      <section className="mb-8 bg-white rounded-2xl shadow-md p-6 border border-gray-100">
        <h2 className="text-2xl font-bold mb-6 text-gray-800 flex items-center gap-2">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            className="h-6 w-6 text-blue-500"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth={2}
              d="M5.121 17.804A13.937 13.937 0 0112 15c2.21 0 4.293.534 6.121 1.476M15 10a3 3 0 11-6 0 3 3 0 016 0z"
            />
          </svg>
          Información Personal
        </h2>

        <div className="space-y-3 text-gray-700">
          <p className="flex items-center gap-2">
            <strong className="w-24 text-gray-500">Nombre:</strong>
            <span>{username}</span>
          </p>
          <p className="flex items-center gap-2">
            <strong className="w-24 text-gray-500">Email:</strong>
            <span>{email}</span>
          </p>
        </div>

        <div className="mt-6 p-4 bg-blue-50 rounded-lg border border-blue-100">
          <p className="text-sm font-medium text-blue-700">
            ¿Quieres cambiar de contraseña?
          </p>
          <Link
            to="/change-password"
            className="mt-1 inline-block text-blue-600 hover:text-blue-800 hover:underline font-medium"
          >
            Puedes cambiarla aquí
          </Link>
        </div>

        <button
          onClick={handleLogout}
          className="mt-6 w-full bg-red-500 text-white py-2.5 rounded-xl shadow hover:bg-red-600 transition-colors font-medium"
        >
          Cerrar Sesión
        </button>
      </section>

      <hr className="border-t-2 border-gray-300 my-6" />

      {/* Historial de compras */}
      <section className="mb-6">
        <h2 className="text-xl font-semibold mb-4 text-gray-700">
          Historial de Compras
        </h2>
        <p className="text-gray-500">No hay compras recientes...</p>
      </section>

      <hr className="border-t-2 border-gray-300 my-6" />

      {/* Historial de ventas */}
      <section className="mb-6">
        <h2 className="text-xl font-semibold mb-4 text-gray-700">
          Historial de Ventas
        </h2>
        <p className="text-gray-500">No hay ventas recientes...</p>
      </section>

      <hr className="border-t-2 border-gray-300 my-6" />

      {/* Publicaciones */}
      <section>
        <h2 className="text-xl font-semibold mb-4 text-gray-700">
          Tus Productos Publicados
        </h2>

        {loading ? (
          <div className="flex justify-center">
            <p>Cargando tus productos...</p>
          </div>
        ) : userPosts.length > 0 ? (
          <>
            <div className="flex gap-4 flex-wrap">
              {userPosts.slice(0, 3).map((post) => (
                <BaseCard key={post.id} post={post} />
              ))}
            </div>
            <div className="mt-6 text-center">
              <Link
                to={`/user/${userId}/posts`}
                className="inline-block bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 transition"
              >
                Ver más publicaciones ({userPosts.length - 3} más)
              </Link>
            </div>
          </>
        ) : (
          <div className="text-center py-8">
            <p className="text-gray-500 mb-4">
              Todavía no has publicado nada...
            </p>
            <Link
              to="/create-post"
              className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 transition"
            >
              Publicar mi primer producto
            </Link>
          </div>
        )}
      </section>
    </div>
  );
}

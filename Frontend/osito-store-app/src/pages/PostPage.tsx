import { Link, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import type { Category, Post } from "../types";
import { getPostById } from "../api/postApi";
import { getCategories } from "../api/categoryApi";

export default function PostPage() {
  const { id } = useParams<{ id: string }>();
  const [post, setPost] = useState<Post | null>(null);
  const [loading, setLoading] = useState(true);
  const [categories, setCategories] = useState<Category[]>([]);

  useEffect(() => {
    const loadPost = async () => {
      try {
        const data = await getPostById(Number(id));
        const categoryData = await getCategories();
        setPost(data);
        setCategories(categoryData);
      } catch (error) {
        console.error("Error al obtener el post:", error);
        console.error("Error al cargar categorias:", error);
      } finally {
        setLoading(false);
      }
    };

    loadPost();
  }, [id]);

  if (loading) return <p>Cargando...</p>;
  if (!post) return <p>No se encontró el post.</p>;

  return (
    <div>
      

      <section className="w-[1000px] mx-auto mt-10 p-6 bg-white rounded-2xl shadow-lg space-y-6">
      <p className="  text-xl">Categoria: 
        {categories.length === 0
          ? "Cargando categoría..."
          : (() => {
              const category = categories.find((c) => c.id === post.categoryId);
              if (!category) return "Categoría no encontrada";
              return (
                <Link
                  to={`/category/${category.name}`}
                  className="text-blue-600 hover:underline"
                >
                  {category.name}
                </Link>
              );
            })()}
      </p>
        <div className="flex justify-between gap-6">
          <div className="w-2/3 h-[400px] rounded-xl overflow-hidden bg-gray-100">
            <img
              src="/images/Adidas.jpeg"
              alt="Zapatilla"
              className="w-full h-full object-contain"
            />
          </div>

          <div className="w-1/3 flex flex-col items-center justify-center bg-gray-50 rounded-xl p-6 shadow">
            <h1 className="text-3xl font-bold mb-4">{post.title}</h1>
            <p className="text-xl font-bold text-green-600">${post.price}</p>
            <p className="text-sm text-gray-500">Stock: {post.stock}</p>
            <div className="flex flex-col w-full gap-3 mt-6">
              <button className="bg-blue-600 text-white py-2 rounded-xl hover:bg-blue-700 transition">
                Comprar Ahora
              </button>
              <button className="bg-gray-800 text-white py-2 rounded-xl hover:bg-gray-900 transition">
                Añadir a carrito
              </button>
            </div>
          </div>
        </div>

        <div className="w-2/3 bg-gray-50 p-4 rounded-xl shadow">
          <h1 className="text-2xl font-black mb-2">Descripción</h1>
          <p className="text-lg whitespace-pre-line">{post.description}</p>
        </div>
      </section>

      <div>Mas productos:</div>
    </div>
  );
}

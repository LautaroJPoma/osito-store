import { Link, useNavigate } from "react-router-dom";

import { useEffect, useState } from "react";
import type { Category, Post } from "../types";
import { getCategories } from "../api/categoryApi";
import { createPost } from "../api/postApi";

export default function CreatePostPage() {
  const [categories, setCategories] = useState<Category[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const [postData, setPostData] = useState<Omit<Post, "id">>({
    title: "",
    description: "",
    price: 0,
    stock: 0,
    categoryId: 0
  });

  useEffect(() => {
    const loadCategories = async () => {
      try {
        const data = await getCategories();
        setCategories(data);
        if (data.length > 0) {
          setPostData(prev => ({ ...prev, categoryId: data[0].id }));
        }
      } catch (error) {
        setError("Error al cargar categorías");
        console.error("Error loading categories:", error);
      }
    };
    loadCategories();
  }, []);

  const handleInputChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>
  ) => {
    const { id, value } = e.target;
    setPostData(prev => ({
      ...prev,
      [id]: id === "price" || id === "stock" || id === "categoryId" 
            ? Number(value) 
            : value
    }));
    setError(null);
  };

  const validateForm = (): boolean => {
    if (!postData.title.trim()) {
      setError("El título es requerido");
      return false;
    }
    if (postData.price <= 0) {
      setError("El precio debe ser mayor a 0");
      return false;
    }
    if (postData.stock < 0) {
      setError("El stock no puede ser negativo");
      return false;
    }
    if (!postData.categoryId) {
      setError("Selecciona una categoría");
      return false;
    }
    return true;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!validateForm()) return;

    try {
      setLoading(true);
      const createdPost = await createPost(postData);
      navigate(`/posts/${createdPost.id}`); 
    } catch (error) {
      setError(error instanceof Error ? error.message : "Error al crear publicación");
      console.error("Error creating post:", error);
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
        <h1 className="font-black text-4xl text-center mb-10">
          Crear Publicación
        </h1>

        {error && (
          <div className="mb-4 p-3 bg-red-100 text-red-700 rounded">
            {error}
          </div>
        )}

        <form className="space-y-6" onSubmit={handleSubmit}>
          <div>
            <label
              htmlFor="title"
              className="text-lg font-semibold block mb-2"
            >
              Titulo
            </label>
            <input
              className="w-full p-4 border border-gray-300 rounded text-lg"
              type="text"
              id="title"
              onChange={handleInputChange}
              placeholder="Ingrese el titulo de la publicación"
              value={postData.title}
            />
          </div>

          <div>
            <label
              htmlFor="category"
              className="text-lg font-semibold block mb-2"
            >
              Categoria
            </label>
            <select
              id="categoryId"
              value={postData.categoryId}
              onChange={handleInputChange}
              required
              className="w-full p-4 border border-gray-300 rounded text-lg"
            >
              {categories.map((category) => (
                <option key={category.id} value={category.id}>
                  {category.name}
                </option>
              ))}
            </select>
          </div>

          <div>
            <label
              htmlFor="description"
              className="text-lg  font-semibold block mb-2"
            >
              Descripción
            </label>
            <textarea
              id="description"
              rows={8}
              className="w-full p-4 border border-gray-300 rounded text-lg"
              placeholder="Ingrese la descripción del producto"
              onChange={handleInputChange}
              value={postData.description}
            ></textarea>
          </div>

          <div>
            <label htmlFor="price" className="text-lg font-semibold block mb-2">
              Precio
            </label>
            <input
              type="number"
              id="price"
              className="w-full p-4 border border-gray-300 rounded text-lg"
              placeholder="Ingrese el precio del producto"
              onChange={handleInputChange}
              value={postData.price}
            />
          </div>

          <div>
            <label htmlFor="stock" className="text-lg font-semibold block mb-2">
              Stock
            </label>
            <input
              type="number"
              id="stock"
              className="w-full p-4 border border-gray-300 rounded text-lg"
              placeholder="Ingrese el stock del producto"
              onChange={handleInputChange}
              value={postData.stock}
            />
          </div>
          <button
            type="submit"
            className={`w-full py-4 rounded-lg text-lg transition ${
              loading
                ? "bg-gray-500 cursor-not-allowed"
                : "bg-black text-white hover:bg-gray-800"
            }`}
            disabled={loading}
          >
            {loading ? "Publicando..." : "Publicar Producto"}
          </button>
        </form>
      </div>
    </div>
  );
}

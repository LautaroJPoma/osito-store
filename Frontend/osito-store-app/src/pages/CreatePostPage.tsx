import { Link, useNavigate } from "react-router-dom";

import { useEffect, useState } from "react";
import type { Category, Post } from "../types";
import { getCategories } from "../api/categoryApi";
import { createPost, uploadVariantImages } from "../api/postApi";

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
    categoryId: 0,
    sellerId: Number(localStorage.getItem("userId")),
    variants: [],
  });

  function addVariant() {
  setPostData((prev) => ({
    ...prev,
    variants: [
      ...prev.variants,
      {
        color: "",
        colorHex: "#000000",
        size: "",
        stock: 0,
        images: [],
        imageUrls: [],
      },
    ],
  }));
}

function addSizeToVariant(index: number) {
  setPostData((prev) => {
    const variants = [...prev.variants];
    const current = variants[index];
    variants.splice(index + 1, 0, {
      color: current.color,
      colorHex: current.colorHex,
      size: "",
      stock: 0,
      images: [],
      imageUrls: [],
      isSizeOnly: true,
    });
    return { ...prev, variants };
  });
}

  
  

  // Para eliminar imágenes locales no subidas
  const removeImage = (variantIndex: number, imageIndex: number) => {
    setPostData((prev) => {
      const updated = [...prev.variants];
      updated[variantIndex].images?.splice(imageIndex, 1);
      return { ...prev, variants: updated };
    });
  };

  // Para eliminar URLs de imágenes ya subidas
  const removeImageUrl = (variantIndex: number, urlIndex: number) => {
    setPostData((prev) => {
      const updated = [...prev.variants];
      updated[variantIndex].imageUrls?.splice(urlIndex, 1);
      return { ...prev, variants: updated };
    });
  };

  const handleVariantChange = (
    index: number,
    field: "color" | "size" | "colorHex" | "stock",
    value: string
  ) => {
    const updated = [...postData.variants];

    updated[index] = {
      ...updated[index],
      [field]: field === "stock" ? Number(value) : value,
    };

    setPostData((prev) => ({ ...prev, variants: updated }));
  };

  async function handleVariantImageUpload(
    variantIndex: number,
    files: FileList
  ) {
    if (!files || files.length === 0) return;

    const filesArray = Array.from(files);

    // Actualización optimista del estado
    setPostData((prev) => ({
      ...prev,
      variants: prev.variants.map((v, i) =>
        i === variantIndex
          ? {
              ...v,
              images: [...(v.images || []), ...filesArray],
            }
          : v
      ),
    }));

    const variantId = postData.variants[variantIndex].id;
    if (!variantId) return;

    try {
      // Subir cada imagen individualmente
      const uploadPromises = filesArray.map((file) => {
        const formData = new FormData();
        formData.append("file", file);
        return uploadVariantImages(variantId, formData);
      });

      const responses = await Promise.all(uploadPromises);
      const newUrls = responses.flatMap((r) => r.data.imageUrl || []);

      // Actualizar URLs
      setPostData((prev) => ({
        ...prev,
        variants: prev.variants.map((v, i) =>
          i === variantIndex
            ? {
                ...v,
                imageUrls: [...(v.imageUrls || []), ...newUrls],
              }
            : v
        ),
      }));
    } catch (error) {
      console.error("Error:", error);
      // Revertir en caso de error
      setPostData((prev) => ({
        ...prev,
        variants: prev.variants.map((v, i) =>
          i === variantIndex
            ? {
                ...v,
                images: (v.images || []).slice(0, -filesArray.length),
              }
            : v
        ),
      }));
    }
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!validateForm()) return;

    try {
      setLoading(true);

      // 1) Crear post sin imágenes
      const createdPost = await createPost({
        title: postData.title,
        description: postData.description,
        price: postData.price,
        stock: postData.stock,
        categoryId: postData.categoryId,
        sellerId: postData.sellerId,
        variants: postData.variants.map((v) => ({
          color: v.color,
          colorHex: v.colorHex,
          size: v.size,
          stock: v.stock,
        })),
      });

      const uploadedImagesMap = new Map<string, string[]>();

      // 3) Subir imágenes de variantes
      for (let i = 0; i < createdPost.variants.length; i++) {
        const variant = createdPost.variants[i];
        const files = postData.variants[i]?.images;
        const key = `${variant.color.toLowerCase()}-${variant.colorHex.toLowerCase()}`;

        if (uploadedImagesMap.has(key)) {
          // Ya subimos imágenes para esta combinación, asignar URLs directamente
          const existingUrls = uploadedImagesMap.get(key)!;
          setPostData((prev) => {
            const updatedVariants = [...prev.variants];
            updatedVariants[i].imageUrls = existingUrls;
            return { ...prev, variants: updatedVariants };
          });
        } else if (files && files.length > 0) {
          // Subir imágenes y guardar URLs en el mapa
          const formData = new FormData();
          for (const file of files) {
            formData.append("files", file);
          }
          const response = await uploadVariantImages(variant.id!, formData);
          const newUrls = response.data.imageUrls || [];
          uploadedImagesMap.set(key, newUrls);

          setPostData((prev) => {
            const updatedVariants = [...prev.variants];
            updatedVariants[i].imageUrls = newUrls;
            return { ...prev, variants: updatedVariants };
          });
        }
      }

      navigate(`/posts/${createdPost.id}`);
    } catch (error) {
      setError(
        error instanceof Error ? error.message : "Error al crear publicación"
      );
      console.error("Error creating post:", error);
    } finally {
      setLoading(false);
    }
  };

  const handleInputChange = (
    e: React.ChangeEvent<
      HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement
    >
  ) => {
    const { id, value } = e.target;
    setPostData((prev) => ({
      ...prev,
      [id]:
        id === "price" || id === "stock" || id === "categoryId"
          ? Number(value)
          : value,
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

  useEffect(() => {
    const loadCategories = async () => {
      try {
        const data = await getCategories();
        setCategories(data);
        if (data.length > 0) {
          setPostData((prev) => ({ ...prev, categoryId: data[0].id }));
        }
      } catch (error) {
        setError("Error al cargar categorías");
        console.error("Error loading categories:", error);
      }
    };
    loadCategories();
  }, []);
  return (
    <div className="min-h-screen pt-10 pb-10 bg-blue-400 flex items-center justify-center">
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
            <label htmlFor="title" className="text-lg font-semibold block mb-2">
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
            <h2 className="text-lg font-semibold mb-2">
              Variantes del Producto
            </h2>

            {postData.variants.map((variant, index) => (
              <div
                key={index}
                className="p-4 border rounded-lg bg-gray-50 shadow-sm flex flex-col gap-3 mb-4"
              >
                <h3 className="font-semibold text-gray-700">
                  Variante {index + 1}
                </h3>
                {!variant.isSizeOnly && (
<>{/* Imagen */}
                <div className="flex flex-col">
                  <label className="text-sm font-medium text-gray-600">
                    Imágenes
                  </label>

                  {/* Input principal para múltiples imágenes (oculto)} */}
                  <input
                    type="file"
                    accept="image/*"
                    multiple
                    onChange={(e) => {
                      if (e.target.files?.length) {
                        handleVariantImageUpload(index, e.target.files);
                      }
                      
                    }}
                    className="hidden"
                    id={`file-upload-${index}`}
                  />

                  {/* Contenedor de previsualización + botón de añadir */}
                  <div className="flex gap-2 mt-2 overflow-x-auto items-center">
                    {/* Mostrar imágenes existentes */}
                    {variant.images?.map((file, i) => (
                      <div key={`local-${i}`} className="relative group">
                        <img
                          src={URL.createObjectURL(file)}
                          alt={`Imagen ${i + 1}`}
                          className="h-20 w-20 object-contain rounded border"
                          onLoad={(e) =>
                            URL.revokeObjectURL(
                              (e.target as HTMLImageElement).src
                            )
                          }
                        />
                        <button
                          type="button"
                          onClick={() => removeImage(index, i)}
                          className="absolute -top-2 -right-2 bg-red-500 text-white rounded-full w-5 h-5 flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity"
                          aria-label="Eliminar imagen"
                        >
                          ×
                        </button>
                      </div>
                    ))}

                    {/* Mostrar URLs de imágenes ya subidas */}
                    {variant.imageUrls?.map((url, i) => (
                      <div key={`remote-${i}`} className="relative group">
                        <img
                          src={url}
                          alt={`Imagen subida ${i + 1}`}
                          className="h-20 w-20 object-contain rounded border"
                        />
                        <button
                          type="button"
                          onClick={() => removeImageUrl(index, i)}
                          className="absolute -top-2 -right-2 bg-red-500 text-white rounded-full w-5 h-5 flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity"
                          aria-label="Eliminar imagen"
                        >
                          ×
                        </button>
                      </div>
                    ))}

                    {/* Botón para añadir más imágenes */}
                    <button
                      type="button"
                      className="h-20 w-20 flex items-center justify-center border-2 border-dashed border-gray-300 rounded hover:border-gray-400 transition-colors"
                      onClick={() =>
                        document.getElementById(`file-upload-${index}`)?.click()
                      }
                    >
                      <span className="text-2xl text-gray-400">+</span>
                    </button>
                  </div>
                </div>
                {/* Fila de color */}
                <div className="flex items-center gap-3">
                  <div className="flex flex-col">
                    <label className="text-sm font-medium text-gray-600">
                      Nombre del color
                    </label>
                    <input
                      type="text"
                      placeholder="Ej. Verde menta"
                      value={variant.color}
                      onChange={(e) =>
                        handleVariantChange(index, "color", e.target.value)
                      }
                      className="p-2 border rounded w-40"
                    />
                  </div>
                  <div className="flex flex-col items-center">
                    <label className="text-sm font-medium text-gray-600">
                      Color
                    </label>
                    <input
                      type="color"
                      value={variant.colorHex}
                      onChange={(e) =>
                        handleVariantChange(index, "colorHex", e.target.value)
                      }
                      className="w-12 h-12 border rounded-full cursor-pointer"
                    />
                  </div>
                </div>

                </>
              )}

                {/* Talla */}
                <div className="flex flex-col">
                  <label className="text-sm font-medium text-gray-600">
                    Talla
                  </label>
                  <input
                    type="text"
                    placeholder="Ej. M"
                    value={variant.size}
                    onChange={(e) =>
                      handleVariantChange(index, "size", e.target.value)
                    }
                    className="p-2 border rounded w-40"
                  />
                  {/* Botón para agregar nueva talla */}
                  <button
                    type="button"
                    onClick={() => addSizeToVariant(index)}
                    className="mt-2 text-sm text-blue-600 hover:underline"
                  >
                    + Agregar otra talla para este color
                  </button>
                </div>

                {/* Stock */}
                <div className="flex flex-col">
                  <label className="text-sm font-medium text-gray-600">
                    Stock
                  </label>
                  <input
                    type="number"
                    placeholder="Ej. 30"
                    value={variant.stock}
                    onChange={(e) =>
                      handleVariantChange(index, "stock", e.target.value)
                    }
                    className="p-2 border rounded w-40"
                  />
                </div>

                


              </div>
            ))}




            <button
              type="button"
              onClick={addVariant}
              className="bg-blue-600 text-white px-4 py-2 rounded mt-2"
            >
              Agregar Variante
            </button>
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

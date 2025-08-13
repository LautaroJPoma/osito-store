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
  const [selectedSize, setSelectedSize] = useState<string | null>(null);
  const [currentImageIndex, setCurrentImageIndex] = useState(0);
  const [selectedColorGroup, setSelectedColorGroup] = useState<string | null>(
    null
  );

  useEffect(() => {
    const loadPost = async () => {
      try {
        const [postData, categoryData] = await Promise.all([
          getPostById(Number(id)),
          getCategories(),
        ]);
        setPost(postData);
        setCategories(categoryData);
      } catch (error) {
        console.error("Error cargando datos:", error);
      } finally {
        setLoading(false);
      }
    };
    loadPost();
  }, [id]);

  // Agrupar variantes por color (usando mismo enfoque que backend)
  const variantsGroupedByColor =
    post?.variants?.reduce(
      (acc, variant) => {
        const key = `${variant.color.toLowerCase()}-${variant.colorHex.toLowerCase()}`;
        if (!acc[key]) {
          acc[key] = {
            color: variant.color,
            colorHex: variant.colorHex,
            sizes: [],
            images: variant.imageUrls || [], // Usamos imágenes de la primera variante
          };
        }
        acc[key].sizes.push({
          size: variant.size,
          stock: variant.stock,
        });
        return acc;
      },
      {} as Record<
        string,
        {
          color: string;
          colorHex: string;
          sizes: { size: string; stock: number; variantId?: string }[];
          images: string[];
        }
      >
    ) || {};

  // Seleccionar primer color al cargar
  useEffect(() => {
    if (post && Object.keys(variantsGroupedByColor).length > 0) {
      const firstColor = Object.keys(variantsGroupedByColor)[0];
      setSelectedColorGroup(firstColor);
    }
  }, [post]);

  // Cambiar color seleccionado
  const handleColorChange = (colorKey: string) => {
    setSelectedColorGroup(colorKey);
    setCurrentImageIndex(0); // Resetear índice
    setSelectedSize(null); // Resetear talla seleccionada
  };

  // Navegación de imágenes
  const nextImage = () => {
    const images = selectedColorGroup
      ? variantsGroupedByColor[selectedColorGroup]?.images || []
      : [];
    setCurrentImageIndex((prev) => (prev + 1) % images.length);
  };

  const prevImage = () => {
    const images = selectedColorGroup
      ? variantsGroupedByColor[selectedColorGroup]?.images || []
      : [];
    setCurrentImageIndex((prev) => (prev - 1 + images.length) % images.length);
  };

  if (loading) return <p>Cargando...</p>;
  if (!post) return <p>No se encontró el producto.</p>;

  const currentColorGroup = selectedColorGroup
    ? variantsGroupedByColor[selectedColorGroup]
    : null;
  const currentImages = currentColorGroup?.images || [];

  return (
    <div>
      <section className="w-[1000px] mx-auto mt-10 p-6 bg-white rounded-2xl shadow-lg space-y-6">
        <p className="text-xl">
          Categoria:
          {categories.length === 0
            ? "Cargando categoría..."
            : (() => {
                const category = categories.find(
                  (c) => c.id === post.categoryId
                );
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
          {/* Sección de imágenes */}
          <div className="w-2/3 h-[400px] rounded-xl overflow-hidden bg-gray-100 flex items-center justify-center relative">
            {currentImages.length > 0 ? (
              <div className="relative w-full h-full">
                <img
                  src={currentImages[currentImageIndex]}
                  alt={`${currentColorGroup?.color} - Imagen ${
                    currentImageIndex + 1
                  }`}
                  className="w-full h-full object-contain"
                  onError={(e) => {
                    (e.target as HTMLImageElement).src =
                      "/images/default-image.png";
                  }}
                />

                {/* Controles de navegación */}
                {currentImages.length > 1 && (
                  <>
                    <button
                      onClick={prevImage}
                      className="absolute left-2 top-1/2 -translate-y-1/2 bg-white/80 rounded-full p-2 shadow hover:bg-white transition-all"
                    >
                      &lt;
                    </button>
                    <button
                      onClick={nextImage}
                      className="absolute right-2 top-1/2 -translate-y-1/2 bg-white/80 rounded-full p-2 shadow hover:bg-white transition-all"
                    >
                      &gt;
                    </button>
                    <div className="absolute bottom-2 left-1/2 transform -translate-x-1/2 bg-black/50 text-white px-2 py-1 rounded-full text-xs">
                      {currentImageIndex + 1}/{currentImages.length}
                    </div>
                  </>
                )}
              </div>
            ) : (
              <div className="flex flex-col items-center text-gray-400">
                <img
                  src="/images/default-image.png"
                  alt="Imagen no disponible"
                  className="w-1/2 h-auto opacity-50"
                />
                <span>Sin imágenes disponibles</span>
              </div>
            )}
          </div>

          {/* Sección de información del producto */}
          <div className="w-1/3 flex flex-col items-center justify-center bg-gray-50 rounded-xl p-6 shadow">
            <h1 className="text-3xl font-bold mb-4">{post.title}</h1>
            <p className="text-xl font-bold text-green-600">${post.price}</p>

            {/* Selector de colores */}
            <div className="w-full mt-4">
              <h3 className="text-sm font-medium mb-2">Color:</h3>
              <div className="flex gap-2">
                {Object.keys(variantsGroupedByColor).map((colorKey) => {
                  const group = variantsGroupedByColor[colorKey];
                  return (
                    <button
                      key={colorKey}
                      onClick={() => handleColorChange(colorKey)}
                      className={`w-8 h-8 rounded-full border-2 ${
                        selectedColorGroup === colorKey
                          ? "border-blue-500"
                          : "border-gray-200"
                      }`}
                      style={{ backgroundColor: group.colorHex }}
                      title={group.color}
                    />
                  );
                })}
              </div>
            </div>

            {/* Selector de tallas */}
            <div className="w-full mt-4">
              <h3 className="text-sm font-medium mb-2">Talla:</h3>
              <div className="flex flex-wrap gap-2">
                {selectedColorGroup &&
                  variantsGroupedByColor[selectedColorGroup].sizes.map(
                    (sizeInfo, i) => (
                      <button
                        key={i}
                        onClick={() => setSelectedSize(sizeInfo.size)}
                        className={`px-3 py-1 border rounded-md text-sm ${
                          sizeInfo.stock > 0
                            ? selectedSize === sizeInfo.size
                              ? "bg-blue-100 border-blue-500"
                              : "hover:bg-gray-100"
                            : "opacity-50 cursor-not-allowed"
                        }`}
                        disabled={sizeInfo.stock <= 0}
                      >
                        {sizeInfo.size}
                      </button>
                    )
                  )}
              </div>

              {/* Muestra el stock debajo de los botones de talles */}
              {selectedSize && selectedColorGroup && (
                <div className="mt-2 text-sm">
                  {(() => {
                    // Aseguramos que exista el grupo de color y encontramos el talle
                    const colorGroup =
                      variantsGroupedByColor[selectedColorGroup];
                    const sizeInfo = colorGroup?.sizes.find(
                      (s: { size: string; stock: number }) =>
                        s.size === selectedSize
                    );

                    if (!sizeInfo) return null;

                    return sizeInfo.stock > 0 ? (
                      <p className="text-green-600">
                        Stock disponible: {sizeInfo.stock}
                      </p>
                    ) : (
                      <p className="text-red-500">AGOTADO</p>
                    );
                  })()}
                </div>
              )}
            </div>

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

        {/* Sección de variantes (puedes mantenerla o eliminarla ahora que tenemos los selectores arriba) */}

        <div className="w-2/3 bg-gray-50 p-4 rounded-xl shadow">
          <h1 className="text-2xl font-black mb-2">Descripción</h1>
          <p className="text-lg whitespace-pre-line">{post.description}</p>
        </div>
      </section>

      <div>Más productos:</div>
    </div>
  );
}

import { useEffect, useState } from "react";
import { Listbox } from "@headlessui/react";
import { Link, useNavigate } from "react-router-dom";
import { getCategories, type Category } from "../services/Api"; // ajustá el path si es necesario

export default function NavBar() {
  const [categories, setCategories] = useState<Category[]>([]);
  const [selected, setSelected] = useState<string>("Categorías");
  const navigate = useNavigate();

  const handleChange = (value: string) => {
    setSelected(value);
    navigate(`/category/${value}`); // Redirige a la categoría
  };

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const data = await getCategories();
        setCategories(data);
      } catch (error) {
        console.error("Error al obtener categorías:", error);
      }
    };

    fetchCategories();
  }, []);

  return (
    <nav className="flex gap-10 items-center text-gray-800 text-lg font-medium">
      <div className="relative">
        <Listbox value={selected} onChange={handleChange}>
          <Listbox.Button className="hover:text-white transition">
            {selected}
          </Listbox.Button>
          <Listbox.Options className="absolute mt-2 w-40 bg-white border border-gray-200 rounded-md shadow-md z-50">
            {categories.map((category) => (
              <Listbox.Option
                key={category.id}
                value={category.name}
                className="px-4 py-2 hover:bg-gray-100 cursor-pointer"
              >
                {category.name}
              </Listbox.Option>
            ))}
          </Listbox.Options>
        </Listbox>
      </div>

      <Link to="/offers" className="hover:text-white transition">
        Ofertas
      </Link>
      <Link to="/tendencies" className="hover:text-white transition">
        Tendencias
      </Link>
      <Link to="/news" className="hover:text-white transition">
        Novedades
      </Link>
      <Link to="/help" className="hover:text-white transition">
        Ayuda
      </Link>
      <Link to="/create-post" className="hover:text-white transition">
        Vender
      </Link>
    </nav>
  );
}

import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Listbox } from '@headlessui/react';

const categories = ['Remeras', 'Camperas', 'Pantalones', 'Accesorios'];

export default function Navbar() {
  const [selected, setSelected] = useState('Categorías');
  const navigate = useNavigate();

  const handleChange = (value: string) => {
    setSelected(value);
    navigate('/categories'); 
  };

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
                key={category}
                value={category}
                className="px-4 py-2 hover:bg-gray-100 cursor-pointer"
              >
                {category}
              </Listbox.Option>
            ))}
          </Listbox.Options>
        </Listbox>
      </div>
      <Link to="/offers" className="hover:text-white transition">Ofertas</Link>
      <Link to="/tendencies" className="hover:text-white transition">Tendencias</Link>
      <Link to="/news" className="hover:text-white transition">Novedades</Link>
      <Link to="/help" className="hover:text-white transition">Ayuda</Link>
    </nav>
  )
}

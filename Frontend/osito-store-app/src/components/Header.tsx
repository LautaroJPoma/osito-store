import { Link } from "react-router-dom";
import Navbar from "./Navbar";
import { IconShoppingCart, IconUser, IconHeart } from "@tabler/icons-react";
import {  useEffect, useState } from "react";

export const Header = () => {
const [userName, setUserName] = useState<string | null>(null);


useEffect(() => {
  
  const storedUserName = localStorage.getItem("username");
  if (storedUserName) {
    setUserName(storedUserName);
  }
}, []);

  return (
    <header className="bg-blue-400 d py-4">
      <div className="container mx-auto flex flex-col gap-4">
        <div className="flex items-center justify-between flex-wrap gap-4">
          <Link to="/" className="flex items-center space-x-3 flex-shrink-0">
            <img
              className="h-30 w-30 object-contain"
              src="/logo.png"
              alt="Logo"
            />
            <span className="text-2xl font-bold text-gray-800">
              Osito Store
            </span>
          </Link>

          <div className="flex-1">
            <input
              type="text"
              placeholder="Buscar producto..."
              className="w-2/3 bg-white p-3 rounded-md shadow-sm"
            />
          </div>
        </div>

        <div className="flex justify-between items-center flex-wrap gap-4">
          <div>
            <Navbar />
          </div>

          <div className="flex items-center gap-4">
            <Link to="/favorites">
              <div className="group flex items-center overflow-hidden cursor-pointer">
                <IconHeart className="h-8 w-8 text-gray-800 transition-all duration-300" />

                <span className="ml-2 max-w-0 opacity-0 group-hover:max-w-[200px] group-hover:opacity-100 transition-all duration-300 text-m text-gray-800 whitespace-nowrap">
                  Favoritos
                </span>
              </div>
            </Link>

            {userName ? (
              <Link to="/profile">
                <div className="group flex items-center overflow-hidden cursor-pointer">
                  <IconUser className="h-8 w-8 text-gray-800 transition-all duration-300" />
                  <span className="ml-2 max-w-0 opacity-0 group-hover:max-w-[200px] group-hover:opacity-100 transition-all duration-300 text-m text-gray-800 whitespace-nowrap">
                    {userName}
                  </span>
                </div>
              </Link>
            ) : (
              <Link to="/login">
                <div className="group flex items-center overflow-hidden cursor-pointer">
                  <IconUser className="h-8 w-8 text-gray-800 transition-all duration-300" />
                  <span className="ml-2 max-w-0 opacity-0 group-hover:max-w-[200px] group-hover:opacity-100 transition-all duration-300 text-m text-gray-800 whitespace-nowrap">
                    Iniciar sesión
                  </span>
                </div>
              </Link>
            )}

            <Link to="/cart">
              <div className="group flex items-center overflow-hidden cursor-pointer">
                <IconShoppingCart className="h-8 w-8 text-gray-800 transition-all duration-300" />

                <span className="ml-2 max-w-0 opacity-0 group-hover:max-w-[200px] group-hover:opacity-100 transition-all duration-300 text-m text-gray-800 whitespace-nowrap">
                  Carrito
                </span>
              </div>
            </Link>
          </div>
        </div>
      </div>
    </header>
  );
};

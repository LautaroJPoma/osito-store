// Footer.tsx
import { Link } from "react-router-dom";
import { IconBrandGithub, IconBrandLinkedin, IconMail } from "@tabler/icons-react";

export default function Footer() {
  return (
    <footer className="bg-blue-400 text-black mt-12">
      <div className="container mx-auto py-8 px-4 grid grid-cols-1 md:grid-cols-3 gap-8">
       
        <div>
          <h2 className="text-2xl font-bold mb-4">Osito Store</h2>
          <p className="text-sm">
            Tu tienda de confianza para encontrar los mejores productos. 
            Compra fácil, rápido y seguro.
          </p>
        </div>

        
        <div>
          <h3 className="text-lg font-semibold mb-4">Enlaces rápidos</h3>
          <ul className="space-y-2">
            <li>
              <Link to="/" className="hover:underline">
                Inicio
              </Link>
            </li>
            <li>
              <Link to="/categories" className="hover:underline">
                Categorías
              </Link>
            </li>
            <li>
              <Link to="/cart" className="hover:underline">
                Carrito
              </Link>
            </li>
            <li>
              <Link to="/login" className="hover:underline">
                Iniciar Sesión
              </Link>
            </li>
          </ul>
        </div>

       
        <div>
          <h3 className="text-lg font-semibold mb-4">Contacto</h3>
          <ul className="space-y-2">
            <li className="flex items-center gap-2">
              <IconMail size={18} />
              <a href="mailto:soporte@ositostore.com" className="hover:underline">
                soporte@ositostore.com
              </a>
            </li>
            <li className="flex items-center gap-2">
              <IconBrandGithub size={18} />
              <a
                href="https://github.com/"
                target="_blank"
                rel="noopener noreferrer"
                className="hover:underline"
              >
                GitHub
              </a>
            </li>
            <li className="flex items-center gap-2">
              <IconBrandLinkedin size={18} />
              <a
                href="https://linkedin.com/"
                target="_blank"
                rel="noopener noreferrer"
                className="hover:underline"
              >
                LinkedIn
              </a>
            </li>
          </ul>
        </div>
      </div>

      {/* Parte inferior */}
      <div className="border-t border-white/20 mt-6 py-4 text-center text-sm">
        © {new Date().getFullYear()} Osito Store. Todos los derechos reservados.
      </div>
    </footer>
  );
}

import { Link } from "react-router-dom"
export default function Navbar() {
  return (
    <nav className="flex gap-20 text-gray-800 text-lg font-medium">
      <Link to="/categories" className="hover:text-white transition">Categorías</Link>
      <Link to="/offers" className="hover:text-white transition">Ofertas</Link>
      <Link to="/tendencies" className="hover:text-white transition">Tendencias</Link>
      <Link to="/news" className="hover:text-white transition">Novedades</Link>
      <Link to="/help" className="hover:text-white transition">Ayuda</Link>
    </nav>
  )
}

import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { changePassword } from "../api/userApi";


export default function ChangePasswordPage() {
    const [oldPassword, setOldPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (newPassword !== confirmPassword) {
      alert("Las nuevas contraseñas no coinciden");
      return;
    }

    try {

        const userEmail = localStorage.getItem("email");
        if (!userEmail) throw new Error("No se encontró el usuario");
      await changePassword({
        email: userEmail,
        oldPassword,
        newPassword,
       
      });
      
      alert("Contraseña cambiada exitosamente. Por favor inicia sesión nuevamente.");
      localStorage.removeItem("token");
      localStorage.remoeItem("email");
      localStorage.removeItem("username");
    
      navigate("/login");
    } catch (error) {
      console.error(error);
      alert(
        "Error: " + 
        (error instanceof Error ? error.message : "Error al cambiar la contraseña")
      );
    }
  };
  
  return (
    <div className="min-h-screen bg-blue-400 flex items-center justify-center">
      <div className="w-full max-w-2xl bg-white shadow-lg rounded-2xl p-12">
        <Link to="/">
          <img src="/logo.png" alt="Logo" className="w-40 h-40 mx-auto" />
        </Link>
        <h1 className="font-black text-4xl text-center mb-10">
          Cambiar Contraseña
        </h1>

        <form className="space-y-6" onSubmit={handleSubmit}>
          <div>
            <label htmlFor="currentPassword" className="text-lg font-semibold block mb-2">
            Contraseña Actual
            </label>
            <input
              className="w-full p-4 border border-gray-300 rounded text-lg"
              type="password"
              id="oldPassword"
              placeholder="Ingresa tu contraseña actual"
              value={oldPassword}
              onChange={(e) => setOldPassword(e.target.value)}
              required
            />
          </div>

          <div>
            <label
              htmlFor="newPassword"
              className="text-lg  font-semibold block mb-2"
            >
             Nueva Contraseña
            </label>
            <input
              className="w-full p-4 border border-gray-300 rounded text-lg"
              type="password"
              id="newPassword"
              placeholder="Escriba su nueva contraseña"
              value={newPassword}
              onChange={(e) => setNewPassword(e.target.value)}
              required
            />
          </div>

          <div>
            <label
              htmlFor="confirmPassword"
              className="text-lg  font-semibold block mb-2"
            >
              Confirmar Nueva Contraseña
            </label>
            <input
              className="w-full p-4 border border-gray-300 rounded text-lg"
              type="password"
              id="password"
              placeholder="Confirme su nueva contraseña"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
              required
            />
          </div>

          

          <button
            type="submit"
            className="w-full bg-black text-white py-4 rounded-lg text-lg hover:bg-gray-800 transition"
          >
            Cambiar Contraseña
          </button>

         
        </form>
      </div>
    </div>
  );
}

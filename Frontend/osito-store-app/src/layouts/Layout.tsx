import { Outlet } from "react-router-dom";
import { Header } from "../components/Header";
import Footer from "../components/Footer";

export default function Layout() {
  return (
    <>
      <div className="bg-gray-200 flex flex-col min-h-screen">
        <Header />
        <main className="flex-1">
          <div className="container mx-auto">
          <Outlet />
          </div>
        </main>
        < Footer/>
      </div>
    </>
  );
}

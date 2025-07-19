import { Outlet } from "react-router-dom";
import { Header } from "../components/Header";

export default function Layout() {
  return (
    <>
      <div className="bg-gray-200">
        <Header />
        <main className="container mx-auto">
          <Outlet />
        </main>
      </div>
    </>
  );
}

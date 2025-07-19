import BaseCard from "../components/BaseCard";

export default function FavoritesPage() {
  return (
    <div className="p-4">
      <h1 className="text-2xl text-center font-black mb-6">
        Puedes ver tus productos favoritos aquí
      </h1>
      <div className="flex items-center justify-center">
        <BaseCard />
      </div>
    </div>
  );
}


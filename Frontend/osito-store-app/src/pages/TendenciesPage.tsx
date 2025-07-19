import PostCard from "../components/PostCard";

export default function TendenciesPage() {
  return (
    <div className="p-4">
      <h1 className="text-2xl font-black text-center">Tendencias</h1>
      <p className="text-center text-gray-600 mb-4">
        Estos son los productos más populares del momento.
      </p>

     
      
    <div className="w-full">
      <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5 gap-4">
        <PostCard />
        <PostCard />
        <PostCard />
        <PostCard />
        <PostCard />
        <PostCard />
      </div>
      </div>
    </div>
  )
}

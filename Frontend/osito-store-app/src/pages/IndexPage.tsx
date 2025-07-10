import Banner from "../components/Banner";
import PostCard from "../components/PostCard";

export default function IndexPage() {
  return (
    <div>
      <Banner
      imageUrl="/images/index.jpg"
      title="Bienvenidos a Osito Store - Tu ropa, tu estilo."
      />

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
  );
}

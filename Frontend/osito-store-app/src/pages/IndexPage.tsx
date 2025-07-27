import { useEffect, useState } from "react";
import Banner from "../components/Banner";
import PostCard from "../components/PostCard";
import type { Post } from "../types";
import { getPosts } from "../api/postApi";


export default function IndexPage() {

  const [posts, setPosts] = useState<Post[]>([]);

  useEffect(() => {
    getPosts().then(setPosts).catch(console.error);
  }, []);

  return (
    <div>
      <Banner
      imageUrl="/images/index.jpg"
      title="Bienvenidos a Osito Store - Tu ropa, tu estilo."
      />

    <div className="w-full">
      <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 lg:grid-cols-5 gap-4">

        {posts.map((post) => (
          <PostCard key={post.id} post={post} />
        ))}
      
      </div>
      </div>
    </div>
  );
}


import { Link } from "react-router-dom";
import type { Post } from "../types";



export default function PostCard({post}: {post: Post}) {
  
  return (
    <article
      className="w-full h-[420px] max-w-xs rounded-3xl bg-white p-4 shadow-sm flex
      flex-col justify-between"
      key={post.id}
    >
      <div className="flex justify-center items-center w-full h-60">
        <img
          className="h-full object-contain"
          src="public/images/Adidas.jpeg"
          alt="Zapatilla Adidas"
        />
      </div>

      <div className="mt-4 flex flex-col flex-1 justify-between text-center">
        <h4 className="text-center text-2xl">{post.title}</h4>
      </div>

      <div className="mt-4 flex justify-between items-center">
        <span className="text-lg font-bold text-green-600">${post.price}</span>
        <Link to={`/posts/${post.id}`}>
        <button className="text-sm bg-black text-white px-3 py-1 rounded-lg hover:bg-gray-800">
          Ver más
        </button>
        </Link>
      </div>
    </article>
  );
}

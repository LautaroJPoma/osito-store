import type { Post } from "../types";

export default function BaseCard({ post }: { post: Post }) {
  
  const allVariantImages = post.variants?.flatMap(variant => variant.imageUrls || []) || [];
  const displayImage = allVariantImages[0];

  return (
    <div className="flex items-center w-full h-[300px] bg-white rounded-2xl shadow-md p-4 mb-4 ">
     
      <div className=" flex-shrink-0 bg-gray-100 rounded-xl overflow-hidden">
        <img
          src={displayImage}
          alt={post.title}
          onError={(e) => {
            (e.target as HTMLImageElement).src = "/images/default-image.png";
          }}
          className="w-[200px] h-[200px] "
        />
      </div>

      
      <div className="ml-6 flex flex-col justify-between flex-1">
        <h2 className="text-xl font-semibold line-clamp-2">{post.title}</h2>
        
        {post.description && (
          <p className="text-gray-600 text-sm line-clamp-2 mt-1">
            {post.description}
          </p>
        )}

        <div className="flex justify-between items-center mt-2">
          <span className="text-lg font-bold text-green-600">
            ${post.price.toLocaleString()}
          </span>
          
        
         
        </div>
      </div>
    </div>
  );
}
  
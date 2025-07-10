interface BannerProps {
    imageUrl: string;
    title: string;
}

export default function Banner({imageUrl, title}: BannerProps) {
  return (
    <div className="relative h-160 w-full overflow-hidden">
        <img
          className=" w-full object-cover"
          src={imageUrl}
          alt="index image"
        />
        <div className="absolute inset-0 bg-black/40">
          <div className="absolute inset-0 flex items-center justify-center">
            <h2 className="text-white text-4xl font-bold text-center">
              {title}
            </h2>
          </div>
        </div>
      </div>
  )
}

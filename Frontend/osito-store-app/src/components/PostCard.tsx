export default function PostCard() {
  return (
    <article
      className="w-full h-[450px] max-w-xs rounded-3xl bg-white p-4 shadow-sm flex
      flex-col justify-between"
    >
      <div className="flex justify-center items-center w-full h-60">
        <img
          className="h-full object-contain"
          src="public/images/Adidas.jpeg"
          alt="Zapatilla Adidas"
        />
      </div>

      <div className="mt-4 flex flex-col flex-1 justify-between text-center">
        <h4 className="text-center text-2xl">My title</h4>
        <p className="text-center text-gray-400">
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Quisquam,
          quos.
        </p>
      </div>

      <div className="mt-4 flex justify-between items-center">
        <span className="text-lg font-bold text-green-600">$9.999</span>
        <button className="text-sm bg-black text-white px-3 py-1 rounded-lg hover:bg-gray-800">
          Ver más
        </button>
      </div>
    </article>
  );
}

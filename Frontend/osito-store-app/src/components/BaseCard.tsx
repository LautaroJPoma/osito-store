export default function BaseCard() {
    return (
      <div className="flex items-center bg-white rounded-2xl shadow-md p-4 mb-4 w-full ">
        <img
          src="/images/Adidas.jpeg"
          alt="Zapatilla Adidas"
          className="w-50 h-50 object-contain rounded-xl"
        />
        <div className="ml-6 flex flex-col justify-between h-full flex-1">
          <h2 className="text-xl font-semibold">Zapatilla Adidas</h2>
          <p className="text-gray-600 text-sm">
            Zapatillas urbanas, cómodas y versátiles.
          </p>
          <div className="flex justify-between items-center mt-2">
            <span className="text-lg font-bold text-green-600">$9.999</span>
           
          </div>
        </div>
      </div>
    );
  }
  
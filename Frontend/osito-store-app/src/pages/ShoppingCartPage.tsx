import BaseCard from "../components/BaseCard";


export default function ShoppingCartPage() {
  return (
    <div className="p-4">
      <h1 className="text-2xl text-center font-black mb-6">
        Carrito de compras
      </h1>
      <div className="flex items-center justify-center">
        <BaseCard />
      </div>
    </div>
  )
}

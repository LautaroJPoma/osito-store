import Question from "../components/Question";

export default function HelpPage() {
  return (
    <div className="p-4 max-w-3xl mx-auto">
      <h1 className="text-2xl text-center font-black mb-4">Ayuda</h1>
      <h2 className="text-lg text-center font-bold mb-6">
        Preguntas Frecuentes
      </h2>

      <Question
        question="¿Cómo puedo realizar una compra?"
        answer="Para realizar una compra, debes seleccionar la categoría de productos y elegir el producto que deseas comprar. Una vez seleccionado, podrás agregar el producto al carrito de compras. Luego, podrás proceder al pago y finalizar la compra."
      />

      <Question
        question="¿Necesito crear una cuenta para comprar?"
        answer="Si, para realizar una compra, debes crear una cuenta. Puedes hacerlo en la sección de registro de la tienda."
      />

      <Question
        question="¿Es seguro comprar en la tienda?"
        answer="Sí, es seguro comprar en la tienda. Todos nuestros productos son de alta calidad y tienen una garantía de 1 año."
      />

      <Question
        question="¿Ques metodos de pago estan disponibles?"
        answer="Actualmente, aceptamos pagos en efectivo, transferencia bancaria y tarjetas de crédito."
      />

      <Question
        question="¿Puedo contactar con el vendedor?"
        answer="Si, puedes contactar con el vendedor a traves del chat de Osito Store. Cualquier duda o consulta, el vendedor te responderá lo antes posible."
      />

      <Question
        question="¿Cómo contacto al soporte?"
        answer="Podés escribirnos desde la sección de contacto o enviar un correo a soporte@ositostore.com."
      />

      <h2 className="text-lg text-center font-bold mb-6">
        ¿Cómo usar la tienda?
      </h2>
      <ol className="list-decimal pl-6 mb-6 text-gray-700">
        <li className="text-lg">Registrate con tu email y contraseña.</li>
        <li className="text-lg">Explorá los productos desde la sección de categorías.</li>
        <li className="text-lg">Agregá tus productos favoritos al carrito.</li>
        <li className="text-lg">Revisá el carrito y realizá el pago.</li>
        <li className="text-lg">Esperá el correo de confirmación con el estado de tu pedido.</li>
      </ol>

      <h2 className="text-lg text-center font-bold mb-6">
        Contacta con nosotros
      </h2>
      <div className="w-full  bg-white shadow-lg rounded-2xl p-12">
        <form className="space-y-6">
          <div>
            <label htmlFor="name" className="text-lg font-semibold block mb-2">
              Nombre
            </label>
            <input
              className="w-full p-4 border border-gray-300 rounded text-lg"
              type="name"
              id="name"
              placeholder="Escriba su nombre"
            />
          </div>
          <div>
            <label htmlFor="email" className="text-lg font-semibold block mb-2">
              Email
            </label>
            <input
              className="w-full p-4 border border-gray-300 rounded text-lg"
              type="email"
              id="email"
              placeholder="Escriba su email"
            />
          </div>

          <div>
            <textarea
              className="w-full p-4 border border-gray-300 rounded text-lg"
              id="message"
              placeholder="Escriba su mensaje"
            />
          </div>

          <button
            type="submit"
            className="w-full bg-black text-white py-4 rounded-lg text-lg hover:bg-gray-800 transition"
          >
            Ingresar
          </button>
        </form>
      </div>
    </div>
  );
}

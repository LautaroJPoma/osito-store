import { useParams } from "react-router-dom";
import Banner from "../components/Banner";


export default function CategoriesPage() {

  const { categoryName } = useParams();

  return (
    <div>

      <Banner 
      imageUrl="public/images/zapatos.jpg"
      title={categoryName || "Categoria"}
      />
    </div>
  )
}

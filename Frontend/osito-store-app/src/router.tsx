import { BrowserRouter, Routes,Route } from "react-router-dom";
import IndexPage from "./pages/IndexPage";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import Layout from "./layouts/Layout";
import TendenciesPage from "./pages/TendenciesPage";
import CategoriesPage from "./pages/CategoriesPage";
import FavoritesPage from "./pages/FavoritesPage";
import ShoppingCartPage from "./pages/ShoppingCartPage";
import HelpPage from "./pages/HelpPage";
import NewsPage from "./pages/NewsPage";
import OffersPage from "./pages/OffersPage";
import CreatePostPage from "./pages/CreatePostPage";
import PostPage from "./pages/PostPage";


export default function AppRouter(){

    return(
        <BrowserRouter>
            <Routes>
                <Route element={<Layout/>}>
                    <Route path='/' element={<IndexPage/>}/>
                   
                    <Route path='/tendencies' element={<TendenciesPage/>} />
                    <Route path='/category/:categoryName' element={<CategoriesPage/>} />
                    <Route path='/favorites' element={<FavoritesPage/>} />
                    <Route path='/cart' element={<ShoppingCartPage/>} />
                    <Route path='/help' element={<HelpPage/>} />
                    <Route path='/news' element={<NewsPage/>} />
                    <Route path='/offers' element={<OffersPage/>} />
                    <Route path='/posts/:id' element={<PostPage/>} />
                </Route>
                
                <Route path='/create-post' element={<CreatePostPage/>} />
                <Route path='/login' element={<LoginPage/>}/>
                <Route path='/register' element={<RegisterPage/>} />
            </Routes>
        
        </BrowserRouter>
    )
}
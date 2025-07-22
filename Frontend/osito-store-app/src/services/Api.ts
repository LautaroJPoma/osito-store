import axios from "axios";

export interface Category {
    id: number;
    name: string;
}

export interface Post {
    id?: number;
    title: string;
    description: string;
    price: number;
    stock: number;
    categoryId: number;
}

export interface User {
    id?: number;
    name: string;
    email: string;
    password: string;
}

const api = axios.create({
    baseURL: "http://localhost:8080/api",
    timeout: 5000,
});

export const getCategories = async (): Promise<Category[]> => {
    try {
        const response = await api.get<Category[]>("/categories");
        return response.data;
    } catch (error) {
        console.error("Error conectando a la API", error);
        throw error;
    }
};


export const registerUser = async (userData: Omit<User, "id">): Promise<User> => {
    try {
        const response = await api.post<User>("/users", userData);
        return response.data;
    } catch (error) {
        console.error("Error al registrar el usuario", error);
        throw error;
    }
};

export const createPost = async (postData: Omit<Post, "id">): Promise<Post> => {
    try {
        const response = await api.post<Post>("/posts", postData);
        return response.data;
    } catch (error) {
        if (axios.isAxiosError(error)) {
            const serverMessage = error.response?.data?.message || error.message;
            throw new Error(serverMessage);
        }
        throw new Error("Error desconocido al crear la publicación");
    }
};
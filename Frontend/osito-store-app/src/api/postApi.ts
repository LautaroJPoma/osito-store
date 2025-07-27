import axios from "axios";
import type { Post } from "../types";
import { api } from "./axios";

export const getPostById = async (id: number): Promise<Post> => {
    const res = await api.get<Post>(`/posts/${id}`);
    return res.data;
  };
  

export const getPosts = async (): Promise<Post[]> => {
    const res = await axios.get("http://localhost:8080/api/posts");
    return res.data;
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
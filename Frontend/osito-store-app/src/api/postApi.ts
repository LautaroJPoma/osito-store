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

export const getPostsByUserId = async (userId: number): Promise<Post[]> => {
  const response = await api.get(`/posts/user/${userId}`);
  return response.data;
};



export const createPost = async (postData: Omit<Post, "id" | "imageUrls" | "variants"> & { variants: { color: string; size: string }[] }): Promise<Post> => {
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


export const uploadVariantImages = (variantId: number, formData: FormData) => {
  return api.post(`/variants/${variantId}/images`, formData, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
};



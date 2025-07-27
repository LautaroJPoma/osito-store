import type { Category } from "../types";
import { api } from "./axios";

export const getCategories = async (): Promise<Category[]> => {
    try {
        const response = await api.get<Category[]>("/categories");
        return response.data;
    } catch (error) {
        console.error("Error conectando a la API", error);
        throw error;
    }
};
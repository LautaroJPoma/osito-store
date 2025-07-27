import type { User } from "../types";
import { api } from "./axios";

export const registerUser = async (userData: Omit<User, "id">): Promise<User> => {
    try {
        const response = await api.post<User>("/users", userData);
        return response.data;
    } catch (error) {
        console.error("Error al registrar el usuario", error);
        throw error;
    }
};
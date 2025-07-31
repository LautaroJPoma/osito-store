import type { AuthenticationResponse, RegisterRequest } from "../types";
import { api } from "./axios";

export const registerUser = async (userData: RegisterRequest): Promise<AuthenticationResponse> => {
  try {
    const response = await api.post<AuthenticationResponse>("/auth/register", userData);
    return response.data;
  } catch (error) {
    console.error("Error al registrar el usuario", error);
    throw error;
  }
};

export const loginUser = async (loginData: { email: string, password: string }): Promise<AuthenticationResponse> => {
    try {
      const response = await api.post<AuthenticationResponse>("/auth/authenticate", loginData);
      return response.data;
    } catch (error) {
      console.error("Error al iniciar sesión", error);
      throw error;
    }
  };
  
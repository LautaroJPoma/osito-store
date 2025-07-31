import axios from "axios";

export const api = axios.create({
    baseURL: "http://localhost:8080/api",
    timeout: 5000,
});

api.interceptors.request.use((config) => {
  
  if (config.url && (config.url.includes("/auth/authenticate") || config.url.includes("/auth/register"))) {
    return config;
  }

  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

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

export interface AuthenticationResponse {
    token: string;
    username: string;
  }

  export interface RegisterRequest {
    name: string;
    email: string;
    password: string;
  }
  
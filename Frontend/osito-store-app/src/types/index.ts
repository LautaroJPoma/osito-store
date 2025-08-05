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
    username: string;
    email: string;
    password: string;
}

export interface UserProfileData {
  username: string;
  email: string;
}

export interface AuthenticationResponse {
    token: string;
    username: string;
    email: string;
  }

  export interface RegisterRequest {
    name: string;
    email: string;
    password: string;
  }

  export interface ChangePasswordRequest {
    email: string;
    oldPassword: string;
    newPassword: string;
  }

  export type Tab = {
    id: string,
    label: string;
    icon?: React.ReactNode;
  }
  
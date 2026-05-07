export interface User {
  id: number;
  email: string;
  nombre: string;
  rol: 'DUEÑO' | 'REVENDEDOR';
}

export interface AuthResponse {
  token: string;
  user: User;
}

export interface LoginCredentials {
  email: string;
  password: string;
}

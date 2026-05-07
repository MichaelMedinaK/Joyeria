import { AuthResponse, LoginCredentials, User } from '@shared/types';

const MOCK_USER: User = {
  id: 1,
  email: 'admin@joyerianicki.com',
  nombre: 'Administrador Nicki',
  rol: 'DUEÑO'
};

const MOCK_TOKEN = 'mock-jwt-token-12345';

export const authService = {
  login: async (credentials: LoginCredentials): Promise<AuthResponse> => {
    // Simular delay de red
    await new Promise(resolve => setTimeout(resolve, 500));

    // Validación simple para demo
    if (credentials.email && credentials.password) {
      return {
        token: MOCK_TOKEN,
        user: MOCK_USER
      };
    }

    throw new Error('Credenciales inválidas');
  },

  logout: async (): Promise<void> => {
    await new Promise(resolve => setTimeout(resolve, 300));
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  },

  getCurrentUser: (): User | null => {
    const userStr = localStorage.getItem('user');
    return userStr ? JSON.parse(userStr) : null;
  },

  getToken: (): string | null => {
    return localStorage.getItem('token');
  },

  saveAuth: (token: string, user: User): void => {
    localStorage.setItem('token', token);
    localStorage.setItem('user', JSON.stringify(user));
  },

  isAuthenticated: (): boolean => {
    return !!localStorage.getItem('token');
  }
};

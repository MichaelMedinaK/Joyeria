import { Producto } from '@shared/types';

const MOCK_PRODUCTOS: Producto[] = [
  {
    id: 1,
    nombre: 'Anillo de Oro 18k',
    descripcion: 'Anillo de oro de 18 kilates con diseño elegante',
    precio_compra: 150000,
    precio_venta: 250000,
    activo: true
  },
  {
    id: 2,
    nombre: 'Collar de Plata',
    descripcion: 'Collar de plata 925 con dije de corazón',
    precio_compra: 80000,
    precio_venta: 150000,
    activo: true
  },
  {
    id: 3,
    nombre: 'Pulsera de Oro',
    descripcion: 'Pulsera de oro de 14 kilates',
    precio_compra: 200000,
    precio_venta: 350000,
    activo: true
  }
];

export const productoService = {
  getAll: async (): Promise<Producto[]> => {
    await new Promise(resolve => setTimeout(resolve, 300));
    return [...MOCK_PRODUCTOS];
  },

  getById: async (id: number): Promise<Producto | null> => {
    await new Promise(resolve => setTimeout(resolve, 300));
    return MOCK_PRODUCTOS.find(p => p.id === id) || null;
  }
};

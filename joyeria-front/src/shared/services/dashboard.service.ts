import { DashboardStats } from '@shared/types';

export const dashboardService = {
  getStats: async (): Promise<DashboardStats> => {
    await new Promise(resolve => setTimeout(resolve, 400));

    return {
      ventas_del_dia: 5,
      ganancia_del_dia: 450000,
      pedidos_pendientes: 3,
      productos_bajo_stock: 2,
      stock_en_revendedores: 8
    };
  }
};

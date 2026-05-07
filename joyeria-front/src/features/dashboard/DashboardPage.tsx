import { useEffect, useState } from 'react';
import { dashboardService } from '@shared/services';
import { DashboardStats } from '@shared/types';
import { formatCurrency } from '@shared/utils';

interface StatCardProps {
  title: string;
  value: string | number;
  icon: string;
  color: string;
}

const StatCard = ({ title, value, icon, color }: StatCardProps) => {
  return (
    <div className="card hover:shadow-lg cursor-pointer transform hover:-translate-y-1 transition-all duration-200">
      <div className="flex items-center justify-between">
        <div className="flex-1">
          <p className="text-xs sm:text-sm text-gray-600 mb-1">{title}</p>
          <p className="text-xl sm:text-2xl font-bold text-gray-900">{value}</p>
        </div>
        <div className={`text-3xl sm:text-4xl ${color}`}>
          {icon}
        </div>
      </div>
    </div>
  );
};

export const DashboardPage = () => {
  const [stats, setStats] = useState<DashboardStats | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadStats();
  }, []);

  const loadStats = async () => {
    try {
      const data = await dashboardService.getStats();
      setStats(data);
    } catch (error) {
      console.error('Error al cargar estadísticas:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center h-64">
        <div className="text-lg text-gray-600">Cargando estadísticas...</div>
      </div>
    );
  }

  if (!stats) {
    return (
      <div className="flex items-center justify-center h-64">
        <div className="text-lg text-gray-600">No se pudieron cargar las estadísticas</div>
      </div>
    );
  }

  return (
    <div>
      <div className="mb-6 sm:mb-8">
        <h1 className="text-2xl sm:text-3xl font-bold text-gray-900 tracking-wide">Dashboard</h1>
        <p className="text-sm sm:text-base text-accent-600 font-medium">Resumen general del sistema</p>
      </div>

      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4 sm:gap-6">
        <StatCard
          title="Ventas del Día"
          value={stats.ventas_del_dia}
          icon="🛍️"
          color="text-blue-500"
        />
        
        <StatCard
          title="Ganancia del Día"
          value={formatCurrency(stats.ganancia_del_dia)}
          icon="💰"
          color="text-accent-600"
        />
        
        <StatCard
          title="Pedidos Pendientes"
          value={stats.pedidos_pendientes}
          icon="📋"
          color="text-yellow-500"
        />
        
        <StatCard
          title="Productos Bajo Stock"
          value={stats.productos_bajo_stock}
          icon="⚠️"
          color="text-red-500"
        />
        
        <StatCard
          title="Stock en Revendedores"
          value={stats.stock_en_revendedores}
          icon="📦"
          color="text-primary-500"
        />

        <div className="card bg-gradient-to-br from-primary-100 to-primary-200 hover:shadow-lg cursor-pointer transform hover:-translate-y-1 transition-all duration-200 border-primary-200">
          <div className="flex items-center justify-between">
            <div>
              <p className="text-sm text-primary-700 mb-1">Sistema Activo</p>
              <p className="text-2xl font-bold text-primary-800">Todo OK</p>
            </div>
            <div className="text-4xl">✅</div>
          </div>
        </div>
      </div>

      <div className="mt-6 sm:mt-8 grid grid-cols-1 lg:grid-cols-2 gap-4 sm:gap-6">
        <div className="card">
          <h2 className="text-lg sm:text-xl font-semibold text-gray-900 mb-4">
            Acciones Rápidas
          </h2>
          <div className="space-y-2 sm:space-y-3">
            <button className="w-full btn-primary text-left text-sm sm:text-base">
              ➕ Crear Nuevo Pedido
            </button>
            <button className="w-full btn-secondary text-left text-sm sm:text-base">
              💎 Agregar Producto
            </button>
            <button className="w-full btn-secondary text-left text-sm sm:text-base">
              👥 Registrar Cliente
            </button>
          </div>
        </div>

        <div className="card">
          <h2 className="text-lg sm:text-xl font-semibold text-gray-900 mb-4">
            Actividad Reciente
          </h2>
          <div className="space-y-3 text-sm">
            <div className="flex items-center gap-3 pb-3 border-b border-gray-200 rounded-lg hover:bg-primary-50 p-2 transition-colors">
              <span className="text-2xl">🛒</span>
              <div className="flex-1">
                <p className="font-medium text-gray-900">Pedido #1234 creado</p>
                <p className="text-gray-500">Hace 5 minutos</p>
              </div>
            </div>
            <div className="flex items-center gap-3 pb-3 border-b border-gray-200 rounded-lg hover:bg-primary-50 p-2 transition-colors">
              <span className="text-2xl">💰</span>
              <div className="flex-1">
                <p className="font-medium text-gray-900">Venta revendedor registrada</p>
                <p className="text-gray-500">Hace 23 minutos</p>
              </div>
            </div>
            <div className="flex items-center gap-3 rounded-lg hover:bg-primary-50 p-2 transition-colors">
              <span className="text-2xl">📦</span>
              <div className="flex-1">
                <p className="font-medium text-gray-900">Stock actualizado</p>
                <p className="text-gray-500">Hace 1 hora</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

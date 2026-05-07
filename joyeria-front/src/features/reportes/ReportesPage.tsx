export const ReportesPage = () => {
  return (
    <div>
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900 tracking-wide">Reportes</h1>
        <p className="text-accent-600 font-medium">Análisis y reportes del sistema</p>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-6">
        <div className="card hover:shadow-lg cursor-pointer transform hover:-translate-y-1 transition-all duration-200 hover:bg-primary-50">
          <h3 className="text-lg font-semibold text-gray-900 mb-2">📊 Reporte de Ventas</h3>
          <p className="text-sm text-gray-600">Análisis de ventas por período</p>
        </div>

        <div className="card hover:shadow-lg cursor-pointer transform hover:-translate-y-1 transition-all duration-200 hover:bg-primary-50">
          <h3 className="text-lg font-semibold text-gray-900 mb-2">💰 Reporte de Ganancias</h3>
          <p className="text-sm text-gray-600">Ganancias por producto y revendedor</p>
        </div>

        <div className="card hover:shadow-lg cursor-pointer transform hover:-translate-y-1 transition-all duration-200 hover:bg-primary-50">
          <h3 className="text-lg font-semibold text-gray-900 mb-2">📦 Reporte de Stock</h3>
          <p className="text-sm text-gray-600">Estado actual del inventario</p>
        </div>

        <div className="card hover:shadow-lg cursor-pointer transform hover:-translate-y-1 transition-all duration-200 hover:bg-primary-50">
          <h3 className="text-lg font-semibold text-gray-900 mb-2">🤝 Reporte de Revendedores</h3>
          <p className="text-sm text-gray-600">Desempeño de revendedores</p>
        </div>
      </div>

      <div className="card">
        <div className="text-center py-12 text-gray-500">
          <div className="text-6xl mb-4">📈</div>
          <p className="text-lg">Módulo de reportes</p>
          <p className="text-sm mt-2">Visualización de estadísticas y análisis</p>
        </div>
      </div>
    </div>
  );
};

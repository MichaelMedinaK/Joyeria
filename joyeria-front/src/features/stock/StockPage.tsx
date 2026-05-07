export const StockPage = () => {
  return (
    <div>
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900 tracking-wide">Stock</h1>
        <p className="text-accent-600 font-medium">Control de inventario general y por revendedor</p>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <div className="card">
          <h2 className="text-xl font-semibold text-gray-900 mb-4">
            📦 Stock del Dueño
          </h2>
          <div className="text-center py-8 text-gray-500">
            <p>Stock principal del negocio</p>
          </div>
        </div>

        <div className="card">
          <h2 className="text-xl font-semibold text-gray-900 mb-4">
            🤝 Stock por Revendedor
          </h2>
          <div className="text-center py-8 text-gray-500">
            <p>Stock asignado a revendedores</p>
          </div>
        </div>
      </div>

      <div className="card mt-6">
        <div className="text-center py-12 text-gray-500">
          <div className="text-6xl mb-4">📊</div>
          <p className="text-lg">Módulo de gestión de stock</p>
          <p className="text-sm mt-2">Visualización y control de inventario</p>
        </div>
      </div>
    </div>
  );
};

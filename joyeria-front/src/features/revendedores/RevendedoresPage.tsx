export const RevendedoresPage = () => {
  return (
    <div>
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900 tracking-wide">Revendedores</h1>
        <p className="text-accent-600 font-medium">Gestión de revendedores y asignación de stock</p>
      </div>

      <div className="card">
        <div className="flex items-center justify-between mb-6">
          <div className="flex-1 max-w-md">
            <input
              type="search"
              placeholder="Buscar revendedores..."
              className="input-field"
            />
          </div>
          <button className="btn-primary ml-4">
            ➕ Nuevo Revendedor
          </button>
        </div>

        <div className="text-center py-12 text-gray-500">
          <div className="text-6xl mb-4">🤝</div>
          <p className="text-lg">Módulo de revendedores</p>
          <p className="text-sm mt-2">CRUD de revendedores y asignación de stock</p>
          <p className="text-xs mt-2 text-gray-400">Control de inventario por revendedor</p>
        </div>
      </div>
    </div>
  );
};

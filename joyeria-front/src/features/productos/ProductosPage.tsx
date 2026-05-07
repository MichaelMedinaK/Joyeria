export const ProductosPage = () => {
  return (
    <div>
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900 tracking-wide">Productos</h1>
        <p className="text-accent-600 font-medium">Gestión de productos de joyería</p>
      </div>

      <div className="card">
        <div className="flex items-center justify-between mb-6">
          <div className="flex-1 max-w-md">
            <input
              type="search"
              placeholder="Buscar productos..."
              className="input-field"
            />
          </div>
          <button className="btn-primary ml-4">
            ➕ Nuevo Producto
          </button>
        </div>

        <div className="text-center py-12 text-gray-500">
          <div className="text-6xl mb-4">💎</div>
          <p className="text-lg">Módulo de productos</p>
          <p className="text-sm mt-2">Aquí se listará y gestionará el catálogo de productos</p>
        </div>
      </div>
    </div>
  );
};

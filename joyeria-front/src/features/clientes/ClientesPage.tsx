export const ClientesPage = () => {
  return (
    <div>
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900 tracking-wide">Clientes</h1>
        <p className="text-accent-600 font-medium">Base de datos de clientes</p>
      </div>

      <div className="card">
        <div className="flex items-center justify-between mb-6">
          <div className="flex-1 max-w-md">
            <input
              type="search"
              placeholder="Buscar clientes..."
              className="input-field"
            />
          </div>
          <button className="btn-primary ml-4">
            ➕ Nuevo Cliente
          </button>
        </div>

        <div className="text-center py-12 text-gray-500">
          <div className="text-6xl mb-4">👥</div>
          <p className="text-lg">Módulo de clientes</p>
          <p className="text-sm mt-2">CRUD completo de clientes</p>
          <p className="text-xs mt-2 text-gray-400">Nombre, teléfono, email, documento</p>
        </div>
      </div>
    </div>
  );
};

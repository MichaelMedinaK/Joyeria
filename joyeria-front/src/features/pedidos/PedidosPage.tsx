export const PedidosPage = () => {
  return (
    <div>
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900 tracking-wide">Pedidos</h1>
        <p className="text-accent-600 font-medium">Gestión de pedidos y ventas a clientes</p>
      </div>

      <div className="card">
        <div className="flex items-center justify-between mb-6">
          <div className="flex gap-3">
            <button className="btn-secondary">
              Todos
            </button>
            <button className="btn-secondary">
              Pendientes
            </button>
            <button className="btn-secondary">
              Completados
            </button>
          </div>
          <button className="btn-primary">
            ➕ Nuevo Pedido
          </button>
        </div>

        <div className="text-center py-12 text-gray-500">
          <div className="text-6xl mb-4">🛒</div>
          <p className="text-lg">Módulo de pedidos</p>
          <p className="text-sm mt-2">Crear y gestionar pedidos con carrito de compras</p>
          <p className="text-xs mt-2 text-gray-400">Incluye cálculo de delivery según kilómetros</p>
        </div>
      </div>
    </div>
  );
};

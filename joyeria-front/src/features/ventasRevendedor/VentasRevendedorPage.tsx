export const VentasRevendedorPage = () => {
  return (
    <div>
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900 tracking-wide">Ventas de Revendedores</h1>
        <p className="text-accent-600 font-medium">Registro de ventas realizadas por revendedores</p>
      </div>

      <div className="card">
        <div className="flex items-center justify-between mb-6">
          <div className="flex gap-3">
            <select className="input-field max-w-xs">
              <option value="">Todos los revendedores</option>
              <option value="1">Revendedor 1</option>
              <option value="2">Revendedor 2</option>
            </select>
          </div>
          <button className="btn-primary">
            ➕ Registrar Venta
          </button>
        </div>

        <div className="text-center py-12 text-gray-500">
          <div className="text-6xl mb-4">💰</div>
          <p className="text-lg">Módulo de ventas de revendedores</p>
          <p className="text-sm mt-2">Registrar ventas y calcular ganancias</p>
          <p className="text-xs mt-2 text-gray-400">Descuenta stock del revendedor automáticamente</p>
        </div>
      </div>
    </div>
  );
};

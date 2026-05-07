export interface Stock {
  id: number;
  producto_id: number;
  producto_nombre?: string;
  cantidad_actual: number;
  stock_minimo: number;
  revendedor_id?: number;
  revendedor_nombre?: string;
  es_dueño: boolean;
}

export interface StockByRevendedor {
  revendedor_id: number;
  revendedor_nombre: string;
  productos: Stock[];
}

export interface PedidoItem {
  producto_id: number;
  producto_nombre?: string;
  cantidad: number;
  precio_unitario: number;
  subtotal: number;
}

export interface Pedido {
  id: number;
  cliente_id: number;
  cliente_nombre?: string;
  fecha: string;
  subtotal: number;
  costo_delivery: number;
  total: number;
  kilometros: number;
  estado: 'PENDIENTE' | 'COMPLETADO' | 'CANCELADO';
  items: PedidoItem[];
}

export interface CreatePedidoDto {
  cliente_id: number;
  kilometros: number;
  items: Array<{
    producto_id: number;
    cantidad: number;
    precio_unitario: number;
  }>;
}

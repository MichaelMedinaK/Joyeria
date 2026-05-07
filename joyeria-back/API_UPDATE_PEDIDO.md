# API de Actualización de Pedidos

## Endpoint Flexible de Actualización

**PATCH** `/api/pedidos/{id}`

Este endpoint permite actualizar un pedido de manera flexible. Solo se actualizan los campos que envíes en el request body.

### Autenticación
Requiere rol: `ADMIN` o `VENDEDOR`

### Request Body (Todos los campos son opcionales)

```json
{
  "idCliente": 2,           // Cambia el cliente (y por ende la dirección)
  "estado": "EN_CAMINO",   // Cambia el estado del pedido
  "kilometros": 10.5,      // Modifica los kilómetros (recalcula delivery y total)
  "detalles": [            // Actualiza los productos y cantidades
    {
      "idProducto": 1,
      "cantidad": 3
    },
    {
      "idProducto": 5,
      "cantidad": 1
    }
  ]
}
```

### Ejemplos de Uso

#### 1. Solo cambiar el estado
```json
{
  "estado": "ENTREGADO"
}
```

#### 2. Solo cambiar el cliente (dirección)
```json
{
  "idCliente": 5
}
```

#### 3. Solo cambiar kilómetros
```json
{
  "kilometros": 8.3
}
```
*Nota: Esto recalculará automáticamente el costo de delivery y el total.*

#### 4. Solo modificar productos y cantidades
```json
{
  "detalles": [
    {
      "idProducto": 2,
      "cantidad": 5
    },
    {
      "idProducto": 8,
      "cantidad": 2
    }
  ]
}
```
*Nota: Esto devuelve el stock de los productos anteriores y descuenta el nuevo stock. Recalcula subtotal, ganancia y total.*

#### 5. Cambiar varias cosas a la vez
```json
{
  "estado": "EN_PROCESO",
  "kilometros": 12,
  "detalles": [
    {
      "idProducto": 3,
      "cantidad": 2
    }
  ]
}
```

### Estados Válidos
- `PENDIENTE`
- `EN_PROCESO`
- `EN_CAMINO`
- `ENTREGADO`
- `CANCELADO`

### Respuesta Exitosa
```json
{
  "success": true,
  "message": "Pedido actualizado exitosamente",
  "data": {
    "idPedido": 4,
    "idCliente": 2,
    "nombreCliente": "Cliente Actualizado",
    "idUsuario": 1,
    "nombreUsuario": "Admin",
    "estado": "EN_CAMINO",
    "kilometros": 10.5,
    "subtotal": 80000,
    "costoDelivery": 11000,
    "total": 91000,
    "gananciaTotal": 80000,
    "fechaPedido": "2026-05-07T15:59:50.260751",
    "detalles": [...]
  },
  "timestamp": "2026-05-07T16:30:00"
}
```

### Errores Comunes

#### Cliente no existe
```json
{
  "success": false,
  "message": "Cliente no encontrado con id: 999"
}
```

#### Estado inválido
```json
{
  "success": false,
  "message": "Estado inválido: ESTADO_INVENTADO"
}
```

#### Stock insuficiente
```json
{
  "success": false,
  "message": "Stock insuficiente para Pulsera Premium. Disponible: 5, Solicitado: 10"
}
```

### Comportamiento del Stock
Cuando actualizas los detalles:
1. **Devuelve** el stock de los productos del pedido anterior
2. **Descuenta** el stock de los nuevos productos
3. **Valida** que haya stock suficiente antes de aplicar cambios
4. **Recalcula** automáticamente subtotal, ganancia y total

### Recálculo Automático
El sistema recalcula automáticamente:
- **Costo de delivery** cuando cambias los kilómetros
- **Subtotal y ganancia** cuando cambias los detalles
- **Total** cuando cambia el subtotal o el delivery

### Notas Importantes
- Puedes enviar solo los campos que quieras modificar
- Los campos que no envíes se mantienen sin cambios
- Si envías `detalles`, debes enviar la lista completa (reemplaza todos los productos)
- Los cambios de stock son transaccionales (si falla algo, no se aplica nada)

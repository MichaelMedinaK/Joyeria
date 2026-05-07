# Colección de Endpoints - Sistema Joyería

## Variables
```
BASE_URL=http://localhost:8080
TOKEN=tu_token_jwt_aqui
```

---

## 1. AUTENTICACIÓN

### 1.1 Registro de Usuario
```http
POST {{BASE_URL}}/api/auth/register
Content-Type: application/json

{
  "nombre": "Admin Principal",
  "email": "admin@joyeria.com",
  "password": "admin123",
  "rol": "ADMIN"
}
```

### 1.2 Login
```http
POST {{BASE_URL}}/api/auth/login
Content-Type: application/json

{
  "email": "admin@joyeria.com",
  "password": "admin123"
}
```

---

## 2. CLIENTES

### 2.1 Listar Clientes
```http
GET {{BASE_URL}}/api/clientes
Authorization: Bearer {{TOKEN}}
```

### 2.2 Crear Cliente
```http
POST {{BASE_URL}}/api/clientes
Authorization: Bearer {{TOKEN}}
Content-Type: application/json

{
  "nombre": "María González",
  "telefono": "+56912345678",
  "email": "maria@email.com",
  "documento": "12345678-9"
}
```

### 2.3 Obtener Cliente por ID
```http
GET {{BASE_URL}}/api/clientes/1
Authorization: Bearer {{TOKEN}}
```

### 2.4 Actualizar Cliente
```http
PUT {{BASE_URL}}/api/clientes/1
Authorization: Bearer {{TOKEN}}
Content-Type: application/json

{
  "nombre": "María González Actualizado",
  "telefono": "+56987654321",
  "email": "maria.nueva@email.com",
  "documento": "12345678-9"
}
```

### 2.5 Eliminar Cliente (ADMIN)
```http
DELETE {{BASE_URL}}/api/clientes/1
Authorization: Bearer {{TOKEN}}
```

---

## 3. PRODUCTOS

### 3.1 Listar Todos los Productos
```http
GET {{BASE_URL}}/api/productos
Authorization: Bearer {{TOKEN}}
```

### 3.2 Listar Productos Activos
```http
GET {{BASE_URL}}/api/productos/activos
Authorization: Bearer {{TOKEN}}
```

### 3.3 Buscar Productos por Nombre
```http
GET {{BASE_URL}}/api/productos/buscar?nombre=anillo
Authorization: Bearer {{TOKEN}}
```

### 3.4 Crear Producto (ADMIN)
```http
POST {{BASE_URL}}/api/productos
Authorization: Bearer {{TOKEN}}
Content-Type: application/json

{
  "nombre": "Anillo de Oro 18k",
  "descripcion": "Hermoso anillo de oro con diamante central",
  "precioCompra": 150000,
  "precioVenta": 250000,
  "activo": true
}
```

### 3.5 Actualizar Producto (ADMIN)
```http
PUT {{BASE_URL}}/api/productos/1
Authorization: Bearer {{TOKEN}}
Content-Type: application/json

{
  "nombre": "Anillo de Oro 18k Premium",
  "descripcion": "Anillo premium con diamante de 1 quilate",
  "precioCompra": 180000,
  "precioVenta": 300000,
  "activo": true
}
```

### 3.6 Desactivar Producto (ADMIN)
```http
DELETE {{BASE_URL}}/api/productos/1
Authorization: Bearer {{TOKEN}}
```

---

## 4. REVENDEDORES

### 4.1 Listar Todos los Revendedores (ADMIN)
```http
GET {{BASE_URL}}/api/revendedores
Authorization: Bearer {{TOKEN}}
```

### 4.2 Listar Revendedores Activos (ADMIN)
```http
GET {{BASE_URL}}/api/revendedores/activos
Authorization: Bearer {{TOKEN}}
```

### 4.3 Crear Revendedor (ADMIN)
```http
POST {{BASE_URL}}/api/revendedores
Authorization: Bearer {{TOKEN}}
Content-Type: application/json

{
  "nombre": "Joyería El Brillante",
  "telefono": "+56923456789",
  "email": "brillante@email.com",
  "activo": true
}
```

### 4.4 Actualizar Revendedor (ADMIN)
```http
PUT {{BASE_URL}}/api/revendedores/1
Authorization: Bearer {{TOKEN}}
Content-Type: application/json

{
  "nombre": "Joyería El Brillante VIP",
  "telefono": "+56923456789",
  "email": "brillante.vip@email.com",
  "activo": true
}
```

### 4.5 Desactivar Revendedor (ADMIN)
```http
DELETE {{BASE_URL}}/api/revendedores/1
Authorization: Bearer {{TOKEN}}
```

---

## 5. STOCK

### 5.1 Ver Stock del Dueño (ADMIN)
```http
GET {{BASE_URL}}/api/stock/dueno
Authorization: Bearer {{TOKEN}}
```

### 5.2 Ver Stock de un Revendedor (ADMIN)
```http
GET {{BASE_URL}}/api/stock/revendedor/1
Authorization: Bearer {{TOKEN}}
```

### 5.3 Ver Productos con Stock Bajo (ADMIN)
```http
GET {{BASE_URL}}/api/stock/bajo
Authorization: Bearer {{TOKEN}}
```

### 5.4 Consultar Stock de un Producto Específico
```http
GET {{BASE_URL}}/api/stock/consultar?idProducto=1&idRevendedor=
Authorization: Bearer {{TOKEN}}
```

### 5.5 Crear/Actualizar Stock (ADMIN)
```http
POST {{BASE_URL}}/api/stock
Authorization: Bearer {{TOKEN}}
Content-Type: application/json

{
  "idProducto": 1,
  "idRevendedor": null,
  "cantidadActual": 50,
  "stockMinimo": 10
}
```

### 5.6 Movimiento de Stock - Entrada (ADMIN)
```http
POST {{BASE_URL}}/api/stock/movimiento
Authorization: Bearer {{TOKEN}}
Content-Type: application/json

{
  "idProducto": 1,
  "idRevendedor": null,
  "cantidad": 20,
  "tipoMovimiento": "ENTRADA"
}
```

### 5.7 Movimiento de Stock - Salida (ADMIN)
```http
POST {{BASE_URL}}/api/stock/movimiento
Authorization: Bearer {{TOKEN}}
Content-Type: application/json

{
  "idProducto": 1,
  "idRevendedor": null,
  "cantidad": 5,
  "tipoMovimiento": "SALIDA"
}
```

### 5.8 Transferir Stock a Revendedor (ADMIN)
```http
POST {{BASE_URL}}/api/stock/transferir?idProducto=1&idRevendedor=1&cantidad=10
Authorization: Bearer {{TOKEN}}
```

---

## 6. PEDIDOS

### 6.1 Listar Todos los Pedidos
```http
GET {{BASE_URL}}/api/pedidos
Authorization: Bearer {{TOKEN}}
```

### 6.2 Ver Pedido por ID
```http
GET {{BASE_URL}}/api/pedidos/1
Authorization: Bearer {{TOKEN}}
```

### 6.3 Pedidos por Cliente
```http
GET {{BASE_URL}}/api/pedidos/cliente/1
Authorization: Bearer {{TOKEN}}
```

### 6.4 Pedidos por Estado
```http
GET {{BASE_URL}}/api/pedidos/estado/PENDIENTE
Authorization: Bearer {{TOKEN}}
```

### 6.5 Pedidos por Fecha (ADMIN)
```http
GET {{BASE_URL}}/api/pedidos/fecha/2026-04-29
Authorization: Bearer {{TOKEN}}
```

### 6.6 Ver Ganancia Diaria (ADMIN)
```http
GET {{BASE_URL}}/api/pedidos/ganancia/diaria
Authorization: Bearer {{TOKEN}}
```

### 6.7 Crear Pedido
```http
POST {{BASE_URL}}/api/pedidos
Authorization: Bearer {{TOKEN}}
Content-Type: application/json

{
  "idCliente": 1,
  "idUsuario": 1,
  "estado": "PENDIENTE",
  "kilometros": 3.5,
  "detalles": [
    {
      "idProducto": 1,
      "cantidad": 2
    },
    {
      "idProducto": 2,
      "cantidad": 1
    }
  ]
}
```

### 6.8 Crear Pedido con Delivery Mayor a 6km
```http
POST {{BASE_URL}}/api/pedidos
Authorization: Bearer {{TOKEN}}
Content-Type: application/json

{
  "idCliente": 1,
  "idUsuario": 1,
  "estado": "PENDIENTE",
  "kilometros": 8.3,
  "detalles": [
    {
      "idProducto": 1,
      "cantidad": 1
    }
  ]
}
```
**Nota:** El costo de delivery será CEIL(8.3) * 1000 = $9000

### 6.9 Actualizar Estado del Pedido
```http
PATCH {{BASE_URL}}/api/pedidos/1/estado?estado=EN_PROCESO
Authorization: Bearer {{TOKEN}}
```

### 6.10 Marcar Pedido como Entregado
```http
PATCH {{BASE_URL}}/api/pedidos/1/estado?estado=ENTREGADO
Authorization: Bearer {{TOKEN}}
```

---

## NOTAS IMPORTANTES

1. **Reemplaza {{TOKEN}}** con el token JWT que recibes del login
2. **Reemplaza {{BASE_URL}}** si tu servidor corre en otro puerto
3. Los endpoints marcados con **(ADMIN)** solo son accesibles con rol ADMIN
4. Los IDs de ejemplo (1, 2, etc.) deben existir en tu base de datos
5. El sistema descuenta automáticamente el stock al crear un pedido

## FLUJO TÍPICO DE USO

1. Registrar usuario ADMIN
2. Hacer login y obtener token
3. Crear productos
4. Agregar stock inicial a los productos
5. Crear clientes
6. Crear pedidos (automáticamente descuenta stock y calcula delivery)
7. Actualizar estado de pedidos según avance
8. Consultar ganancias diarias

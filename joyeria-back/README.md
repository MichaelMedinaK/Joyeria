# 💎 Sistema de Ventas de Joyería - Backend

Sistema backend completo para gestión de ventas de joyería con arquitectura MVC, Spring Boot 3 y PostgreSQL.

## 📋 Características

### ✅ Funcionalidades Implementadas

- **Autenticación y Autorización**
  - Login con JWT
  - Registro de usuarios
  - Roles: ADMIN y VENDEDOR
  - BCrypt para encriptación de contraseñas (12 rounds)
  - Tokens JWT seguros con HS256

- **Gestión de Productos**
  - CRUD completo
  - Precio de compra y venta
  - Búsqueda por nombre
  - Soft delete (desactivación)

- **Gestión de Clientes**
  - CRUD completo
  - Validación de datos

- **Gestión de Revendedores**
  - CRUD completo
  - Control de activos/inactivos

- **Sistema de Stock**
  - Stock del dueño
  - Stock por revendedor
  - Movimientos (entrada/salida)
  - Transferencias entre dueño y revendedores
  - Alertas de stock bajo

- **Sistema de Pedidos**
  - Creación de pedidos con múltiples productos
  - Cálculo automático de delivery:
    - Si km ≤ 6 → $6000
    - Si km > 6 → CEIL(km) × $1000
  - Cálculo automático de ganancias
  - Estados: PENDIENTE, EN_PROCESO, EN_CAMINO, ENTREGADO, CANCELADO
  - Descuento automático de stock
  - Consulta de ganancias diarias

## 🛠️ Stack Tecnológico

- **Java 21**
- **Spring Boot 3.3.0**
- **Spring Security** con JWT
- **Spring Data JPA**
- **PostgreSQL**
- **Lombok**
- **MapStruct** para mapeos
- **Validation**
- **Maven**

## 📁 Estructura del Proyecto

```
src/main/java/com/joyeriajoy/joyeria_back/
├── config/                  # Configuraciones (SecurityConfig)
├── controller/              # Controllers REST
│   ├── AuthController
│   ├── ClienteController
│   ├── ProductoController
│   ├── RevendedorController
│   ├── StockController
│   └── PedidoController
├── dto/                     # Data Transfer Objects
│   ├── auth/
│   ├── cliente/
│   ├── producto/
│   ├── revendedor/
│   ├── stock/
│   ├── pedido/
│   └── common/
├── exception/               # Excepciones personalizadas
├── mapper/                  # MapStruct Mappers
├── model/entity/            # Entidades JPA
│   ├── Usuario
│   ├── Cliente
│   ├── Producto
│   ├── Revendedor
│   ├── Stock
│   ├── Pedido
│   └── PedidoDetalle
├── repository/              # Repositorios JPA
├── security/                # JWT y seguridad
│   ├── JwtUtil
│   ├── JwtAuthenticationFilter
│   └── CustomUserDetailsService
└── service/                 # Lógica de negocio
    ├── AuthService
    ├── ClienteService
    ├── ProductoService
    ├── RevendedorService
    ├── StockService
    └── PedidoService
```

## ⚙️ Configuración

### 1. Base de Datos

Actualiza `src/main/resources/application.properties` con tus credenciales:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/joyeria_db
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_CONTRASEÑA
```

### 2. JWT Secret

Para producción, cambia el secret en `application.properties`:

```properties
jwt.secret=TuClaveSecretaSuperSeguraDeAlMenos256BitsParaHS256DebeSerLargaYCompleja
jwt.expiration=86400000
```

### 3. Compilar

```bash
./mvnw clean compile
```

### 4. Ejecutar

```bash
./mvnw spring-boot:run
```

El servidor estará disponible en: `http://localhost:8080`

## 🔐 API Endpoints

### Autenticación

#### Registro
```http
POST /api/auth/register
Content-Type: application/json

{
  "nombre": "Admin User",
  "email": "admin@joyeria.com",
  "password": "password123",
  "rol": "ADMIN"
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "admin@joyeria.com",
  "password": "password123"
}
```

**Respuesta:**
```json
{
  "success": true,
  "message": "Login exitoso",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "tipo": "Bearer",
    "idUsuario": 1,
    "nombre": "Admin User",
    "email": "admin@joyeria.com",
    "rol": "ADMIN"
  }
}
```

### Clientes

#### Listar todos
```http
GET /api/clientes
Authorization: Bearer {token}
```

#### Crear cliente
```http
POST /api/clientes
Authorization: Bearer {token}
Content-Type: application/json

{
  "nombre": "Juan Pérez",
  "telefono": "+56912345678",
  "email": "juan@example.com",
  "documento": "12345678-9"
}
```

#### Actualizar cliente
```http
PUT /api/clientes/{id}
Authorization: Bearer {token}
Content-Type: application/json
```

#### Eliminar cliente (solo ADMIN)
```http
DELETE /api/clientes/{id}
Authorization: Bearer {token}
```

### Productos

#### Listar productos activos
```http
GET /api/productos/activos
Authorization: Bearer {token}
```

#### Buscar por nombre
```http
GET /api/productos/buscar?nombre=anillo
Authorization: Bearer {token}
```

#### Crear producto (solo ADMIN)
```http
POST /api/productos
Authorization: Bearer {token}
Content-Type: application/json

{
  "nombre": "Anillo de Oro 18k",
  "descripcion": "Anillo de oro con diamante",
  "precioCompra": 150000,
  "precioVenta": 250000,
  "activo": true
}
```

### Stock

#### Consultar stock del dueño
```http
GET /api/stock/dueno
Authorization: Bearer {token}
```

#### Consultar stock de revendedor
```http
GET /api/stock/revendedor/{idRevendedor}
Authorization: Bearer {token}
```

#### Consultar stock de un producto
```http
GET /api/stock/consultar?idProducto=1&idRevendedor=null
Authorization: Bearer {token}
```

#### Procesar movimiento de stock
```http
POST /api/stock/movimiento
Authorization: Bearer {token}
Content-Type: application/json

{
  "idProducto": 1,
  "idRevendedor": null,
  "cantidad": 10,
  "tipoMovimiento": "ENTRADA"
}
```

#### Transferir stock a revendedor
```http
POST /api/stock/transferir?idProducto=1&idRevendedor=1&cantidad=5
Authorization: Bearer {token}
```

### Pedidos

#### Crear pedido
```http
POST /api/pedidos
Authorization: Bearer {token}
Content-Type: application/json

{
  "idCliente": 1,
  "idUsuario": 1,
  "estado": "PENDIENTE",
  "kilometros": 8.5,
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

**El sistema automáticamente:**
- Calcula el subtotal
- Calcula el costo de delivery (CEIL(8.5) * 1000 = $9000)
- Calcula el total
- Calcula las ganancias
- Descuenta el stock

#### Listar pedidos
```http
GET /api/pedidos
Authorization: Bearer {token}
```

#### Pedidos por cliente
```http
GET /api/pedidos/cliente/{idCliente}
Authorization: Bearer {token}
```

#### Pedidos por estado
```http
GET /api/pedidos/estado/PENDIENTE
Authorization: Bearer {token}
```

#### Ganancia diaria (solo ADMIN)
```http
GET /api/pedidos/ganancia/diaria
Authorization: Bearer {token}
```

#### Actualizar estado del pedido
```http
PATCH /api/pedidos/{id}/estado?estado=EN_CAMINO
Authorization: Bearer {token}
```

## 🔒 Seguridad Implementada

1. **JWT con HS256**
   - Tokens firmados con clave secreta de 256 bits
   - Expiración configurable (24 horas por defecto)
   - Validación en cada request

2. **BCrypt con 12 rounds**
   - Encriptación robusta de contraseñas
   - Salt automático

3. **Spring Security**
   - Autenticación stateless
   - Filtro JWT personalizado
   - Autorización por roles

4. **Validaciones**
   - Validación de DTOs con Bean Validation
   - Manejo global de excepciones
   - Mensajes de error claros

## 🚀 Roles y Permisos

### ADMIN
- Acceso total a todos los endpoints
- Gestión de usuarios
- Gestión de productos
- Gestión de revendedores
- Control de stock
- Reportes de ganancias

### VENDEDOR
- Gestión de clientes
- Consulta de productos
- Creación de pedidos
- Consulta de stock (lectura)

## 📊 Base de Datos

El sistema usa las tablas que ya tienes creadas en PostgreSQL:
- `usuarios`
- `clientes`
- `productos`
- `stock`
- `pedidos`
- `pedido_detalle`
- `revendedores`

**Configuración JPA:**
```properties
spring.jpa.hibernate.ddl-auto=none
```
No se modifican las tablas existentes.

## 📝 Notas Importantes

1. **Primer Usuario**: Registra el primer usuario ADMIN usando `/api/auth/register`

2. **Tokens JWT**: Incluye el token en todas las peticiones protegidas:
   ```
   Authorization: Bearer {tu_token}
   ```

3. **Stock**: El sistema descuenta automáticamente el stock del dueño al crear pedidos

4. **Delivery**: 
   - 0-6 km = $6000 fijo
   - Más de 6 km = CEIL(km) × $1000

5. **Ganancias**: Se calculan automáticamente como:
   ```
   ganancia = (precioVenta - precioCompra) × cantidad
   ```

## 🧪 Testing

Para ejecutar tests:
```bash
./mvnw test
```

## 📦 Build para Producción

```bash
./mvnw clean package -DskipTests
```

El JAR se generará en `target/joyeria-back-0.0.1-SNAPSHOT.jar`

Ejecutar:
```bash
java -jar target/joyeria-back-0.0.1-SNAPSHOT.jar
```

## 🛡️ CORS

Configurado para aceptar requests desde:
- `http://localhost:3000` (React)
- `http://localhost:4200` (Angular)

Para agregar más orígenes, edita `SecurityConfig.java`

## 📞 Soporte

Para consultas o problemas, revisa:
- Logs en consola
- `application.properties` para configuración
- `GlobalExceptionHandler` para errores personalizados

---

**Desarrollado con ❤️ usando Spring Boot 3 y Java 21**

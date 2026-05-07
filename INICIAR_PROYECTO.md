# 🚀 Guía de Inicialización del Proyecto Joyería

## 📋 Requisitos Previos

- **Java 21** instalado
- **Maven** instalado (o usar mvnw incluido)
- **Node.js 18+** y **npm** (para el frontend)
- Cuenta de **Supabase** con base de datos PostgreSQL configurada

## 🗄️ Configuración de Base de Datos

### Actualizar credenciales de Supabase

Edita `joyeria-back/src/main/resources/application.yml`:

```yaml
datasource:
  url: jdbc:postgresql://db.kwnimpgoticbvkixwnsf.supabase.co:5432/postgres?sslmode=require
  username: postgres
  password: TU_CONTRASEÑA_AQUI  # ⚠️ Cambia esto por tu contraseña real
```

### Crear las tablas (Primera vez)

Si necesitas crear las tablas automáticamente, cambia temporalmente en `application.yml`:

```yaml
jpa:
  hibernate:
    ddl-auto: update  # Cambia de 'none' a 'update'
```

⚠️ **Importante:** Después de crear las tablas, vuelve a cambiar a `ddl-auto: none` para evitar modificaciones accidentales.

## 🔧 Iniciar Backend (Spring Boot)

### Opción 1: Con Maven Wrapper (Recomendado)

```bash
cd joyeria-back
./mvnw spring-boot:run
```

### Opción 2: Con Maven instalado

```bash
cd joyeria-back
mvn spring-boot:run
```

### Verificar que funcionó

Deberías ver en la terminal:
```
Started JoyeriaBackApplication in X.XXX seconds
Tomcat started on port 8080 (http) with context path '/'
```

## 📚 Swagger / OpenAPI

Una vez el backend esté corriendo, accede a:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

Aquí podrás:
- Ver todos los endpoints disponibles
- Probar las APIs directamente desde el navegador
- Ver la documentación de cada endpoint

## 🎨 Iniciar Frontend (React + Vite)

### Primera vez: Instalar dependencias

```bash
cd joyeria-front
npm install
```

### Iniciar servidor de desarrollo

```bash
npm run dev
```

El frontend estará disponible en: http://localhost:5173

## 🔑 Endpoints Principales

### Autenticación
- `POST /api/auth/login` - Iniciar sesión
- `POST /api/auth/register` - Registrar usuario

### Productos
- `GET /api/productos` - Listar productos
- `POST /api/productos` - Crear producto
- `PUT /api/productos/{id}` - Actualizar producto
- `DELETE /api/productos/{id}` - Eliminar producto

### Clientes
- `GET /api/clientes` - Listar clientes
- `POST /api/clientes` - Crear cliente

### Pedidos
- `GET /api/pedidos` - Listar pedidos
- `POST /api/pedidos` - Crear pedido

### Dashboard
- `GET /api/dashboard` - Estadísticas generales

## 🛑 Detener los servidores

### Backend
Presiona `Ctrl+C` en la terminal donde está corriendo

### Frontend
Presiona `Ctrl+C` en la terminal donde está corriendo

## ⚠️ Solución de Problemas Comunes

### Error de conexión a Supabase
- Verifica que la contraseña en `application.yml` sea correcta
- Asegúrate de que tu IP esté permitida en Supabase (o usa "Allow all" temporalmente)

### Puerto 8080 ya en uso
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Linux/Mac
lsof -i :8080
kill -9 <PID>
```

### Errores de compilación Maven
```bash
cd joyeria-back
./mvnw clean install
```

## 📝 Estructura del Proyecto

```
joyeria-proyect/
├── joyeria-back/          # Backend Spring Boot
│   ├── src/main/java/     # Código fuente Java
│   ├── src/main/resources/
│   │   └── application.yml # Configuración
│   └── pom.xml            # Dependencias Maven
│
└── joyeria-front/         # Frontend React
    ├── src/               # Código fuente React
    ├── package.json       # Dependencias npm
    └── vite.config.ts     # Configuración Vite
```

## 🎯 Próximos Pasos

1. ✅ Configurar base de datos en Supabase
2. ✅ Actualizar credenciales en application.yml
3. ✅ Iniciar backend y verificar Swagger
4. ⬜ Crear tablas si es necesario
5. ⬜ Iniciar frontend
6. ⬜ Probar login/registro
7. ⬜ Comenzar a usar la aplicación

---

**¿Necesitas ayuda?** Revisa los logs en la terminal para más detalles sobre errores.

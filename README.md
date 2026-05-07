# 💎 Sistema de Gestión de Joyería

Sistema completo de gestión para joyerías con funcionalidades de ventas, inventario, clientes y revendedores.

## 🚀 Inicio Rápido

### Windows
```bash
# Doble clic en:
iniciar.bat
```

### Manual
```bash
# Terminal 1 - Backend
cd joyeria-back
./mvnw spring-boot:run

# Terminal 2 - Frontend
cd joyeria-front
npm install  # Solo la primera vez
npm run dev
```

📖 **Guía completa**: Ver [INICIAR_PROYECTO.md](INICIAR_PROYECTO.md)

## 🌐 URLs

| Servicio | URL | Descripción |
|----------|-----|-------------|
| **Frontend** | http://localhost:5173 | Aplicación React |
| **Backend** | http://localhost:8080 | API REST |
| **Swagger** | http://localhost:8080/swagger-ui.html | Documentación API |
| **OpenAPI** | http://localhost:8080/v3/api-docs | Especificación OpenAPI |

## 🛠️ Tecnologías

### Backend
- **Spring Boot 3.3.0** - Framework Java
- **Java 21** - Lenguaje de programación
- **PostgreSQL** (Supabase) - Base de datos
- **Spring Security + JWT** - Autenticación
- **MapStruct** - Mapeo de objetos
- **SpringDoc OpenAPI** - Documentación Swagger
- **Maven** - Gestión de dependencias

### Frontend
- **React 18** - Biblioteca UI
- **TypeScript** - Tipado estático
- **Vite** - Build tool
- **React Router** - Navegación
- **Tailwind CSS** - Estilos
- **Axios** - Cliente HTTP

## 📁 Estructura del Proyecto

```
joyeria-proyect/
├── joyeria-back/              # Backend Spring Boot
│   ├── src/main/java/com/joyeriajoy/joyeria_back/
│   │   ├── config/           # Configuraciones
│   │   ├── controller/       # Controladores REST
│   │   ├── dto/              # Data Transfer Objects
│   │   ├── model/            # Entidades JPA
│   │   ├── repository/       # Repositorios
│   │   ├── service/          # Lógica de negocio
│   │   ├── security/         # Seguridad JWT
│   │   └── mapper/           # MapStruct mappers
│   └── src/main/resources/
│       └── application.yml   # Configuración
│
├── joyeria-front/            # Frontend React
│   ├── src/
│   │   ├── app/             # Configuración de la app
│   │   ├── features/        # Módulos por funcionalidad
│   │   │   ├── auth/       # Autenticación
│   │   │   ├── dashboard/  # Dashboard
│   │   │   ├── productos/  # Gestión de productos
│   │   │   ├── clientes/   # Gestión de clientes
│   │   │   ├── pedidos/    # Gestión de pedidos
│   │   │   ├── revendedores/ # Gestión de revendedores
│   │   │   ├── stock/      # Control de inventario
│   │   │   └── reportes/   # Reportes y estadísticas
│   │   └── shared/         # Componentes compartidos
│   │       ├── components/ # UI components
│   │       ├── services/   # Servicios API
│   │       ├── types/      # Tipos TypeScript
│   │       └── layouts/    # Layouts
│   └── public/             # Archivos estáticos
│
├── INICIAR_PROYECTO.md      # Guía detallada
├── iniciar.bat              # Script de inicio (Windows)
└── README.md                # Este archivo
```

## 📋 Funcionalidades

### ✅ Implementadas
- 🔐 Autenticación con JWT
- 👥 Gestión de usuarios y roles
- 📦 Gestión de productos
- 👤 Gestión de clientes
- 🤝 Gestión de revendedores
- 📊 Dashboard con estadísticas
- 🛒 Sistema de pedidos
- 📦 Control de stock
- 🔒 Spring Security configurado
- 📚 Documentación Swagger

### 🚧 En desarrollo
- 📱 Aplicación móvil
- 📊 Reportes avanzados
- 💳 Integración de pagos
- 📧 Notificaciones por email
- 📱 Notificaciones push

## 🔐 Seguridad

- Autenticación basada en JWT
- Endpoints protegidos con Spring Security
- Roles de usuario: ADMIN, USER
- Contraseñas hasheadas con BCrypt
- CORS configurado para desarrollo

## 🗄️ Base de Datos (Supabase PostgreSQL)

### Configuración
```yaml
url: jdbc:postgresql://db.kwnimpgoticbvkixwnsf.supabase.co:5432/postgres?sslmode=require
username: postgres
password: TU_CONTRASEÑA
```

### Tablas principales
- `usuarios` - Usuarios del sistema
- `clientes` - Clientes finales
- `revendedores` - Revendedores/Distribuidores
- `productos` - Catálogo de productos
- `pedidos` - Órdenes de compra
- `pedido_detalle` - Líneas de pedidos
- `stock` - Inventario por producto

## 📝 Variables de Entorno

### Backend (application.yml)
```yaml
spring.datasource.url: URL de PostgreSQL
spring.datasource.username: Usuario DB
spring.datasource.password: Contraseña DB
jwt.secret: Clave secreta JWT
jwt.expiration: Tiempo de expiración (ms)
server.port: Puerto del servidor (8080)
```

### Frontend (.env)
```env
VITE_API_URL=http://localhost:8080/api
```

## 🧪 Testing

### Backend
```bash
cd joyeria-back
./mvnw test
```

### Frontend
```bash
cd joyeria-front
npm test
```

## 📦 Compilar para Producción

### Backend (JAR)
```bash
cd joyeria-back
./mvnw clean package
# JAR generado en: target/joyeria-back-0.0.1-SNAPSHOT.jar
```

### Frontend (Build)
```bash
cd joyeria-front
npm run build
# Build generado en: dist/
```

## 🤝 Contribuir

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto es privado y propiedad de Joyería Joy.

## 📞 Soporte

Para problemas o preguntas:
- Revisar [INICIAR_PROYECTO.md](INICIAR_PROYECTO.md)
- Consultar la documentación Swagger
- Contactar al equipo de desarrollo

---

**Desarrollado con ❤️ para Joyería Joy**
# Joyeria

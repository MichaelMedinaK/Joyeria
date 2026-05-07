# Conexión con Supabase

## 📋 Pasos para Conectar tu Proyecto Spring Boot con Supabase

### 1. Obtener la Contraseña de la Base de Datos

1. Ve a tu **Dashboard de Supabase**: https://supabase.com/dashboard
2. Selecciona tu proyecto
3. Navega a: `Settings` → `Database`
4. En la sección **"Connection String"**, haz clic en **"Reset database password"** si no recuerdas tu contraseña
5. **Copia la nueva contraseña** que Supabase te muestre (¡guárdala en un lugar seguro!)

### 2. Configurar Variables de Entorno

#### **Opción A: Usando IntelliJ IDEA / Eclipse**

1. Ve a **Run** → **Edit Configurations**
2. Selecciona tu aplicación Spring Boot
3. En **Environment Variables**, agrega:
   ```
   DB_URL=jdbc:postgresql://db.kwnimpgoticbvkixwnsf.supabase.co:5432/postgres?sslmode=require;DB_USERNAME=postgres;DB_PASSWORD=tu-contraseña-aqui
   ```

#### **Opción B: Usando archivo .env (con plugin)**

1. Copia el archivo `.env.example` a `.env`:
   ```bash
   cp .env.example .env
   ```

2. Edita el archivo `.env` y reemplaza `tu-password-real-aqui` con tu contraseña real de Supabase

3. Instala el plugin **EnvFile** en tu IDE (IntelliJ) o usa **Spring Boot DevTools**

#### **Opción C: Variables del Sistema (Windows)**

```bash
# En PowerShell (para la sesión actual)
$env:DB_URL="jdbc:postgresql://db.kwnimpgoticbvkixwnsf.supabase.co:5432/postgres?sslmode=require"
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD="tu-contraseña-aqui"

# Luego ejecuta la aplicación
.\mvnw spring-boot:run
```

```bash
# En CMD (para la sesión actual)
set DB_URL=jdbc:postgresql://db.kwnimpgoticbvkixwnsf.supabase.co:5432/postgres?sslmode=require
set DB_USERNAME=postgres
set DB_PASSWORD=tu-contraseña-aqui

mvnw spring-boot:run
```

### 3. Verificar la Conexión

Ejecuta tu aplicación Spring Boot:

```bash
.\mvnw spring-boot:run
```

Si la conexión es exitosa, verás en los logs:
```
HikariPool-1 - Start completed.
```

Si hay un error de conexión, revisa:
- ✅ La contraseña es correcta
- ✅ La URL incluye `?sslmode=require`
- ✅ Las variables de entorno están configuradas
- ✅ Tu IP está permitida en Supabase (normalmente está abierto por defecto)

### 4. Configuración de Firewall en Supabase (Opcional)

Si tienes problemas de conexión:

1. Ve a `Settings` → `Database` en Supabase
2. Baja a **"Restrict database access"**
3. Asegúrate de que **"Restrict database access to specified IP addresses"** esté desactivado, o agrega tu IP

### 5. Seguridad en Producción

Para desplegar en producción:

1. **NUNCA** subas el archivo `.env` a Git (ya está en `.gitignore`)
2. Configura las variables de entorno en tu plataforma de deployment:
   - **Heroku**: `heroku config:set DB_PASSWORD=tu-password`
   - **AWS Elastic Beanstalk**: Usa Environment Properties
   - **Docker**: Usa `docker-compose.yml` con variables
   - **Azure**: Usa Application Settings

### 6. Supabase Agent Skills (Opcional)

Si quieres mejorar la integración con herramientas AI:

```bash
cd joyeria-back
npx skills add supabase/agent-skills
```

## 🔍 Solución de Problemas

### Error: "PSQLException: FATAL: password authentication failed"
- ✅ Verifica que la contraseña sea correcta
- ✅ Resetea la contraseña en Supabase Dashboard

### Error: "Connection refused"
- ✅ Verifica que la URL sea correcta
- ✅ Asegúrate de incluir `?sslmode=require`

### Error: "No suitable driver found"
- ✅ Verifica que el driver PostgreSQL esté en `pom.xml` (ya está incluido)

## 📚 Recursos

- [Documentación Supabase Database](https://supabase.com/docs/guides/database)
- [Spring Boot Database Configuration](https://docs.spring.io/spring-boot/reference/data/sql.html)

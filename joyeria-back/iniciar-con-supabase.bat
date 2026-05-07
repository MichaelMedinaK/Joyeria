@echo off
REM =====================================================
REM Script para Configurar Variables de Entorno y Ejecutar
REM =====================================================

echo =====================================================
echo    CONFIGURACION DE SUPABASE PARA JOYERIA-BACK
echo =====================================================
echo.

REM Verificar si existe .env
if not exist .env (
    echo [INFO] No se encontro el archivo .env
    if exist .env.example (
        echo [INFO] Copiando .env.example a .env...
        copy .env.example .env
        echo.
        echo [ACCION REQUERIDA] Por favor edita el archivo .env con tu password real
        echo                    y vuelve a ejecutar este script.
        echo.
        pause
        exit /b 1
    ) else (
        echo [ERROR] No existe .env.example
        exit /b 1
    )
)

REM Leer variables del archivo .env
echo [INFO] Leyendo configuracion desde .env...
for /f "tokens=1,2 delims==" %%a in (.env) do (
    set %%a=%%b
)

REM Configurar variables de entorno
echo [INFO] Configurando variables de entorno...
set DB_URL=%DB_URL%
set DB_USERNAME=%DB_USERNAME%
set DB_PASSWORD=%DB_PASSWORD%

echo.
echo [OK] Variables configuradas correctamente
echo.
echo =====================================================
echo    INICIANDO SPRING BOOT APPLICATION
echo =====================================================
echo.

REM Ejecutar Maven
mvnw.cmd spring-boot:run

pause

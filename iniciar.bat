@echo off
echo ========================================
echo   INICIANDO PROYECTO JOYERIA
echo ========================================
echo.

echo [1/2] Iniciando Backend (Spring Boot)...
cd joyeria-back
start "Backend - Joyeria" cmd /k "./mvnw spring-boot:run"
cd ..

timeout /t 10 /nobreak >nul

echo.
echo [2/2] Iniciando Frontend (React)...
cd joyeria-front
start "Frontend - Joyeria" cmd /k "npm run dev"
cd ..

echo.
echo ========================================
echo   PROYECTO INICIADO
echo ========================================
echo.
echo Backend:  http://localhost:8080
echo Swagger:  http://localhost:8080/swagger-ui.html
echo Frontend: http://localhost:5173
echo.
echo Presiona cualquier tecla para cerrar esta ventana...
pause >nul

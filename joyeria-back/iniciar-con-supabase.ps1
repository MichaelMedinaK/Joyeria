# =====================================================
# Script PowerShell para Configurar y Ejecutar con Supabase
# =====================================================

Write-Host "=====================================================" -ForegroundColor Cyan
Write-Host "   CONFIGURACION DE SUPABASE PARA JOYERIA-BACK" -ForegroundColor Cyan
Write-Host "=====================================================" -ForegroundColor Cyan
Write-Host ""

# Verificar si existe .env
if (-not (Test-Path ".env")) {
    Write-Host "[INFO] No se encontro el archivo .env" -ForegroundColor Yellow
    
    if (Test-Path ".env.example") {
        Write-Host "[INFO] Copiando .env.example a .env..." -ForegroundColor Yellow
        Copy-Item ".env.example" ".env"
        Write-Host ""
        Write-Host "[ACCION REQUERIDA] Por favor:" -ForegroundColor Red
        Write-Host "  1. Abre el archivo .env" -ForegroundColor White
        Write-Host "  2. Reemplaza 'tu-password-real-aqui' con tu password de Supabase" -ForegroundColor White
        Write-Host "  3. Vuelve a ejecutar este script" -ForegroundColor White
        Write-Host ""
        
        # Abrir el archivo .env en el editor predeterminado
        Start-Process ".env"
        
        Read-Host "Presiona Enter para salir"
        exit 1
    }
    else {
        Write-Host "[ERROR] No existe .env.example" -ForegroundColor Red
        exit 1
    }
}

# Leer variables del archivo .env
Write-Host "[INFO] Leyendo configuracion desde .env..." -ForegroundColor Green

Get-Content ".env" | ForEach-Object {
    if ($_ -match '^([^#][^=]+)=(.*)$') {
        $key = $matches[1].Trim()
        $value = $matches[2].Trim()
        
        # Configurar variable de entorno para la sesión actual
        [Environment]::SetEnvironmentVariable($key, $value, "Process")
        
        # Mostrar (ocultar contraseña)
        if ($key -eq "DB_PASSWORD") {
            Write-Host "  $key=****" -ForegroundColor Gray
        }
        else {
            Write-Host "  $key=$value" -ForegroundColor Gray
        }
    }
}

Write-Host ""
Write-Host "[OK] Variables configuradas correctamente" -ForegroundColor Green
Write-Host ""
Write-Host "=====================================================" -ForegroundColor Cyan
Write-Host "   INICIANDO SPRING BOOT APPLICATION" -ForegroundColor Cyan
Write-Host "=====================================================" -ForegroundColor Cyan
Write-Host ""

# Ejecutar Maven
& .\mvnw.cmd spring-boot:run

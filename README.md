## Configuración del proyecto

Este proyecto requiere un archivo de variables de entorno para funcionar correctamente.  
Crea un archivo `.env` en la raíz del proyecto con los siguientes parámetros:

```env
# Configuración de la aplicación
APP_NAME=app-name       # Nombre de la aplicación
SERVER_PORT=app-port    # Puerto en el que correrá el servidor
LOG_PATH=path-to-logs   # Ruta donde se guardarán los logs
LOG_LEVEL=level-logs    # Nivel de logs (e.g., debug, info, warn, error)

# Configuración de la base de datos
DB_NAME=database-name   # Nombre de la base de datos
DB_USER=user-db-name    # Usuario de la base de datos
DB_PASSWORD=password-db # Contraseña de la base de datos
DB_HOST=db-host         # Host de la base de datos
DB_PORT=db-port         # Puerto de la base de datos

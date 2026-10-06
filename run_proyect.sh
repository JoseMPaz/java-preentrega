#!/bin/bash

# Detener el script inmediatamente si ocurre algun error
set -e

# Verificamos si pasaste el argumento por la terminal
if [ -z "$1" ]; then
    echo "Error: No especificaste el proyecto a ejecutar."
    echo "Uso correcto: $0 <nombre_del_proyecto>"
    echo "Ejemplo: $0 preentrega"
    exit 1
fi

# Asignamos el argumento a una variable clara
EJERCICIO="$1"

echo "=== Ejecutando la aplicacion con Maven ($EJERCICIO) ==="
mvn package exec:java -Dexec.mainClass="com.josepaz.${EJERCICIO}.Main"


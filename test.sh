#!/bin/bash

# Detener la ejecucion inmediatamente si ocurre algun error critico en el proceso
set -e

echo "============================================="
echo " Ejecutando Compilacion del Proyecto - TechLab"
echo "============================================="

# Limpia los binarios en cache y compila las clases principales del negocio
mvn clean compile

echo "============================================="
echo " Ejecutando las Pruebas Unitarias con JUnit 5"
echo "============================================="

# Dispara la ejecucion de la suite de pruebas unitarias
mvn test


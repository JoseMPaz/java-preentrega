#!/bin/bash

# Detener el script inmediatamente si ocurre algun error
set -e

# =================================================================
# VALIDACION DE ARGUMENTOS DE LINEA DE COMANDO
# =================================================================
# Si el primer argumento ($1) esta vacio, muestra un error explicativo y sale.
if [ -z "$1" ]; then
    echo "Error: No especificaste el nombre del proyecto."
    echo "Uso correcto: $0 <nombre_del_proyecto>"
    echo "Ejemplo: $0 preentrega"
    exit 1
fi

# Asignamos el primer argumento de la terminal a nuestra variable
NUM_EJERCICIO="$1"

# Definicion de rutas estandar de Maven usando la variable
PACKAGE_DIR="src/main/java/com/josepaz/$NUM_EJERCICIO"
TEST_DIR="src/test/java/com/josepaz/$NUM_EJERCICIO"
RESOURCES_DIR="src/main/resources"

echo "=== 1. Generando estructura de carpetas estandar de Maven y paquetes internos para: $NUM_EJERCICIO ==="
# Creamos la ruta base del paquete y del entorno de pruebas
mkdir -p "$PACKAGE_DIR"
mkdir -p "$TEST_DIR"
mkdir -p "$RESOURCES_DIR"

# Creamos las subcarpetas de arquitectura dentro de la carpeta del ejercicio
mkdir -p "$PACKAGE_DIR/exception"
mkdir -p "$PACKAGE_DIR/model"
mkdir -p "$PACKAGE_DIR/service"
mkdir -p "$PACKAGE_DIR/util"
mkdir -p "$PACKAGE_DIR/ui"

echo "=== 2. Verificando archivo Java principal ==="
# Solo crea el Main.java de ejemplo inicial si el archivo NO existe.
if [ ! -f "$PACKAGE_DIR/Main.java" ]; then
    echo "Creando archivo Java de ejemplo (Main.java)..."
    cat << EOF > "$PACKAGE_DIR/Main.java"
package com.josepaz.$NUM_EJERCICIO;

public class Main 
{
    public static void main(String[] args) 
    {
        System.out.println("Hola Mundo desde Main.java en Maven ($NUM_EJERCICIO)");
    }
}
EOF
else
    echo "El archivo Main.java ya existe. Respetando tu codigo actual."
fi

echo "=== 3. Generando archivo pom.xml con soporte JUnit 5 y Surefire ==="
# Se elimino la barra invertida de la etiqueta mainClass para que Bash inyecte el valor real
cat << EOF > pom.xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://apache.org"
         xmlns:xsi="http://w3.org"
         xsi:schemaLocation="http://apache.org http://apache.org">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.josepaz</groupId>
    <artifactId>hola-mundo-terminal</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <!-- Dependencia para habilitar las pruebas unitarias de JUnit 5 -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.10.2</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <!-- Plugin para compilar con tu version Java 21 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.13.0</version>
                <configuration>
                    <source>21</source>
                    <target>21</target>
                </configuration>
            </plugin>
            
            <!-- Plugin Surefire moderno para mapear y listar la ejecucion de JUnit 5 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.2.5</version>
            </plugin>
            
            <!-- Plugin Exec apuntando a tu clase dinamica com.josepaz -->
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>exec-maven-plugin</artifactId>
                <version>3.5.0</version>
                <configuration>
                    <mainClass>com.josepaz.${NUM_EJERCICIO}.Main</mainClass>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
EOF

echo "=== 4. Limpiando y compilando el proyecto ==="
mvn clean compile

echo "=== 5. Ejecutando la clase Main de $NUM_EJERCICIO ==="
mvn exec:java

echo "=== 6. Ejecutando las pruebas unitarias con JUnit 5 ==="
mvn test


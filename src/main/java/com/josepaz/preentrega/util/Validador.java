package util;

import java.util.InputMismatchException;
import java.util.Scanner;

import exception.StockInsuficienteException;

/**
 * Clase de utilidad que contiene metodos de validacion y lectura reutilizables.
 * 
 * Todos los metodos son estaticos: no se requiere crear una instancia de 
 * Validador para utilizarlos, sino que se invocan directamente desde la clase.
 */
public class Validador 
{

    // =====================================================================
    // VALIDACIONES DE DATOS DEL PRODUCTO
    // =====================================================================
    // Estos metodos lanzan una excepcion si el dato recibido es invalido.
    // No retornan ningun valor: si la ejecucion finaliza sin lanzar la 
    // excepcion, significa que el dato es valido.

    public static void validarNombre(String nombre) 
    {
        // Un nombre nulo o vacio no representa un producto valido
        if (nombre == null || nombre.trim().isEmpty()) 
        {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }
    }

    public static void validarPrecio(double precio) 
    {
        // El precio no debe ser negativo, pero se acepta un valor de 0
        if (precio < 0) 
        {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
    }

    public static void validarStock(int stock) {
        // Un stock negativo no es valido. Se utiliza una excepcion personalizada.
        if (stock < 0) 
        {
            throw new StockInsuficienteException("El stock no puede ser negativo.");
        }
    }

    public static void validarCategoria(String categoria) 
    {
        if (categoria == null || categoria.isBlank()) 
        {
            throw new IllegalArgumentException("La categoria no puede estar vacia.");
        }
    }

    // =====================================================================
    // METODOS DE LECTURA POR CONSOLA
    // =====================================================================

    public static int leerEntero(Scanner sc, String mensaje) 
    {
        // Bucle que se repite hasta que el usuario ingresa un entero valido.
        while (true) 
        {
            System.out.println(mensaje);
            try 
            {
                int valor = sc.nextInt();
                sc.nextLine(); // Limpia el salto de linea pendiente en el buffer
                return valor;
            } 
            catch (InputMismatchException e) 
            {
                System.out.println("Debe ingresar un numero entero. Intente nuevamente.");
                sc.nextLine(); // Limpia el buffer ante un ingreso incorrecto
            }
        }
    }

    public static double leerDouble(Scanner sc, String mensaje) 
    {
        // Bucle que se repite hasta que el usuario ingresa un decimal valido.
        while (true) 
        {
            System.out.println(mensaje);
            try 
            {
                double valor = sc.nextDouble();
                sc.nextLine(); // Limpia el salto de linea pendiente en el buffer
                return valor;
            } 
            catch (Exception e) 
            {
                System.out.println("Debe ingresar un numero decimal usando el separador correcto.");
                sc.nextLine(); // Limpia el buffer ante un ingreso incorrecto
            }
        }
    }

    public static String leerTexto(Scanner sc, String mensaje) 
    {
        // Lectura simple de una linea de texto completa
        System.out.println(mensaje);
        return sc.nextLine();
    }
}


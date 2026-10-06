package com.josepaz.preentrega;

import java.util.Scanner;

import exception.ProductoNoEncontradoException;
import exception.StockInsuficienteException;
import model.Producto;
import service.ProductoService;
import ui.MenuProducto;
import util.Validador;

public class Main 
{
    public static void main(String[] args) 
    {
        // Creamos las dependencias una sola vez y las compartimos
        // durante toda la ejecución del programa.
        ProductoService service = new ProductoService();
        Scanner sc = new Scanner(System.in);
        MenuProducto menu = new MenuProducto(sc, service);
        cargarDatosDePrueba(service);
        
        int opcion;

        // do-while garantiza que el menú se muestre al menos una
        // vez. Se repite hasta que el usuario elige la opción 6.
        do 
        {
            menu.mostrarMenu();
            opcion = Validador.leerEntero(sc, "Elija una opción: ");

            // Cada opción del menú está envuelta en try/catch.
            // Si una operación lanza una excepción (producto
            // inexistente, datos inválidos, etc.), el programa NO
            // se cae: muestra el mensaje y vuelve a mostrar el menú.
            try 
            {
                switch (opcion) 
                {
                    case 1 -> menu.agregarProducto();
                    case 2 -> menu.listarProductos();
                    case 3 -> menu.buscarProducto();
                    case 4 -> menu.actualizarProducto();
                    case 5 -> menu.eliminarProducto();
                    case 6 -> System.out.println("¡Hasta luego!");
                    default -> System.out.println("Opción inválida. Elija un número del 1 al 6.");
                }
            } 
            catch (ProductoNoEncontradoException | StockInsuficienteException e) 
            {
                // capturamos las excepciones personalizadas
                System.out.println(e.getMessage());
            } 
            catch (IllegalArgumentException e) 
            {
                // Validador de datos genericos invalidos
                // (nombre,precio negativo,etc...)
                System.out.println(e.getMessage());
            }

        } while (opcion != 6);

        sc.close();
        
        
        
    }
    
    private static void cargarDatosDePrueba(ProductoService service) 
    {
        service.guardar(new Producto("Café molido 500g", 4500, 30, "Bebidas"));
        service.guardar(new Producto("Yerba mate 1kg", 3200, 50, "Bebidas"));
        service.guardar(new Producto("Galletitas dulces", 1850, 100, "Almacén"));
        service.guardar(new Producto("Aceite de oliva 500ml", 6700, 20, "Almacén"));
        service.guardar(new Producto("Chocolate amargo 70%", 2900, 15, "Golosinas"));
        System.out.println("✔ Se cargaron 5 productos de prueba.\n");
    }
}

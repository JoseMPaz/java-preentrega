package com.josepaz.preentrega;

import java.util.Scanner;

import exception.ProductoNoEncontradoException;
import exception.StockInsuficienteException;
import model.Producto;
import service.ProductoService;
import ui.MenuProducto;
import util.Validador;

/**
 * Clase principal que actua como el Orquestador o Controlador General de la aplicacion.
 * Inicia el programa, configura los datos de prueba y maneja el bucle principal de ejecucion.
 */
public class Main 
{
    public static void main(String[] args) 
    {
        // ============================================
        // 1. CONFIGURACION E INYECCION DE DEPENDENCIAS
        // ============================================
        // Creamos las dependencias clave una sola vez en el punto de entrada de la app
        // y las compartimos (inyectamos) para evitar multiples instancias innecesarias.
        ProductoService service = new ProductoService();
        Scanner sc = new Scanner(System.in);
        
        // Pasamos el Scanner y el Servicio al menu para que trabaje sobre las mismas instancias
        MenuProducto menu = new MenuProducto(sc, service);
        
        // Llenamos el inventario inicial con productos precargados para facilitar las pruebas
        cargarDatosDePrueba(service);
        
        // Variable local para almacenar y evaluar la decision del usuario en cada iteracion
        int opcion;

        // ============================================
        // 2. BUCLE PRINCIPAL DE LA APLICACION (CONTROL DE FLUJO)
        // ============================================
        // El bloque do-while garantiza que el menu se muestre de entrada al menos una vez.
        // El programa seguira corriendo de forma interactiva hasta que el usuario digite la opcion 6.
        do 
        {
            // Pinta las opciones disponibles en la terminal
            menu.mostrarMenu();
            
            // Lee la entrada numerica de forma segura delegando la validacion a la clase utilitaria
            opcion = Validador.leerEntero(sc, "Elija una opcion: ");

            // ============================================
            // 3. CAPA DE SEGURIDAD Y MANEJO DE ERRORES (ROBUSTEZ)
            // ============================================
            // Todo el Switch-Expression se envuelve en un bloque try/catch global.
            // Si cualquier operacion del menu o del servicio falla y lanza una excepcion,
            // el flujo es capturado de inmediato. Esto evita que el programa "explote" (crash)
            // y en su lugar muestra un mensaje elegante antes de reiniciar el bucle.
            try 
            {
                switch (opcion) 
                {
                    case 1 -> menu.agregarProducto();
                    case 2 -> menu.listarProductos();
                    case 3 -> menu.buscarProducto();
                    case 4 -> menu.actualizarProducto();
                    case 5 -> menu.eliminarProducto();
                    case 6 -> System.out.println("Hasta luego!");
                    default -> System.out.println("Opcion invalida. Elija un numero del 1 al 6.");
                }
            } 
            catch (ProductoNoEncontradoException | StockInsuficienteException e) 
            {
                // Captura errores especificos del dominio del negocio (ej. ID inexistente o falta de stock)
                System.out.println("\n[ERROR DE NEGOCIO]: " + e.getMessage());
            } 
            catch (IllegalArgumentException e) 
            {
                // Captura validaciones de datos genericos incorrectos (ej. campos vacios, valores negativos)
                System.out.println("\n[ERROR DE VALIDACION]: " + e.getMessage());
            }
            
            // ============================================
            // 4. CONTROL VISUAL Y REFRESCO DE PANTALLA
            // ============================================
            // Detiene temporalmente la ejecucion para que los mensajes de exito o error 
            // no desaparezcan de inmediato. Se ejecuta siempre y cuando no se elija salir.
        	if (opcion != 6) 
        	{
            	presionarEnterParaContinuar(sc);
        	}

        } while (opcion != 6); // Evalua la condicion de salida para ver si repite el ciclo

        // Cierre preventivo del recurso Scanner al finalizar la ejecucion global del software
        sc.close();
    }
    
    /**
     * Metodo de Soporte: Alimenta la capa de servicios con productos quemados en codigo (mock data).
     * @param service La instancia unica del servicio de productos donde se guardara la coleccion inicial.
     */
    private static void cargarDatosDePrueba(ProductoService service) 
    {
        service.guardar(new Producto("Cafe molido 500g", 4500, 30, "Bebidas"));
        service.guardar(new Producto("Yerba mate 1kg", 3200, 50, "Bebidas"));
        service.guardar(new Producto("Galletitas dulces", 1850, 100, "Almacen"));
        service.guardar(new Producto("Aceite de oliva 500ml", 6700, 20, "Almacen"));
        service.guardar(new Producto("Chocolate amargo 70%", 2900, 15, "Golosinas"));
        System.out.println("Se cargaron 5 productos de prueba.\n");
    }
    
    /**
     * Metodo de Soporte: Crea una pausa de consola obligando al usuario a presionar la tecla Enter.
     * Esto evita que el menu secundario se encime velozmente sobre los resultados de las operaciones CRUD.
     * @param sc La instancia compartida del Scanner de entrada.
     */
    private static void presionarEnterParaContinuar(Scanner sc) 
    {
    	System.out.println("\nPresione Enter para continuar...");
    	sc.nextLine(); // Bloquea la consola esperando unicamente el salto de linea del Enter
	}
}


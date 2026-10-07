package util;

import java.util.InputMismatchException;
import java.util.Scanner;

import exception.StockInsuficienteException;

/**
 * Clase Utilitaria (Utility Class): Provee metodos estaticos centralizados para validacion y lectura de datos.
 * 
 * Sigue el patron Helper/Utility: Al declarar todos sus metodos como 'static', no requiere ser instanciada 
 * mediante el operador 'new'. Actua como una libreria interna que puede ser invocada directamente 
 * por cualquier capa de la aplicacion (UI o Service) bajo demanda.
 */
public class Validador 
{
    // Nota de diseno: Idealmente, las clases utilitarias suelen llevar un constructor privado vacio 
    // para evitar explicitamente que alguien intente hacer: Validador v = new Validador();

    // ============================================
    // VALIDACIONES DE REGLAS DE NEGOCIO (Data Validation)
    // ============================================
    // Estos metodos aplican la tecnica de "Fail-Fast" (Fallar rapido). Si el parametro viola 
    // una restriccion, se interrumpe el hilo de inmediato lanzando una excepcion. 
    // No retornan nada (void): el silencio del metodo es sinonimo de que los datos son totalmente validos.

    /**
     * Valida que una cadena de texto contenga caracteres legibles y reales.
     * @param nombre Cadena de texto a evaluar.
     * @throws IllegalArgumentException Excepcion nativa si el texto es nulo o esta compuesto solo por espacios.
     */
    public static void validarNombre(String nombre) 
    {
        // trim() remueve espacios fantasma en los extremos para evitar trampas como "   "
        if (nombre == null || nombre.trim().isEmpty()) 
        {
            throw new IllegalArgumentException("El nombre no puede estar vacio.");
        }
    }

    /**
     * Valida que los valores monetarios sean financieramente logicos.
     * @param precio Valor decimal a evaluar.
     * @throws IllegalArgumentException Si el precio es menor a cero (los productos gratis con valor 0 son validos).
     */
    public static void validarPrecio(double precio) 
    {
        if (precio < 0) 
        {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
    }

    /**
     * Valida la existencia fisica del stock del inventario.
     * @param stock Cantidad de unidades a evaluar.
     * @throws StockInsuficienteException Excepcion personalizada utilizada para reflejar un error semantico del negocio.
     */
    public static void validarStock(int stock) {
        if (stock < 0) 
        {
            throw new StockInsuficienteException("El stock no puede ser negativo.");
        }
    }

    /**
     * Valida la categorizacion del catalogo.
     * @param categoria Cadena de texto de la categoria.
     * @throws IllegalArgumentException Si la categoria esta ausente o vacia.
     */
    public static void validarCategoria(String categoria) 
    {
        // isBlank() evalua de forma optima si la cadena tiene longitud cero o puros espacios en blanco
        if (categoria == null || categoria.isBlank()) 
        {
            throw new IllegalArgumentException("La categoria no puede estar vacia.");
        }
    }

    // ============================================
    // METODOS DE CAPTURA ROBUSTA POR CONSOLA (Console Input Processing)
    // ============================================
    // Resuelven el problema clasico del Scanner en Java: el desbordamiento o bloqueo del buffer 
    // cuando el usuario introduce caracteres no numericos o presiona saltos de linea imprevistos.

    /**
     * Captura de forma blindada un valor entero, atrapando errores tipograficos del usuario.
     * 
     * @param sc Instancia unica y compartida del Scanner.
     * @param mensaje Indicacion textual (Prompt) que guia al usuario sobre que ingresar.
     * @return El numero entero ya validado sintacticamente.
     */
    public static int leerEntero(Scanner sc, String mensaje) 
    {
        // Bucle infinito controlado: No se rompera hasta que el bloque 'try' alcance con exito el 'return'
        while (true) 
        {
            System.out.println(mensaje);
            try 
            {
                int valor = sc.nextInt();
                
                // LIMPIEZA CLAVE DEL BUFFER: nextInt() consume el numero pero deja el caracter invisible
                // del salto de linea ('\n') en la consola. sc.nextLine() consume ese residuo para evitar 
                // que el siguiente metodo de lectura se salte automaticamente.
                sc.nextLine(); 
                
                return valor; // Rompe el bucle e inmuniza la salida
            } 
            catch (InputMismatchException e) 
            {
                System.out.println("Debe ingresar un numero entero. Intente nuevamente.");
                
                // RECOLECCION DE BASURA DEL BUFFER: Si el usuario escribe una letra (ej. "hola"), 
                // nextInt() falla y deja la palabra "hola" atrapada en el buffer. Si no llamamos a nextLine() 
                // aqui, el bucle intentara leer "hola" infinitamente provocando un ciclo sin fin.
                sc.nextLine(); 
            }
        }
    }

    /**
     * Captura de forma blindada un valor decimal, previniendo fallas de localizacion por comas o puntos.
     * 
     * @param sc Instancia compartida del Scanner.
     * @param mensaje Indicacion en pantalla.
     * @return El numero de punto flotante de doble precision (double) validado.
     */
    public static double leerDouble(Scanner sc, String mensaje) 
    {
        while (true) 
        {
            System.out.println(mensaje);
            try 
            {
                double valor = sc.nextDouble();
                sc.nextLine(); // Consume el residuo del salto de linea ('\n')
                return valor;
            } 
            catch (Exception e) 
            {
                // Maneja genericamente cualquier anomalia tipografica (ej. ingresar un punto cuando el sistema espera coma)
                System.out.println("Debe ingresar un numero decimal usando el separador correcto.");
                sc.nextLine(); // Limpia la entrada corrupta del buffer antes de reiniciar el ciclo
            }
        }
    }

    /**
     * Lee de forma directa lineas completas de caracteres sin riesgo de saltos automaticos no deseados.
     * 
     * @param sc Instancia compartida del Scanner.
     * @param mensaje Indicacion en pantalla.
     * @return La cadena de caracteres digitada por el usuario.
     */
    public static String leerTexto(Scanner sc, String mensaje) 
    {
        System.out.println(mensaje);
        // Al usar nextLine() directamente tras haber mantenido limpios los buffers numericos anteriores,
        // garantizamos una captura limpia sin efectos secundarios.
        return sc.nextLine();
    }
}


package ui;

import java.util.List;
import java.util.Scanner;

import model.Producto;
import service.ProductoService;
import util.Validador;

/*
    Manejar la interaccion con el usuario: mostrar el menu, leer datos, mostrar mensajes de error o exito.

    Responsable de:
        - Mostrar el menu al usuario
        - Pedirle los datos
        - Mostrar los resultados
    
    No contiene la logica del negocio.
    No controla el flujo del programa.
*/

public class MenuProducto 
{
    // Atributos de la clase: el Scanner y el Service se reciben por constructor (no se crean aqui dentro).
    // Esto permite que quien instancie la clase tenga el control sobre que instancias usar.
    private final Scanner sc;
    private final ProductoService service;

    /**
     * Constructor de la clase.
     * Implementa el patron de "Inyeccion de Dependencias por Constructor" (muy utilizado en Spring Boot).
     * @param sc Objeto Scanner compartido para la lectura de datos por consola.
     * @param service Capa de servicio encargada de la logica del negocio de los productos.
     */
    public MenuProducto(Scanner sc, ProductoService service) 
    {
        this.sc = sc;
        this.service = service;
    }

    /**
     * Muestra visualmente las opciones disponibles en la consola.
     * Incluye saltos de linea al inicio para limpiar y refrescar el aspecto de la pantalla.
     */
    public void mostrarMenu() 
    {
        System.out.println("\n\n\n"); // Empuja el texto anterior hacia arriba para simular una pantalla limpia
        System.out.println("======= TechLab - Gestion de Productos =======");
        System.out.println("1) Agregar producto");
        System.out.println("2) Listar productos");
        System.out.println("3) Buscar producto por ID");
        System.out.println("4) Actualizar producto");
        System.out.println("5) Eliminar producto");
        System.out.println("6) Salir");
        System.out.println("==============================================");
    }

    // ==========================================
    // Operaciones del CRUD (Opciones del menu)
    // ==========================================

    /**
     * Opcion 1: Solicita los datos de un nuevo producto, lo construye y delega el guardado al servicio.
     */
    public void agregarProducto() 
    {
        System.out.println("--- Nuevo producto ---");
        
        // Se utiliza la clase utilitaria 'Validador' para garantizar que los tipos de datos ingresados sean correctos
        String nombre = Validador.leerTexto(sc, "Nombre: ");
        double precio = Validador.leerDouble(sc, "Precio: ");
        int stock = Validador.leerEntero(sc, "Stock: ");
        String categoria = Validador.leerTexto(sc, "Categoria: ");

        // Construimos el objeto producto de forma temporal con los datos capturados
        Producto p = new Producto(nombre, precio, stock, categoria);
        
        // Enviamos el producto al servicio. El metodo 'guardar' se encarga internamente de validar las 
        // reglas de negocio (ej. precios no negativos) y de asignarle un ID unico de forma automatica.
        Producto guardado = service.guardar(p); 
        
        // Confirmamos el exito de la operacion mostrando el ID generado por el sistema
        System.out.println("Producto agregado con el id " + guardado.getId());
    }

    /**
     * Opcion 2: Recupera la lista completa de productos desde el servicio y los muestra en pantalla.
     */
    public void listarProductos()
    {
        // Solicitamos la lista de productos almacenada en la capa de negocio
        List<Producto> lista = service.listarTodos();
        
        // Validacion local de interfaz: Si la lista viene vacia, informamos al usuario y cortamos la ejecucion del metodo
        if (lista.isEmpty()){
            System.out.println("No hay productos cargados.");
            return;
        }
    
        System.out.println("--- Catalogo ---");
        // Recorremos la lista usando un bucle for-each para imprimir cada producto de manera estructurada
        for(Producto p : lista){
            p.mostrar(); // Llama al metodo interno del modelo disenado para imprimir con formato propio
        }
    }

    /**
     * Opcion 3: Solicita un ID numerico e intenta recuperar el producto correspondiente mediante el servicio.
     */
    public void buscarProducto()
    {
        // Solicitamos de forma segura un numero entero para el ID
        int id = Validador.leerEntero(sc, "Ingrese el id del producto: ");

        // El servicio buscara el producto. Si no existe, lanzara una excepcion (ProductoNoEncontradoException)
        // que sera capturada y manejada por el try/catch ubicado en la clase Main.java
        Producto p = service.obtenerPorId(id);
        
        // Si el producto fue encontrado con exito, se muestra su representacion en texto
        System.out.println("Encontrado: " + p);
    }

    /**
     * Opcion 4: Busca un producto existente y actualiza todas sus propiedades con nuevos valores provistos.
     */
    public void actualizarProducto() 
    {
        int id = Validador.leerEntero(sc, "Ingrese el id del producto a actualizar: ");

        // Recuperamos y mostramos primero los datos actuales para que el usuario visualice que esta modificando.
        // Al igual que en buscarProducto, si el ID no existe, el flujo se interrumpe aqui por la excepcion del servicio.
        Producto actual = service.obtenerPorId(id);
        System.out.println("Datos actuales: " + actual);

        System.out.println("--- Ingrese los nuevos datos ---");
        String nombre = Validador.leerTexto(sc, "Nombre: ");
        double precio = Validador.leerDouble(sc, "Precio: ");
        int stock = Validador.leerEntero(sc, "Stock: ");
        String categoria = Validador.leerTexto(sc, "Categoria: ");

        // Creamos un nuevo objeto con los cambios requeridos
        Producto datos = new Producto(nombre, precio, stock, categoria);
        
        // Enviamos el ID del destino y el objeto con las modificaciones al servicio para que procese el cambio
        Producto actualizado = service.actualizar(id, datos);

        // Confirmamos mostrando en pantalla el estado final del producto modificado
        System.out.println("Producto actualizado: " + actualizado);
    }

    /**
     * Opcion 5: Solicita un ID numerico y delega la remocion fisica o logica del producto al servicio.
     */
    public void eliminarProducto() 
    {
        int id = Validador.leerEntero(sc, "Ingrese el id del producto a eliminar: ");
        
        // El servicio valida la existencia del ID y procede a borrarlo del almacen de datos
        service.eliminar(id);
        
        // Si el servicio no arrojo ninguna excepcion, confirmamos la eliminacion exitosa
        System.out.println("Producto eliminado.");
    }
}


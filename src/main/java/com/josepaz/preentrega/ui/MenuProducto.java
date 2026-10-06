package ui;

import java.util.List;
import java.util.Scanner;

import model.Producto;
import service.ProductoService;
import util.Validador;

/*
    Manejar la interacción con el usuario: mostrar el menu, leer datos, mostrar mensajes de error o exito.

    responsable de:
        -Mostrar el menu al usuario"
        -Pedirle los datos
        -Mostrar los resultados
    
    No contiene la logica del negocio
    No controla el flujo del programa

    
*/

public class MenuProducto 
{
    // Atributo : el scanner y el service se reciben por contructor ( no se crean
    // aca adentro )
    // para quien instancie la clase tenga el control sobre que usar

    private final Scanner sc;
    private final ProductoService service;

    // "Inyeccion por constructor" patron utilizado en Spring Boot
    public MenuProducto(Scanner sc, ProductoService service) 
    {
        this.sc = sc;
        this.service = service;
    }

    // Menu principal

    public void mostrarMenu() 
    {
        System.out.println("======= TechLab - Gestión de Productos =======");
        System.out.println("1) Agregar producto");
        System.out.println("2) Listar productos");
        System.out.println("3) Buscar producto por ID");
        System.out.println("4) Actualizar producto");
        System.out.println("5) Eliminar producto");
        System.out.println("6) Salir");
        System.out.println("==============================================");
    }

    // Operaciones del CRUD

    // cada metodo corresponde a una opcion del menu

    public void agregarProducto() 
    {
        System.out.println("--- Nuevo producto ---");
        String nombre = Validador.leerTexto(sc, "Nombre: ");
        double precio = Validador.leerDouble(sc, "Precio: ");
        int stock = Validador.leerEntero(sc, "Stock: ");
        String categoria = Validador.leerTexto(sc, "Categoría: ");

         
        

        // Construimos el producto
        Producto p = new Producto(nombre,precio,stock,categoria);
        // lo enviamos al servicio
        Producto guardado = service.guardar(p); // El servicio se encarga de validar y asignar el id
        
        System.out.println("Producto agregado con el id " + guardado.getId());

    }

    public void listarProductos()
    {
        // Recibimos un List ( ver ProductoService )
        List<Producto> lista = service.listarTodos();
        if (lista.isEmpty()){
            System.out.println("No hay productos cargados.");
            return;
        }
    
        System.out.println("--- Catálogo ---");
        for(Producto p : lista){
            //System.out.println(p);
            p.mostrar();
        }
    }

    public void buscarProducto()
    {
        int id = Validador.leerEntero(sc, "Ingrese el id del producto: ");

        Producto p = service.obtenerPorId(id);
        System.out.println("Encontrado: " + p);
    }

    public void actualizarProducto() 
    {
        int id = Validador.leerEntero(sc, "Ingrese el id del producto a actualizar: ");

        // Mostramos primero los datos actuales para que el usuario
        // sepa qué está modificando.
        Producto actual = service.obtenerPorId(id);
        System.out.println("Datos actuales: " + actual);

        System.out.println("--- Ingrese los nuevos datos ---");
        String nombre = Validador.leerTexto(sc, "Nombre: ");
        double precio = Validador.leerDouble(sc, "Precio: ");
        int stock = Validador.leerEntero(sc, "Stock: ");
        String categoria = Validador.leerTexto(sc, "Categoría: ");

        Producto datos = new Producto(nombre, precio, stock, categoria);
        Producto actualizado = service.actualizar(id, datos);

        System.out.println("Producto actualizado: " + actualizado);
    }

    public void eliminarProducto() 
    {
        int id = Validador.leerEntero(sc, "Ingrese el id del producto a eliminar: ");
        service.eliminar(id);
        System.out.println("Producto eliminado.");
    }


}



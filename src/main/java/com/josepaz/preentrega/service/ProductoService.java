package service;

import java.util.ArrayList;
import java.util.List;

import exception.ProductoNoEncontradoException;
import model.Producto;
import util.Validador;

/**
 * Capa de servicio: contiene la logica de negocio de nuestro sistema.
 * 
 * Es responsable de:
 * - Mantener la coleccion de productos.
 * - Asignar el ID al guardar un nuevo producto.
 * - Validar los datos antes de guardar o actualizar.
 * - Buscar, modificar y eliminar productos por ID.
 *
 * No contiene Scanner ni System.out: no interactua con el usuario.
 * Quien requiera mostrar mensajes o leer datos lo realiza por fuera (desde la clase Main).
 * Nota: Esta separacion permitira, en etapas posteriores, reemplazar el menu 
 * por una API REST sin necesidad de modificar este archivo.
 */
public class ProductoService 
{    
    // Coleccion en memoria que almacena los productos
    private List<Producto> productos = new ArrayList<>();

    // Contador para asignar IDs unicos. Es static porque pertenece a la clase 
    // y no a una instancia, garantizando su unicidad.
    private static int contadorId = 1;

    // =============================================================
    // OPERACIONES CRUD (Create, Read, Update, Delete)
    // =============================================================

    // CREATE: agregar un nuevo producto
    public Producto guardar(Producto p) 
    {
        // Validamos antes de guardar. Si ocurre un error, se lanza una
        // excepcion y el producto NO se agrega a la lista.
        Validador.validarNombre(p.getNombre());
        Validador.validarPrecio(p.getPrecio());
        Validador.validarStock(p.getStock());
        Validador.validarCategoria(p.getCategoria());

        // El ID lo asigna el servicio, no el usuario.
        // Luego de asignarlo, se incrementa el contador.
        p.setId(contadorId);
        contadorId++;

        // Guardamos el producto en la coleccion
        productos.add(p);

        return p;
    }

    // READ: devuelve toda la lista de productos
    public List<Producto> listarTodos() 
    {
        return productos;
    }

    // READ (Individual): busca un producto por ID
    public Producto obtenerPorId(int id) 
    {
        for (Producto p : productos) 
        {
            if (p.getId() == id) 
            {
                return p;
            }
        }

        throw new ProductoNoEncontradoException("No se encontro un producto con el ID " + id);
    }

    // UPDATE: actualiza los datos de un producto existente
    public Producto actualizar(int id, Producto datos) 
    {
        // Reutilizamos obtenerPorId. Si lanza excepcion, la actualizacion se cancela.
        Producto p = obtenerPorId(id);

        // Validamos los datos nuevos antes de aplicarlos
        Validador.validarNombre(datos.getNombre());
        Validador.validarPrecio(datos.getPrecio());
        Validador.validarStock(datos.getStock());
        Validador.validarCategoria(datos.getCategoria());

        // Modificamos el producto encontrado.
        // Como Java maneja los objetos por referencia, los cambios se reflejan
        // en la lista sin necesidad de realizar ninguna accion adicional.
        p.setNombre(datos.getNombre());
        p.setPrecio(datos.getPrecio());
        p.setStock(datos.getStock());
        p.setCategoria(datos.getCategoria());

        return p;
    }

    // DELETE: eliminar un producto por ID
    public void eliminar(int id) 
    {
        Producto p = obtenerPorId(id);
        productos.remove(p);
    }
}


package service;

import java.util.ArrayList;
import java.util.List;

import exception.ProductoNoEncontradoException;
import model.Producto;
import util.Validador;

/**
 * Capa de Servicio (Service Layer): Orquesta e implementa la Logica de Negocio (Business Logic) del sistema.
 * 
 * Es la capa intermedia que actua como barrera de seguridad entre la Interfaz de Usuario (UI) 
 * y la persistencia de datos (en este caso, una coleccion en memoria). 
 * 
 * Responsabilidades:
 * - Gestionar el ciclo de vida del almacen de datos (List).
 * - Controlar la generacion e integridad de llaves primarias unicas (IDs).
 * - Forzar el cumplimiento de las reglas o restricciones de negocio mediante validaciones antes de cualquier mutacion.
 * 
 * Cumple con el principio de Desacoplamiento (Alta Cohesion / Bajo Acoplamiento): Al no contener codigo 
 * acoplado a la consola (sin Scanner ni System.out), esta misma clase podra ser reutilizada intacta en el futuro 
 * cuando el sistema migre a una arquitectura Web o API REST con Spring Boot.
 */
public class ProductoService 
{    
    // Almacenamiento volatil en memoria utilizando una lista dinamica (Estructura de Datos).
    // Se encapsula como 'private' para que ninguna clase externa altere la coleccion sin pasar por las reglas del servicio.
    private List<Producto> productos = new ArrayList<>();

    // Generador secuencial de IDs unicos. Al ser un atributo estatico (static), su valor se conserva a nivel 
    // de Clase y no de Instancia, garantizando consistencia matematica y evitando la duplicidad de identificadores.
    private static int contadorId = 1;

    // =============================================
    // OPERACIONES CRUD (Create, Read, Update, Delete)
    // ============================================

    /**
     * CREATE: Procesa, valida y persiste una nueva entidad Producto en el sistema.
     * Si alguna validacion de datos falla, se dispara una RuntimeException interrumpiendo el flujo, 
     * lo que garantiza que nunca ingresen datos corruptos a la lista.
     * 
     * @param p Objeto producto transitorio (aun sin ID).
     * @return El objeto Producto completamente persistido (con su ID oficial asignado).
     */
    public Producto guardar(Producto p) 
    {
        // Fase de Validacion: Filtro estricto que evalua las restricciones del negocio antes de impactar los datos
        Validador.validarNombre(p.getNombre());
        Validador.validarPrecio(p.getPrecio());
        Validador.validarStock(p.getStock());
        Validador.validarCategoria(p.getCategoria());

        // Asignacion de Identificador: Mecanismo de persistencia controlado internamente por el backend.
        // Se aplica un post-incremento (contadorId++) para preparar automaticamente el valor del siguiente registro.
        p.setId(contadorId);
        contadorId++;

        // Insercion en la estructura de datos en memoria
        productos.add(p);

        return p;
    }

    /**
     * READ (Coleccion): Expone la lista completa de elementos registrados.
     * @return List de objetos Producto actualmente almacenados en memoria.
     */
    public List<Producto> listarTodos() 
    {
        return productos;
    }

    /**
     * READ (Individual): Implementa un algoritmo de busqueda lineal para localizar un registro por su ID unico.
     * 
     * @param id Identificador numerico del producto deseado.
     * @return El objeto Producto que coincide exactamente con el parametro recibido.
     * @throws ProductoNoEncontradoException Excepcion personalizada si el bucle termina sin hallar coincidencias.
     */
    public Producto obtenerPorId(int id) 
    {
        for (Producto p : productos) 
        {
            if (p.getId() == id) 
            {
                return p; // Retorno inmediato (Early Return) al encontrar el objetivo, optimizando el ciclo
            }
        }

        // Control de errores de negocio: Si el flujo llega a este punto, significa que el ID es invalido
        throw new ProductoNoEncontradoException("No se encontro un producto con el ID " + id);
    }

    /**
     * UPDATE: Sobreescribe el estado de un registro existente basandose en un ID de destino.
     * 
     * Nota de Arquitectura: El parametro datos actua tecnicamente como un DTO (Data Transfer Object), 
     * un contenedor temporal que transporta los nuevos datos desde la UI hacia la logica de actualizacion.
     * 
     * @param id Identificador del producto real que se desea modificar.
     * @param datos Objeto con la informacion actualizada que se va a inyectar.
     * @return La entidad Producto con su nuevo estado consolidado.
     */
    public Producto actualizar(int id, Producto datos) 
    {
        // Reutilizacion de codigo: Se delega la busqueda a obtenerPorId. 
        // Si el ID no existe, este metodo lanza su excepcion nativa abortando el proceso de forma segura.
        Producto p = obtenerPorId(id);

        // Validamos exhaustivamente el paquete de cambios antes de mutar el objeto original
        Validador.validarNombre(datos.getNombre());
        Validador.validarPrecio(datos.getPrecio());
        Validador.validarStock(datos.getStock());
        Validador.validarCategoria(datos.getCategoria());

        // Mutacion de estado por Referencia: Debido a que Java manipula las referencias a objetos en memoria, 
        // al alterar las propiedades de 'p', los cambios impactan directamente sobre el elemento dentro de la List.
        // No es necesario remover e insertar el elemento de nuevo.
        p.setNombre(datos.getNombre());
        p.setPrecio(datos.getPrecio());
        p.setStock(datos.getStock());
        p.setCategoria(datos.getCategoria());

        return p;
    }

    /**
     * DELETE: Remueve fisicamente un registro del almacen de datos basandose en su ID.
     * @param id Identificador numerico del objeto a dar de baja.
     */
    public void eliminar(int id) 
    {
        // Localiza el objeto en memoria. Si no existe, detiene la operacion lanzando la excepcion correspondiente.
        Producto p = obtenerPorId(id);
        
        // Remocion fisica: Rompe el enlace del objeto dentro del ArrayList
        productos.remove(p);
    }
}


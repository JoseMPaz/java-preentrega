package model;

/**
 * Modelo de Dominio o Plain Old Java Object (POJO): Representa la entidad basica de un Producto.
 * 
 * Aplica el principio de Encapsulamiento: Los atributos son privados y el acceso se restringe
 * a traves de metodos publicos (getters y setters). Esta clase sigue el Principio de Responsabilidad
 * Unica (SRP), limitandose a modelar un objeto del mundo real; no gestiona bases de datos ni logica visual.
 */
public class Producto 
{
    // ============================================
    // ATRIBUTOS (Estado del Objeto)
    // ============================================
    // Al ser 'private', se protege la integridad de los datos impidiendo modificaciones 
    // externas maliciosas o directas que puedan romper el estado de la entidad.
    private int id;
    private String nombre;
    private double precio;
    private int stock;
    private String categoria;

    // ============================================
    // CONSTRUCTORES (Instanciacion)
    // ============================================

    /**
     * Constructor Parametrizado (Sin ID).
     * Se utiliza para crear productos nuevos antes de ser persistidos. El ID no se incluye 
     * aqui porque su generacion es responsabilidad de la capa de persistencia (Base de datos / Servicio).
     */
    public Producto(String nombre, double precio, int stock, String categoria) 
    {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }

    /**
     * Constructor Vacio (Por defecto).
     * Requisito fundamental de la especificacion JavaBeans. Permite instanciar objetos "vacios" 
     * para completarlos progresivamente mediante Setters. Es obligatorio para que frameworks 
     * de mapeo como Hibernate/JPA, Jackson (JSON) o Spring puedan realizar la instanciacion por reflexion.
     */
    public Producto() 
    {
    	// Bloque intencionalmente vacio para cumplir con el estandar POJO/JavaBean
    }

    // ============================================
    // METODOS DE ACCESO (Getters y Setters)
    // ============================================
    // Constituyen la interfaz publica segura para interactuar con los atributos privados.

    public int getId() 
    {
        return id;
    }

    public void setId(int id) 
    {
        this.id = id;
    }

    public String getNombre() 
    {
        return nombre;
    }

    public void setNombre(String nombre) 
    {
        this.nombre = nombre;
    }

    public double getPrecio() 
    {
        return precio;
    }

    public void setPrecio(double precio) 
    {
        this.precio = precio;
    }

    public int getStock() 
    {
        return stock;
    }

    public void setStock(int stock) 
    {
        this.stock = stock;
    }

    public String getCategoria() 
    {
        return categoria;
    }

    public void setCategoria(String categoria) 
    {
        this.categoria = categoria;
    }

    // ============================================
    // METODOS DE REPRESENTACION Y SALIDA
    // ============================================

    /**
     * Sobreescritura del metodo estandar toString() heredado de la clase base java.lang.Object.
     * Convierte la direccion de memoria nativa del objeto en una cadena de texto util y legible.
     * Ideal para tareas de depuracion (debugging) o concatenaciones rapidas de texto.
     * @return Una representacion formateada con el estado actual de los atributos del producto.
     */
    @Override
    public String toString() 
    {
        return "ID: " + id +
                " | " + nombre +
                " | $" + precio +
                " | Stock: " + stock +
                " | Categoria: " + categoria;
    }
    
    /**
     * Envia directamente la informacion formateada del producto hacia el flujo de salida estandar (Consola).
     * Disenado especificamente para estandarizar la visualizacion de registros individuales dentro de la UI.
     */
    public void mostrar() 
    {
        System.out.println("ID: " + id +
                " | " + nombre +
                " | $" + precio +
                " | Stock: " + stock +
                " | Categoria: " + categoria);
    }
}


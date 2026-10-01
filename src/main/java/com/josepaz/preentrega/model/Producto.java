package model;

/**
 * Modelo de dominio: representa un producto del catalogo.
 *
 * Aplica encapsulamiento: los atributos son privados y se accede
 * a ellos a traves de getters y setters. Esta clase no sabe nada
 * sobre como se almacenan los productos ni como se muestran al
 * usuario; su unica responsabilidad es representar un producto.
 */
public class Producto 
{
    // Atributos privados: nadie desde afuera puede modificarlos
    // directamente. Para acceder o modificarlos se usan los metodos
    // getters y setters definidos mas abajo.
    private int id;
    private String nombre;
    private double precio;
    private int stock;
    private String categoria;

    // Constructor sin id: el id lo asigna el ProductoService al
    // momento de guardar el producto. El usuario nunca elige el id.
    public Producto(String nombre, double precio, int stock, String categoria) 
    {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }

    // Constructor vacio: util para crear un Producto y completarlo
    // con setters despues. Tambien lo necesitara Spring/JPA mas adelante en el curso.
    public Producto() 
    {
    	//Vacio
    }

    // Getters y setters: la unica forma de acceder o modificar
    // los atributos privados desde afuera de la clase.
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

    // toString() sobreescribe el metodo heredado de Object.
    // Sirve para mostrar el producto de forma legible al listarlo
    // en consola. Cuando hagamos System.out.println(producto), Java
    // llama automaticamente a este metodo.
    @Override
    public String toString() 
    {
        return "ID: " + id +
                " | " + nombre +
                " | $" + precio +
                " | Stock: " + stock +
                " | Categoria: " + categoria;
    }
}


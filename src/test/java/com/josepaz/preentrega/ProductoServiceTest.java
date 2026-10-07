package com.josepaz.preentrega;

// IMPORTS ESTATICOS: Permiten usar los metodos de verificacion de JUnit 5
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

// NUEVOS IMPORTS: Enlazan las clases de negocio con la suite de pruebas
import service.ProductoService;
import model.Producto;
import exception.ProductoNoEncontradoException;
import exception.StockInsuficienteException;

/**
 * Clase de pruebas unitarias para ProductoService utilizando JUnit 5.
 * Aplica el patron internacional AAA (Arrange, Act, Assert).
 */
public class ProductoServiceTest 
{
    private ProductoService service;

    /**
     * Ciclo de Vida - Configuracion Inicial (Setup).
     * Se ejecuta antes de cada metodo de prueba individual para limpiar el estado de la memoria.
     * Invoca reiniciarServicio para restablecer tambien el contador secuencial de IDs estaticos.
     */
    @BeforeEach
    public void setUp() 
    {
        service = new ProductoService();
        service.reiniciarServicio(); // NUEVO: Garantiza que el primer ID asignado sea siempre 1
    }

    /**
     * Ciclo de Vida - Despliegue de Resultados (Teardown).
     * Se ejecuta inmediatamente despues de que un test finaliza con exito.
     * Utiliza TestInfo para obtener el nombre del metodo y mostrarlo en la terminal de Linux.
     */
    @AfterEach
    public void alFinalizarCadaTest(TestInfo testInfo) 
    {
        System.out.println(" -> OK: Test aprobado con exito: " + testInfo.getTestMethod().get().getName() + "()");
    }

    // =========================================
    // TESTS PARA LA OPERACION: GUARDAR (CREATE)
    // =========================================

    @Test
    public void guardarProducto_Exitoso() 
    {
        Producto nuevo = new Producto("Teclado Mecanico", 8500.0, 15, "Electronica");
        Producto guardado = service.guardar(nuevo);

        assertNotNull(guardado);
        assertEquals(1, guardado.getId());
        assertEquals("Teclado Mecanico", guardado.getNombre());
        assertEquals(15, guardado.getStock());
    }

    @Test
    public void guardarProducto_Error_NombreVacio_LanzaException() 
    {
        Producto invalido = new Producto("   ", 500.0, 10, "Almacen");

        assertThrows(IllegalArgumentException.class, () -> {
            service.guardar(invalido);
        });
    }

    @Test
    public void guardarProducto_Error_PrecioNegativo_LanzaException() 
    {
        Producto invalido = new Producto("Mouse", -150.0, 5, "Electronica");

        assertThrows(IllegalArgumentException.class, () -> {
            service.guardar(invalido);
        });
    }

    @Test
    public void guardarProducto_Error_StockNegativo_LanzaStockInsuficienteException() 
    {
        Producto invalido = new Producto("Monitor", 45000.0, -1, "Electronica");

        assertThrows(StockInsuficienteException.class, () -> {
            service.guardar(invalido);
        });
    }

    // =========================================
    // TESTS PARA LA OPERACION: OBTENER POR ID / LISTAR (READ)
    // =========================================

    @Test
    public void obtenerPorId_Exitoso() 
    {
        Producto p = service.guardar(new Producto("Gorra", 1200.0, 20, "Indumentaria"));
        Producto encontrado = service.obtenerPorId(p.getId());

        assertNotNull(encontrado);
        assertEquals(p.getId(), encontrado.getId());
    }

    @Test
    public void obtenerPorId_Error_IdInexistente_LanzaProductoNoEncontradoException() 
    {
        assertThrows(ProductoNoEncontradoException.class, () -> {
            service.obtenerPorId(999);
        });
    }

    @Test
    public void listarTodos_Exitoso() 
    {
        service.guardar(new Producto("A", 10.0, 5, "Cat"));
        service.guardar(new Producto("B", 20.0, 10, "Cat"));

        List<Producto> lista = service.listarTodos();
        assertEquals(2, lista.size());
    }

    // =========================================
    // TESTS PARA LA OPERACION: ACTUALIZAR (UPDATE)
    // =========================================

    @Test
    public void actualizarProducto_Exitoso() 
    {
        Producto original = service.guardar(new Producto("Original", 100.0, 5, "Cat"));
        Producto nuevosDatos = new Producto("Modificado", 150.0, 8, "NuevaCat");

        Producto actualizado = service.actualizar(original.getId(), nuevosDatos);

        assertEquals("Modificado", actualizado.getNombre());
        assertEquals(150.0, actualizado.getPrecio());
        assertEquals(8, actualizado.getStock());
    }

    // =========================================
    // TESTS PARA LA OPERACION: ELIMINAR (DELETE)
    // =========================================

    @Test
    public void eliminarProducto_Exitoso() 
    {
        Producto p = service.guardar(new Producto("Eliminar", 50.0, 2, "Cat"));
        int id = p.getId();

        service.eliminar(id);

        assertTrue(service.listarTodos().isEmpty());
        assertThrows(ProductoNoEncontradoException.class, () -> {
            service.obtenerPorId(id);
        });
    }
}


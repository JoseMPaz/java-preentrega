package exception;

/**
 * Excepcion personalizada lanzada cuando se intenta asignar un stock invalido
 * (por ejemplo, un valor negativo).
 * 
 * Tambien permite gestionar las validaciones del carrito de compras, senalando
 * los casos en los que un cliente solicita mas unidades de las disponibles
 * en el inventario actual.
 */
public class StockInsuficienteException extends RuntimeException 
{ 
    public StockInsuficienteException(String mensaje) 
    {
        // Llama al constructor de RuntimeException para almacenar el mensaje de error
        super(mensaje);
    }
}


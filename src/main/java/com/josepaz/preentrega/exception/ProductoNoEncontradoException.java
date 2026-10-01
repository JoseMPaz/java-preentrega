package exception;

/**
 * Excepcion personalizada lanzada cuando un producto buscado por su ID
 * no existe en el sistema.
 * 
 * Hereda de RuntimeException (excepciones no verificadas). Esto evita obligar
 * al desarrollador a usar bloques try/catch de forma explicita, manteniendo
 * el codigo mas limpio, pero permitiendo su captura cuando sea necesario.
 * 
 * El uso de excepciones propias mejora la expresividad del modelo de dominio,
 * ofreciendo nombres claros en lugar de utilizar clases genericas como
 * Exception o IllegalArgumentException.
 */
public class ProductoNoEncontradoException extends RuntimeException 
{    
    public ProductoNoEncontradoException(String mensaje) 
    {
        // super llama al constructor de la clase padre (RuntimeException),
        // la cual almacena el mensaje y lo expone mediante getMessage().
        super(mensaje);
    }
}


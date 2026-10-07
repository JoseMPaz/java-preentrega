package exception;

/**
 * Excepcion Personalizada de Negocio (Custom Domain Exception).
 * Se dispara exclusivamente cuando un proceso del sistema intenta interactuar con un ID de producto inexistente.
 * 
 * Arquitectura de Errores:
 * Hereda de java.lang.RuntimeException, clasificandose como una Unchecked Exception (Excepcion No Verificada).
 * A diferencia de las Checked Exceptions (que heredan directamente de java.lang.Exception), las excepciones no 
 * verificadas no obligan a propagar firmas con la palabra clave 'throws' ni fuerzan el uso de bloques try/catch 
 * en todas las capas intermedias, manteniendo el codigo limpio y desacoplado.
 * 
 * Ventajas del Diseno Expresivo (Domain-Driven Design):
 * Provee una semantica clara y especifica en la pila de llamadas (Stacktrace). Es mucho mas descriptivo 
 * leer un error tipo 'ProductoNoEncontradoException' que uno generico como 'IllegalArgumentException' o 'NullPointerException'.
 */
public class ProductoNoEncontradoException extends RuntimeException 
{    
    /**
     * Constructor de la excepcion personalizada.
     * 
     * @param mensaje Texto detallado que describe las circunstancias exactas de la anomalia (ej. el ID fallido).
     */
    public ProductoNoEncontradoException(String mensaje) 
    {
        // La palabra clave 'super' invoca de forma explicita al constructor parametrizado de la clase 
        // base (RuntimeException). Esto inicializa los mecanismos nativos de Java para registrar el mensaje 
        // de error y congelar el estado actual del hilo, permitiendo luego recuperarlo mediante e.getMessage().
        super(mensaje);
    }
}


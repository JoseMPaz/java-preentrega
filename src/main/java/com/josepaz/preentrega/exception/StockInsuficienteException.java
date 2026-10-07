package exception;

/**
 * Excepcion Personalizada de Negocio (Custom Domain Exception).
 * Se dispara cuando una operacion viola las reglas de consistencia de inventario, ya sea al ingresar 
 * valores de stock fisicamente imposibles (negativos) o al intentar egresar mas unidades de las disponibles.
 * 
 * Arquitectura de Errores:
 * Al igual que 'ProductoNoEncontradoException', hereda de java.lang.RuntimeException, consolidandose 
 * como una Unchecked Exception (Excepcion No Verificada). Esto permite un flujo libre de firmas en la capa 
 * de servicios ('ProductoService') y centraliza de forma elegante su captura en el controlador principal ('Main.java').
 * 
 * Polimorfismo Semantico:
 * Su diseno es lo suficientemente versatil para actuar en dos frentes del dominio del negocio:
 * 1. Fase de Administracion (Backoffice): Previene errores de carga (ej. ingresar un stock inicial de -10).
 * 2. Fase de Transaccion (E-Commerce): Servira como la base perfecta para el control del flujo cuando 
 *    el sistema evolucione y requiera validar un Carrito de Compras (ej. un cliente pide 5 unidades pero quedan 2).
 */
public class StockInsuficienteException extends RuntimeException 
{ 
    /**
     * Constructor de la excepcion personalizada de stock.
     * 
     * @param mensaje Texto descriptivo que detalla la inconsistencia de inventario detectada por el sistema.
     */
    public StockInsuficienteException(String mensaje) 
    {
        // La palabra clave 'super' delega la inicializacion del mensaje al constructor de RuntimeException.
        // Esto indexa la cadena de texto en las propiedades nativas del objeto arrojable (Throwable), 
        // quedando disponible para ser capturado en las capas superiores de la interfaz de usuario.
        super(mensaje);
    }
}


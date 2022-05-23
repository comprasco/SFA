package co.gov.supernotariado.bachue.dispositivos.common.enums;

/**
 * Enum que relaciona las salidas y sus codigos.
 */
public enum SalidasEnum {
   RECURSO_EXITOSO("200", "OK"),
   RECURSO_NO_VALIDO("422", "Recurso no válido"),
   RECURSO_NO_ENCONTRADO("404", "Recurso no encontrado"),
   EXCEPCION_NO_CONTROLADA("500", "Excepción no controlada"),
   FIRMA_NO_ENCONTRADO("400", "Firma NO Encontrada"),
   FIRMA_EXCEPCION_NO_CONTROLADA("500", "Error Grave");

   /**
    * Código de resultado
    */
   private final String cs_codigo;
   private final String cs_mensaje;

   /**
    * Constructor de la clase
    * @param as_codigo codigo de respuesta
    * @param as_mensaje mensaje de respuesta
    */
   SalidasEnum(final String as_codigo, String as_mensaje) {
      this.cs_codigo = as_codigo;
      this.cs_mensaje = as_mensaje;
   }

  /**
   * Metodo que retorna el codigo de la respuesta.
   * @return el código de respuesta
   */
   public String consultarCodigo() {
      return cs_codigo;
   }

  /**
   * Metodo que retorna el mensaje.
   * @return mensaje
   */
   public String consultarMensaje() {
      return cs_mensaje;
   }

}
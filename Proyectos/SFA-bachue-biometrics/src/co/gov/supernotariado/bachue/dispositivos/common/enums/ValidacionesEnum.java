package co.gov.supernotariado.bachue.dispositivos.common.enums;

/**
 * Enum que relaciona los mensajes de validacion
 */
public enum ValidacionesEnum {
  VALIDACION_EXITOSA("Validado exitosamente"),
  CLAVE_LARGA("La contraseña es demasiado larga"),
  CLAVE_CORTA("La contraseña es demasiado corta"),
  POCA_COMPLEJIDAD("La contraseña debe tener al menos un caracter en minúscula, uno en mayúscula y un símbolo especial");

   /**
    * Mensaje
    */
   private final String cs_mensaje;

   /**
    * Establece el mensaje
    * @param as_mensaje mensaje
    */
   ValidacionesEnum(final String as_mensaje) {
      this.cs_mensaje = as_mensaje;
   }

  /**
   * Metodo que retorna el mensaje de validacion
   */
   public String consultarMensaje() {
      return cs_mensaje;
   }
}

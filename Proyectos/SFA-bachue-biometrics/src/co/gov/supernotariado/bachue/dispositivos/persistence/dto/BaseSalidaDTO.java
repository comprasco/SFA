package co.gov.supernotariado.bachue.dispositivos.persistence.dto;

/**
 * DTO de base que contiene las propiedades comunes de los DTOs de salida.
 */
public class BaseSalidaDTO {
   /**
    * Código de resultado
    */
   protected String is_codigo;

   /**
    * Mensaje de resultado
    */
   protected String is_mensaje;

   /**
    * Retorna el código de resultado
    * @return código de resultado
    */
   public String getCodigo() {
      return is_codigo;
   }

   /**
    * Establece el código de resultado
    * @param as_codigo código de resultado
    */
   public void setCodigo(String as_codigo) {
      this.is_codigo = as_codigo;
   }

   /**
    * Retorna el mensaje de resultado
    * @return mensaje de resultado
    */
   public String getMensaje() {
      return is_mensaje;
   }

   /**
    * Establece el mensaje de resultado
    * @param as_mensaje mensaje de resultado
    */
   public void setMensaje(String as_mensaje) {
      this.is_mensaje = as_mensaje;
   }
}

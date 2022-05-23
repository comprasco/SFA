package co.gov.supernotariado.bachue.dispositivos.persistence.dto;

/**
 *  DTO de salida para strings.
 */
public class StringSalidaDTO extends BaseSalidaDTO {

   /**
    * Resultado
    */
   private String is_resultado;

   /**
    * Retorna el resultado
    * @return resultado
    */
   public String getResultado() {
      return is_resultado;
   }

   /**
    * Establece el resultado
    * @param as_resultado resultado
    */
   public void setResultado(String as_resultado) {
      this.is_resultado = as_resultado;
   }
}

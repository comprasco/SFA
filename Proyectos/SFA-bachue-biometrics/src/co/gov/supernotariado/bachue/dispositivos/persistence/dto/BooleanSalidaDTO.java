package co.gov.supernotariado.bachue.dispositivos.persistence.dto;

/**
 * DTO de salida para booleanos.
 */
public class BooleanSalidaDTO extends BaseSalidaDTO {

   /**
    * Resultado
    */
   private Boolean ib_resultado;

   /**
    * Retorna el resultado
    * @return resultado
    */
   public Boolean getResultado() {
      return ib_resultado;
   }

   /**
    * Establece el resultado
    * @param ab_resultado resultado
    */
   public void setResultado(Boolean ab_resultado) {
      this.ib_resultado = ab_resultado;
   }
}

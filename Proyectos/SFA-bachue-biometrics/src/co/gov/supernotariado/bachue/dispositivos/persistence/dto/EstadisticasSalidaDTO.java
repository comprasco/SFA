package co.gov.supernotariado.bachue.dispositivos.persistence.dto;

/**
 * DTO de salida de estadisticas.
 */
public class EstadisticasSalidaDTO extends BaseSalidaDTO {

   /**
    * Contador 
    */
   private int ii_contador;

   /**
    * Retorna el contador
    * @return contador
    */
   public int getContador() {
      return ii_contador;
   }

   /**
    * Establece el contador
    * @param ai_contador contador
    */
   public void setContador(int ai_contador) {
      this.ii_contador = ai_contador;
   }
}

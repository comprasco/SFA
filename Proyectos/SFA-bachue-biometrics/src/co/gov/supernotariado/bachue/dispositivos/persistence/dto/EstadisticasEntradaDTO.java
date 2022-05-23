package co.gov.supernotariado.bachue.dispositivos.persistence.dto;

/**
 * DTO de entrada de estadisticas.
 *
 */
public class EstadisticasEntradaDTO {
   /**
    * Entidad de registro
    */
   private String is_idEntidad;
   
   /**
    * Tipo de evento
    */
   private String is_tipo;

   /**
    * Retorna la entidad de registro
    * @return entidad de registro
    */
   public String getIdEntidad() {
      return is_idEntidad;
   }

   /**
    * Establece la entidad de registro
    * @param as_idEntidad entidad de registro
    */
   public void setIdEntidad(String as_idEntidad) {
      this.is_idEntidad = as_idEntidad;
   }

   /**
    * Retorna el tipo de evento
    * @return tipo de evento
    */
   public String getTipo() {
      return is_tipo;
   }

   /**
    * Establece el tipo de evento
    * @param as_tipo tipo de evento
    */
   public void setTipo(String as_tipo) {
      this.is_tipo = as_tipo;
   }
}

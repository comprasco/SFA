package co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dto;

/**
 * DTO de entrada de sesion.
 */
public class SesionEntradaDTO {

   /**
    * id de la sesión
    */
  private String is_sesion;

  /**
   * Retorna el id de la sesión
   * @return id de la sesión
   */
  public String getSesion() {
    return is_sesion;
  }

  /**
   * Establece el id de sesión
   * @param as_sesion id de sesión
   */
  public void setSesion(String as_sesion) {
    this.is_sesion = as_sesion;
  }
}

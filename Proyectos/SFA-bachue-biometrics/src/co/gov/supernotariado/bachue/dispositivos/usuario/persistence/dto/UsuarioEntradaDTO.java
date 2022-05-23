package co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dto;

/**
 * DTO de entrada de usuario.
 *
 */
public class UsuarioEntradaDTO {

   /**
    * Id del usuario
    */
  private String is_idUsuario;

  /**
   * Retorna el id del usuario
   * @return id del usuario
   */
  public String getIdUsuario() {
    return is_idUsuario;
  }

  /**
   * Establece el id del usuario
   * @param as_idUsuario
   */
  public void setIdUsuario(String as_idUsuario) {
    this.is_idUsuario = as_idUsuario;
  }
}

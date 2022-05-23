package co.gov.supernotariado.bachue.dispositivos.usuario.ejb;

import co.gov.supernotariado.bachue.dispositivos.persistence.dto.BooleanSalidaDTO;
import co.gov.supernotariado.bachue.dispositivos.persistence.dto.StringSalidaDTO;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dao.ClaveDTO;
import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dto.UsuarioDTO;

/**
 * Interface de lógica de negocio de usuarios.
 */
public interface IUsuarioBusiness {
  /**
   * Método que envia los datos del usuario al DAO para ser almacenado.
   * @param aud_usuario DTO con la informacion del usuario.
   * @return true si el usuario es registrado con éxito.
   */
  StringSalidaDTO crearUsuario(UsuarioDTO aud_usuario);

  /**
   * Método que cambia la clave del usuario.
   * @param aud_usuario DTO con la informacion del usuario.
   * @return true si el usuario es registrado con éxito.
   */
  StringSalidaDTO actualizarClave(UsuarioDTO aud_usuario);

  /**
   * Método que verifica la existencia de un usuario.
   * @param as_id id del usuario.
   * @return true si el usuario existe.
   */
  StringSalidaDTO obtenerUsuario(String as_id);

  /**
   * Metodo que verifica la sesion de un usuario usando clave.
   * @param acd_clave id del usuario.
   * @return true si el usuario existe.
   */
  BooleanSalidaDTO verificarUsuario(ClaveDTO acd_clave);

  /**
   * Método que obtiene el tipo de segundo factor.
   * @param as_id id del usuario.
   * @return segundo factor de autenticacion.
   */
  StringSalidaDTO obtenerTipoSegundoFactor(String as_id);
}

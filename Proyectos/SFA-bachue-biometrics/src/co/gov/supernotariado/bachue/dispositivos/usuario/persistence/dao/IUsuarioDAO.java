package co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dao;

import javax.ejb.Local;

import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.model.Usuario;

/**
 * Interface de DAO de usuarios.
 */
@Local
public interface IUsuarioDAO {
  /**
   * Método que registra el usuario en la tabla de usuarios.
   * @param au_usuario Modelo que sera almacenado en la base de datos.
   * @return true si el usuario es registrado con éxito.
   */
  Boolean crearUsuario(Usuario au_usuario);

  /**
   * Método que actualiza la clave de usuario en la tabla de usuarios.
   * @param au_usuario Modelo que sera almacenado en la base de datos.
   * @return true si el usuario es registrado con éxito.
   */
  Boolean actualizarClave(Usuario au_usuario);

  /**
   * Metodo que consulta un usuario en la tabla de usuarios.
   * @param idUsuario id del usuario consultado.
   * @return el usuario consultado
   */
  Usuario consultarUsuario(String idUsuario);

  /**
   * Metodo que borra el usuario de la tabla de usuarios.
   * @param idUsuario Modelo que sera borrado de la base de datos.
   * @return true si el usuario es borrado con exito.
   */
  Boolean borrarUsuario(String idUsuario);
}

package co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dao;

import javax.ejb.Local;

/**
 * Interface de DAO de logs.
 */
@Local
public interface IUsuarioBachueDAO {
  /**
   * Método que obtiene el segundo factor del usuario en la tabla de usuarios.
   * @param as_usuarioId Modelo que sera almacenado en la base de datos.
   * @return segundo factor de autenticación.
   */
  String obtenerSegundoFactor(String as_usuarioId);
}

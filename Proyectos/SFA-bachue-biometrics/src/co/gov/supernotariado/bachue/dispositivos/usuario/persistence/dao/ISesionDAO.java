package co.gov.supernotariado.bachue.dispositivos.usuario.persistence.dao;

import javax.ejb.Local;

import co.gov.supernotariado.bachue.dispositivos.usuario.persistence.model.Sesion;

/**
 * Interface de DAO de sesion.
 */
@Local
public interface ISesionDAO {
  /**
   * Método que consulta la sesion en la tabla de sesiones.
   * @param as_sesion Sesion que sera consultada en la base de datos.
   * @return sesión consultada en la base de datos.
   */
  Sesion consultarSesion(String as_sesion);

  /**
   * Método que crea la sesion en la tabla de sesiones.
   * @param as_sesion Sesion que sera creada en la base de datos.
   * @return resultado de la crecion de la sesión.
   */
  Boolean crearSesion(Sesion as_sesion);
  
  /**
   * Método que actualiza la sesion en la tabla de sesiones.
   * @param as_sesion Sesion que sera creada en la base de datos.
   * @return resultado de la actualizacion de la sesión.
   */
  Boolean actualizarSesion(Sesion as_sesion);
}

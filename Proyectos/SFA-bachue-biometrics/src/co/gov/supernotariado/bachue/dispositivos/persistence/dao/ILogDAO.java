package co.gov.supernotariado.bachue.dispositivos.persistence.dao;

import javax.ejb.Local;

import co.gov.supernotariado.bachue.dispositivos.persistence.model.Accion;

/**
 * Interface de DAO de logs.
 */
@Local
public interface ILogDAO {
  /**
   * Método que registra el evento en la tabla de logs.
   * @param al_log Modelo que sera almacenado en la base de datos.
   * @return true si el evento es registrado con éxito.
   */
   Boolean crearEvento(Accion al_log);

  /**
   * Método que consulta las stats en la tabla de logs.
   * @param as_tipo Modelo que sera almacenado en la base de datos.
   * @param as_id Modelo que sera almacenado en la base de datos.
   * @return conteo registrado en la base de datos.
   */
   int consultarStats(String as_tipo, String as_id);
}

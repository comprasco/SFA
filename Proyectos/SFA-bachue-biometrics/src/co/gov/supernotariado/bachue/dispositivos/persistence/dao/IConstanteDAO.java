package co.gov.supernotariado.bachue.dispositivos.persistence.dao;

import javax.ejb.Local;

import co.gov.supernotariado.bachue.dispositivos.persistence.model.Constante;

import java.util.List;

/**
 * Interface de DAO de logs.
 */
@Local
public interface IConstanteDAO {
  /**
   * Método que obtiene las constantes en la tabla de constantes.
   * @return lista de las constantes.
   */
  List<Constante> consultarConstantes();
}

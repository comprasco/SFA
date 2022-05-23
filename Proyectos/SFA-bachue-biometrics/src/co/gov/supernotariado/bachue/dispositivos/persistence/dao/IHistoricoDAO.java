package co.gov.supernotariado.bachue.dispositivos.persistence.dao;

import javax.ejb.Local;

import co.gov.supernotariado.bachue.dispositivos.persistence.model.Historico;

import java.util.List;

/**
 * Interface de DAO de logs.
 */
@Local
public interface IHistoricoDAO {
  /**
   * Método que registra el historico de clave.
   * @param ah_historico Modelo que sera almacenado en la base de datos.
   * @return true si el usuario es registrado con éxito.
   */
   Boolean crearHistorico(Historico ah_historico);

  /**
   * Método que consulta las ultimas cinco claves en la tabla de histórico.
   * @param as_idUsuario id del usuario consultado.
   * @return las últimas cinco claves
   */
   List<Historico> consultarUltimasCincoClaves(String as_idUsuario);
}

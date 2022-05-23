package co.gov.supernotariado.bachue.dispositivos.ejb;

import co.gov.supernotariado.bachue.dispositivos.persistence.dto.BooleanSalidaDTO;
import co.gov.supernotariado.bachue.dispositivos.persistence.dto.EstadisticasSalidaDTO;
import co.gov.supernotariado.bachue.dispositivos.persistence.dto.LogDTO;

/**
 * Interface de logica de negocio de logs.
 */
public interface ILogBusiness {
  /**
   * Método que envia los datos del evento logueado al DAO para ser almacenado.
   * @param ald_log DTO con la informacion del evento.
   * @return true si el evento es registrado con éxito.
   */
  BooleanSalidaDTO registrarEvento(LogDTO ald_log);

  /**
   * Método que envia los datos de la peticion de stat al DAO para ser consultado.
   * @param as_tipo tipo de peticicion a consultar.
   * @param as_id id de la entidad a consultar.
   * @return int con el conteo de la entidad consultada.
   */
  EstadisticasSalidaDTO consultarStats(String as_tipo, String as_id);
}

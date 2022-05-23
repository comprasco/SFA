package co.gov.supernotariado.bachue.dispositivos.log.http.interfaces;

import co.gov.supernotariado.bachue.dispositivos.common.enums.TypeEnum;

/**
 * Interfaz para el envio del log al servidor.
 *
 */
public interface ILogRequests {

  /**
   * Metodo que guarda el log de la actividad ejecutada en la base de datos.
   * @param as_type tipo de evento.
   * @param as_details detalle de la accion ejecutada.
   * @param as_entityId entidad involucrada en la accion.
   * @param as_userId usuario que ejecuto la accion.
   * @return indicador de éxito de registro de log 
   */
  Boolean saveLog(TypeEnum as_type, String as_details, String as_entityId, String as_userId);
}

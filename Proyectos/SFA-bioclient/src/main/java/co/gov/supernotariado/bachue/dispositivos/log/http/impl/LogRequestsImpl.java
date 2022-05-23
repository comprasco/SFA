package co.gov.supernotariado.bachue.dispositivos.log.http.impl;

import org.apache.log4j.Logger;

import co.gov.supernotariado.bachue.corebachue.servicios.ws.BiometriaController;
import co.gov.supernotariado.bachue.corebachue.servicios.ws.BiometriaWS;
import co.gov.supernotariado.bachue.corebachue.servicios.ws.LogDTO;
import co.gov.supernotariado.bachue.dispositivos.common.enums.TypeEnum;
import co.gov.supernotariado.bachue.dispositivos.common.settings.LoggerFactory;
import co.gov.supernotariado.bachue.dispositivos.common.settings.Settings;
import co.gov.supernotariado.bachue.dispositivos.log.http.interfaces.ILogRequests;


/**
 * Implementacion para guardar el log del proceso ejecutado.
 */
public class LogRequestsImpl implements ILogRequests {
   /**
    * Logger de la aplicación
    */
   private transient Logger il_log = LoggerFactory.getLogger (getClass ());

   /**
    * Envia la información al log de auditoría de negocio del servidor
    */
  @Override
  public Boolean saveLog(TypeEnum as_type, String as_details, String as_entityId, String as_userId) {
    try {

      BiometriaController lbc_biometriaController = new BiometriaController(Settings.getBackendURL());
      BiometriaWS bws = lbc_biometriaController.getBiometriaWSPort();

      LogDTO lld_logDTO = new LogDTO();
      lld_logDTO.setEvento(as_type.getEvent());
      lld_logDTO.setDetalle(as_details);
      lld_logDTO.setIdEntidad(as_entityId);
      lld_logDTO.setIdUsuario(as_userId);

      bws.registrarEvento(lld_logDTO);
      return true;
    } catch (Exception le_exception) {
       PrintStackTrace(le_exception);
      return false;
    }
  }

  /**
   * Registro del error y la causa en el log
   * @param exception Excepción que se va a registrar
   */
  private void PrintStackTrace(Throwable exception)
  {
     il_log.error("Error " + exception.getMessage());
     while(exception.getCause() == null)
     {
        il_log.error("Causa :" + exception.getCause().getMessage());
        exception = exception.getCause();
     }
  }
}
